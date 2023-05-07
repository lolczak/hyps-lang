package hyps.lang.compiler.semantic

import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.ast.Declaration.FunctionDeclaration
import hyps.lang.compiler.util.tree.CompilerPass

import scala.collection.mutable

class SymbolTableConstructionPass extends CompilerPass[AST] {

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
          .declare(Symbol.VariableSymbol(parameter.name, parameter, currentScope.resolveType(parameter.parameterType)))
      }
      functionDeclaration
  }

  after {
    case functionDeclaration: FunctionDeclaration =>
      scopeStack.pop()
      functionDeclaration
  }

}
