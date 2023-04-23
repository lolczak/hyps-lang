package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.Origin
import hyps.lang.compiler.util.tree.TreeNode

import scala.reflect.internal.SymbolTable

trait AST extends TreeNode[AST] {

  var origin: Origin = _
  var symbolTable: SymbolTable = _
  var `type`: String = _

}
