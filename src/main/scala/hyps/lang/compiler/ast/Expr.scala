package hyps.lang.compiler.ast

sealed trait Expr extends AST

object Expr {

  sealed trait Equality extends Expr
  case class Equal(left: Expr, right: Expr) extends Expr
  case class Unequal(left: Expr, right: Expr) extends Expr

  sealed trait Comparison extends Expr
  case class Greater(left: Expr, right: Expr) extends Comparison
  case class GreaterEqual(left: Expr, right: Expr) extends Comparison
  case class Less(left: Expr, right: Expr) extends Comparison
  case class LessEqual(left: Expr, right: Expr) extends Comparison

  sealed trait Term extends Expr
  case class Addition(left: Expr, right: Expr)    extends Term
  case class Subtraction(left: Expr, right: Expr) extends Term

  sealed trait Factor                                extends Expr
  case class Multiplication(left: Expr, right: Expr) extends Factor
  case class Division(left: Expr, right: Expr)       extends Factor

  sealed trait Unary                   extends Expr
  case class LogicalNot(expr: Expr)    extends Unary
  case class UnaryNegation(expr: Expr) extends Unary

  case class Grouping(expr: Expr) extends Expr

  sealed trait Literal                extends Expr
  case class Number(value: String)    extends Literal
  case class StringLit(value: String) extends Literal
  case object True                    extends Literal
  case object False                   extends Literal
  case object Nil                     extends Literal

}
