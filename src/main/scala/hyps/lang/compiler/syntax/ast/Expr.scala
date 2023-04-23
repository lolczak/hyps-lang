package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.CompilerError

sealed trait Expr extends AST

object Expr {

  sealed trait Equality extends Expr {
    def left: Expr
    def right: Expr
  }

  case class Equal(left: Expr, right: Expr) extends Equality {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Equal(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Equal from $newChildren")
      }
  }

  case class Unequal(left: Expr, right: Expr) extends Equality {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Unequal(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Unequal from $newChildren")
      }
  }

  sealed trait Comparison extends Expr {
    def left: Expr
    def right: Expr
  }

  case class Greater(left: Expr, right: Expr) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Greater(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Greater from $newChildren")
      }
  }

  case class GreaterEqual(left: Expr, right: Expr) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => GreaterEqual(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct GreaterEqual from $newChildren")
      }
  }

  case class Less(left: Expr, right: Expr) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Less(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Less from $newChildren")
      }
  }

  case class LessEqual(left: Expr, right: Expr) extends Comparison {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => LessEqual(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct LessEqual from $newChildren")
      }
  }

  sealed trait Term extends Expr

  case class Addition(left: Expr, right: Expr) extends Term {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Addition(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Addition from $newChildren")
      }
  }

  case class Subtraction(left: Expr, right: Expr) extends Term {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Subtraction(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Subtraction from $newChildren")
      }
  }

  sealed trait Factor extends Expr

  case class Multiplication(left: Expr, right: Expr) extends Factor {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Multiplication(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Multiplication from $newChildren")
      }
  }

  case class Division(left: Expr, right: Expr) extends Factor {
    override def children(): List[AST] = List(left, right)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (l: Expr) :: (r: Expr) :: Nil => Division(l, r)
        case _                             => throw CompilerError(origin, s"Cannot construct Division from $newChildren")
      }
  }

  sealed trait Unary extends Expr {}

  case class LogicalNot(expr: Expr) extends Unary {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expr) :: Nil => Division(expr)
        case _                   => throw CompilerError(origin, s"Cannot construct LogicalNot from $newChildren")
      }
  }

  case class UnaryNegation(expr: Expr) extends Unary {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expr) :: Nil => UnaryNegation(expr)
        case _                   => throw CompilerError(origin, s"Cannot construct UnaryNegation from $newChildren")
      }
  }

  case class Grouping(expr: Expr) extends Expr {
    override def children(): List[AST] = List(expr)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case (expr: Expr) :: Nil => Grouping(expr)
        case _                   => throw CompilerError(origin, s"Cannot construct Grouping from $newChildren")
      }
  }

  sealed trait Literal extends Expr

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

  case object Null extends Literal {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

}
