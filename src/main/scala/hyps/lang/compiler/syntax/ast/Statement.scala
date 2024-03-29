package hyps.lang.compiler.syntax.ast

trait Statement extends AST

object Statement {

  case class Block(statements: List[Statement]) extends Statement {
    override def children(): List[AST] = statements

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      Block(newChildren.collect { case s: Statement => s })
  }

  case class ExpressionStatement(expr: Expression) extends Statement {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      ExpressionStatement(newChildren.head.asInstanceOf[Expression])
  }

  case class PrintlnStatement(expr: Expression) extends Statement {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      PrintlnStatement(newChildren.head.asInstanceOf[Expression])
  }

}
