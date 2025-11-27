package hyps.lang.compiler.syntax.ast

/** A class that implement a default tree traversal strategy: breadth-first component-wise. */
trait Traverser {

  /** Visits the given AST node.
    */
  def visit(ast: AST): Unit

}
