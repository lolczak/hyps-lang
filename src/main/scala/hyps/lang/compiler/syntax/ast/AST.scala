package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.Origin
import hyps.lang.compiler.util.tree.TreeNode

import scala.reflect.internal.SymbolTable

/** Representation of the abstract syntactic structure of Hyps source code.
  * Each node of the tree denotes a construct occurring in the source code.
  * It does not represent every detail appearing in the source code, but rather just the structural or
  * content-related details.  */
trait AST extends TreeNode[AST] {

  var origin: Origin           = _
  var symbolTable: SymbolTable = _
  var `type`: String           = _

}
