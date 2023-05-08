package hyps.lang.compiler.semantic

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.semantic.Symbol.VariableSymbol
import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.ast.Declaration.{FunctionDeclaration, VariableDeclaration}
import hyps.lang.compiler.syntax.ast.Expression.SymbolReference
import hyps.lang.compiler.syntax.ast.Reference.VariableReference
import hyps.lang.compiler.util.tree.TreeRewriter

import scala.collection.mutable

class SymbolTableConstructionPass extends TreeRewriter[AST] {

  private val scopeStack: mutable.Stack[Scope] = mutable.Stack(Scopes.GlobalScope)

  private def currentScope: Scope = scopeStack.head

  before {
    case functionDeclaration: FunctionDeclaration =>
      currentScope.declare(Symbol.FunctionSymbol(functionDeclaration.identifier, functionDeclaration))
      val functionScope = new Scope(s"function:${functionDeclaration.identifier}", Some(currentScope))
      scopeStack.push(functionScope)
      functionDeclaration.setScope(functionScope)
  }

  transform {
    case functionDeclaration: FunctionDeclaration =>
      functionDeclaration.parameters.foreach { parameter =>
        currentScope
          .declare(VariableSymbol(parameter.name, parameter, currentScope.resolveType(parameter.parameterType)))
      }
      functionDeclaration

    case code @ VariableDeclaration(name, varType, _) =>
      currentScope.declare(VariableSymbol(name, code, varType.flatMap(currentScope.resolveType)))
      code.setScope(currentScope)
      code

    case code @ SymbolReference(name) =>
      currentScope.resolve(name) match {
        case Some(symbol: VariableSymbol) =>
          val ref = VariableReference(symbol)
          ref.setScope(currentScope)
          ref

        case _ =>
          throw CompilerError(code.origin, s"Cannot resolve symbol $name")
      }

    case other =>
      other.setScope(currentScope)
      other
  }

  after {
    case _: FunctionDeclaration =>
      scopeStack.pop()
  }

}
