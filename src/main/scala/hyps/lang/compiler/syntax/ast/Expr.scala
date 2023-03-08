package hyps.lang.compiler.ast

sealed trait Expr extends AST

object Expr {

  sealed trait Equality extends Expr {
    def left: Expr
    def right: Expr
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitEquality(this)
  }
  case class Equal(left: Expr, right: Expr)   extends Equality
  case class Unequal(left: Expr, right: Expr) extends Equality

  sealed trait Comparison extends Expr {
    def left: Expr
    def right: Expr
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitComparison(this)
  }
  case class Greater(left: Expr, right: Expr)      extends Comparison
  case class GreaterEqual(left: Expr, right: Expr) extends Comparison
  case class Less(left: Expr, right: Expr)         extends Comparison
  case class LessEqual(left: Expr, right: Expr)    extends Comparison

  sealed trait Term extends Expr

  case class Addition(left: Expr, right: Expr) extends Term {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitAddition(this)
  }

  case class Subtraction(left: Expr, right: Expr) extends Term {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitSubtraction(this)
  }

  sealed trait Factor extends Expr

  case class Multiplication(left: Expr, right: Expr) extends Factor {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitMultiplication(this)
  }

  case class Division(left: Expr, right: Expr) extends Factor {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitDivision(this)
  }

  sealed trait Unary extends Expr {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitUnaryExpr(this)
  }
  case class LogicalNot(expr: Expr)    extends Unary
  case class UnaryNegation(expr: Expr) extends Unary

  case class Grouping(expr: Expr) extends Expr {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitExprGrouping(this)
  }

  sealed trait Literal extends Expr

  case class NumberLiteral(value: String) extends Literal {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitNumberLiteral(this)
  }

  case class StringLiteral(value: String) extends Literal {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitStringLiteral(this)
  }

  sealed trait BooleanLiteral extends Literal {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitBooleanLiteral(this)
  }
  case object True  extends BooleanLiteral
  case object False extends BooleanLiteral

  case object Nil extends Literal {
    override def accept[A](visitor: AstVisitor[A]): A = visitor.visitNil(this)
  }

}
