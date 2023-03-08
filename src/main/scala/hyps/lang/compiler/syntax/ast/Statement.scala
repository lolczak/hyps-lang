package hyps.lang.compiler.ast

sealed trait Statement extends AST

object Statement {

  case class ExpressionStatement(expr: Expr) extends Statement {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitExpressionStatement(this)
  }

  case class PrintStatement(expr: Expr) extends Statement {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitPrintStatement(this)
  }

}
