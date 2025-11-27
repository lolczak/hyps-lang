package hyps.lang.compiler.syntax.ast

trait Statement extends AST

object Statement {

  case class Block(statements: List[Statement]) extends Statement {

    override def traverse(traverser: Traverser): Unit =
      statements.foreach(traverser.visit)

    override def transform(transformer: Transformer): AST =
      Block(statements.map(stmt => transformer.transform(stmt).asInstanceOf[Statement]))
  }

//  case class ExpressionStatement(expr: Expression) extends Statement

  case class PrintlnStatement(expr: Expression) extends Statement {

    override def traverse(traverser: Traverser): Unit =
      traverser.visit(expr)

    override def transform(transformer: Transformer): AST =
      PrintlnStatement(transformer.transform(expr).asInstanceOf[Expression])
  }
}
