package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.CompilerError

trait Expression extends AST

object Expression {

  sealed trait Equality extends Expression {
    def left: Expression
    def right: Expression
  }

  case class Equal(left: Expression, right: Expression) extends Equality {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Equal(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Equal from $newChildren")
      }
  }

  case class Unequal(left: Expression, right: Expression) extends Equality {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Unequal(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Unequal from $newChildren")
      }
  }

  sealed trait Comparison extends Expression {
    def left: Expression
    def right: Expression
  }

  case class Greater(left: Expression, right: Expression) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Greater(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Greater from $newChildren")
      }
  }

  case class GreaterEqual(left: Expression, right: Expression) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => GreaterEqual(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct GreaterEqual from $newChildren")
      }
  }

  case class Less(left: Expression, right: Expression) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Less(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Less from $newChildren")
      }
  }

  case class LessEqual(left: Expression, right: Expression) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => LessEqual(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct LessEqual from $newChildren")
      }
  }

  sealed trait Term extends Expression

  case class Addition(left: Expression, right: Expression) extends Term {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Addition(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Addition from $newChildren")
      }
  }

  case class Subtraction(left: Expression, right: Expression) extends Term {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Subtraction(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Subtraction from $newChildren")
      }
  }

  sealed trait Factor extends Expression

  case class Multiplication(left: Expression, right: Expression) extends Factor {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Multiplication(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Multiplication from $newChildren")
      }
  }

  case class Division(left: Expression, right: Expression) extends Factor {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expression) :: (r: Expression) :: Nil => Division(l, r)
        case _                                         => throw CompilerError(origin, s"Cannot construct Division from $newChildren")
      }
  }

  sealed trait Unary extends Expression {}

  case class LogicalNot(expr: Expression) extends Unary {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expression) :: Nil => LogicalNot(expr)
        case _                         => throw CompilerError(origin, s"Cannot construct LogicalNot from $newChildren")
      }
  }

  case class UnaryNegation(expr: Expression) extends Unary {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expression) :: Nil => UnaryNegation(expr)
        case _                         => throw CompilerError(origin, s"Cannot construct UnaryNegation from $newChildren")
      }
  }

  case class Grouping(expr: Expression) extends Expression {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expression) :: Nil => Grouping(expr)
        case _                         => throw CompilerError(origin, s"Cannot construct Grouping from $newChildren")
      }
  }

  case class SymbolReference(name: String) extends Expression {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  sealed trait Literal extends Expression

  case class NumberLiteral(value: String) extends Literal {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class StringLiteral(value: String) extends Literal {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  sealed trait BooleanLiteral extends Literal {}

  case object True extends BooleanLiteral {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case object False extends BooleanLiteral {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case object NullLiteral extends Literal {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

}
