package hyps.lang.compiler.semantic.analysis

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.semantic.types
import hyps.lang.compiler.semantic.types.Symbol.VariableSymbol
import hyps.lang.compiler.semantic.types.{Scope, Scopes}
import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.ast.Declaration.{FunctionDeclaration, VariableDeclaration}
import hyps.lang.compiler.syntax.ast.Expression.SymbolReference
import hyps.lang.compiler.syntax.ast.Reference.VariableReference
import hyps.lang.compiler.util.tree.TreeRewriter

import scala.collection.mutable

/** A transformation that constructs the symbol table for a program.
  */
class SymbolTableConstructionPass extends TreeRewriter[AST] {

  private val scopeStack: mutable.Stack[Scope] = mutable.Stack(Scopes.GlobalScope)

  private def currentScope: Scope = scopeStack.head

  before {
    case functionDeclaration: FunctionDeclaration =>
      currentScope.declare(types.Symbol.FunctionSymbol(functionDeclaration.identifier, functionDeclaration))
      val functionScope = new Scope(Some(currentScope))
      scopeStack.push(functionScope)
      functionDeclaration.setScope(functionScope)
  }

  rewrite {
    case functionDeclaration: FunctionDeclaration =>
      functionDeclaration.parameters.foreach { parameter =>
        currentScope.declare(VariableSymbol(parameter.name, parameter))
        parameter.setScope(currentScope)
      }
      functionDeclaration

    case construct @ VariableDeclaration(name, _, _) =>
      currentScope.declare(VariableSymbol(name, construct))
      construct

    case construct @ SymbolReference(name) =>
      currentScope.resolve(name) match {
        case Some(_: VariableSymbol) =>
          VariableReference(name)

        case _ =>
          throw CompilerError(construct.origin, s"Cannot resolve symbol $name [$construct]")
      }
  }

  after {
    case funDecl: FunctionDeclaration =>
      funDecl.setScope(currentScope)
      scopeStack.pop()

    case other =>
      other.setScope(currentScope)
  }

}
