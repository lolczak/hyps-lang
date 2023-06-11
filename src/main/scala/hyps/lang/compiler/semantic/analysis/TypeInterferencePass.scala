package hyps.lang.compiler.semantic.analysis

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.semantic.types.Symbol.VariableSymbol
import hyps.lang.compiler.syntax.ast.Declaration._
import hyps.lang.compiler.syntax.ast.Reference.VariableReference
import hyps.lang.compiler.syntax.ast.Typed._
import hyps.lang.compiler.syntax.ast.{AST, Typed}
import hyps.lang.compiler.util.tree.TreeRewriter

/** A transformation that infers the type of untyped constructs.
  * It also resolves the type of typed constructs.
  */
object TypeInterferencePass extends TreeRewriter[AST] {

  rewrite {
    case fnDecl @ FunctionDeclaration(name, decorators, parameters, returnType, body) =>
      val scope = fnDecl.getScope
      val newParameters = parameters.map {
        case parDecl @ ParameterDeclaration(name, dataTypeName) =>
          scope.resolveType(dataTypeName) match {
            case Some(dataType) =>
              scope.redefine(VariableSymbol(name, parDecl, Some(dataType)))
              val newDecl = TypedParameterDeclaration(name, dataType)
              newDecl.setScope(scope)
              newDecl
            case None => throw CompilerError(parDecl.origin, s"Cannot resolve type $dataTypeName [$parDecl]")
          }
      }
      val newReturnType = scope.resolveType(returnType) match {
        case Some(dataType) => dataType
        case None           => throw CompilerError(fnDecl.origin, s"Cannot resolve type $returnType [$fnDecl]")
      }
      val newDecl = TypedFunctionDeclaration(name, decorators, newParameters, newReturnType, body)
      newDecl.setScope(scope)
      newDecl

    case parDecl @ ParameterDeclaration(name, dataTypeName) =>
      val scope = parDecl.getScope
      scope.resolveType(dataTypeName) match {
        case Some(dataType) =>
          scope.redefine(VariableSymbol(name, parDecl, Some(dataType)))
          val newDecl = TypedParameterDeclaration(name, dataType)
          newDecl.setScope(scope)
          newDecl
        case None => throw CompilerError(parDecl.origin, s"Cannot resolve type $dataTypeName [$parDecl]")
      }

    case varDecl @ VariableDeclaration(name, Some(dataTypeName), initializer) =>
      val scope = varDecl.getScope
      scope.resolveType(dataTypeName) match {
        case Some(dataType) =>
          scope.redefine(VariableSymbol(name, varDecl, Some(dataType)))
          val newDecl = TypedVariableDeclaration(name, initializer, dataType)
          newDecl.setScope(scope)
          newDecl
        case None => throw CompilerError(varDecl.origin, s"Cannot resolve type $dataTypeName [$varDecl]")
      }

    case varDecl @ VariableDeclaration(name, None, initializer) =>
      initializer match {
        case typedExpr: Typed =>
          val scope = varDecl.getScope
          scope.redefine(VariableSymbol(name, varDecl, Some(typedExpr.dataType)))
          val newDecl = TypedVariableDeclaration(name, initializer, typedExpr.dataType)
          newDecl.setScope(scope)
          newDecl
        case _ => varDecl
      }

    case varRef @ VariableReference(name) =>
      val scope = varRef.getScope
      scope.resolve(name) match {
        case Some(symbol: VariableSymbol) if symbol.dataType.isDefined =>
          val newRef = TypedVariableReference(name, symbol.dataType.get)
          newRef.setScope(scope)
          newRef
        case _ => varRef
      }
  }

}
