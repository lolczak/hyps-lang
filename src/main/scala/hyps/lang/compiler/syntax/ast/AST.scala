package hyps.lang.compiler.ast

trait AST {

  def accept[A](visitor: AstVisitor[A]): A

}
