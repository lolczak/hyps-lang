package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.Origin

/** Representation of the abstract syntactic structure of Hyps source code.
  * Each node of the tree denotes a construct occurring in the source code.
  * It does not represent every detail appearing in the source code, but rather just the structural or
  * content-related details.  */
trait AST extends ScopeHolder {

  var origin: Origin = _

  /** Traverse over the structure of the given AST.
    * It is used to visit all nodes in the AST. */
  def traverse(traverser: Traverser): Unit

  /** Traverse over the structure of the given AST and transform it. */
  def transform(transformer: Transformer): AST

}
