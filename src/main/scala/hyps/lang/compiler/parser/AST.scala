package hyps.lang.compiler.parser

trait AST

object AST {

  sealed trait Statement extends AST

  case class Block(statements: List[AST]) extends Statement

  case class Parameter(name: String, `type`: String) extends Statement

  case class FunctionDef(name: String, arguments: List[Parameter], body: List[Statement]) extends Statement

  case class ReturnStatement(expression: Expression) extends Statement

  sealed trait Expression extends Statement

  case class AddOperator(lhs: Expression, rhs: Expression) extends Expression

  case class Var(name: String) extends Expression

}
