package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.builtin.BuiltInTypes
import hyps.lang.compiler.semantic.types.DataType

trait Expression extends AST

object Expression {

//  sealed trait Equality extends Expression {
//    def left: Expression
//    def right: Expression
//  }
//
//  case class Equal(left: Expression, right: Expression) extends Equality
//
//  case class Unequal(left: Expression, right: Expression) extends Equality
//
//  sealed trait Comparison extends Expression
//
//  case class Greater(left: Expression, right: Expression) extends Comparison
//
//  case class GreaterEqual(left: Expression, right: Expression) extends Comparison
//
//  case class Less(left: Expression, right: Expression) extends Comparison
//
//  case class LessEqual(left: Expression, right: Expression) extends Comparison
//
//  sealed trait Term extends Expression
//
//  case class Addition(left: Expression, right: Expression) extends Term
//
//  case class Subtraction(left: Expression, right: Expression) extends Term
//
//  sealed trait Factor extends Expression
//
//  case class Multiplication(left: Expression, right: Expression) extends Factor
//
//  case class Division(left: Expression, right: Expression) extends Factor
//
//  sealed trait Unary extends Expression
//
//  case class LogicalNot(expr: Expression)    extends Unary
//  case class UnaryNegation(expr: Expression) extends Unary
//  case class Grouping(expr: Expression)      extends Expression

  sealed trait Literal extends Expression

  case class StringLiteral(value: String) extends Literal with Typed {

    override def dataType: DataType = BuiltInTypes.StringType

    override def traverse(traverser: Traverser): Unit = ()

    override def transform(transformer: Transformer): AST = this
  }

}
