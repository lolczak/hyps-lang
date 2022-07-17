package hyps.lang.compiler.interpreter

import hyps.lang.compiler.ast.Expr._
import hyps.lang.compiler.ast.{AstVisitor, Expr, Statement}
import hyps.lang.compiler.interpreter.RuntimeValue._

class Interpreter(program: List[Statement]) extends AstVisitor[RuntimeValue] {

  def interpret(): String = {
    var lastResult: RuntimeValue = RuntimeValue.UndefinedValue
    for (statement <- program) {
      lastResult = execute(statement)
    }
    stringify(lastResult)
  }

  protected def stringify(value: RuntimeValue): String =
    value match {
      case RuntimeValue.NilValue            => "nil"
      case RuntimeValue.UndefinedValue      => "undefined"
      case BooleanValue(value)              => s"$value"
      case RuntimeValue.DecimalValue(value) => s"$value"
      case RuntimeValue.StringValue(value)  => value
      case RuntimeValue.UnitValue           => "()"
    }

  protected def execute(statement: Statement): RuntimeValue =
    statement.accept(this)

  protected def evaluate(expr: Expr): RuntimeValue =
    expr.accept(this)

  override def visitStringLiteral(literal: Expr.StringLiteral): RuntimeValue = RuntimeValue.StringValue(literal.value)

  override def visitNumberLiteral(literal: Expr.NumberLiteral): RuntimeValue =
    try {
      val number = literal.value.toLong
      DecimalValue(number)
    } catch {
      case ex: NumberFormatException => throw RuntimeError(ex.getMessage)
    }

  override def visitExprGrouping(expr: Expr.Grouping): RuntimeValue =
    evaluate(expr.expr)

  override def visitUnaryExpr(expr: Expr.Unary): RuntimeValue =
    expr match {
      case LogicalNot(innerExpr) =>
        val evaluated = evaluate(innerExpr)
        evaluated match {
          case BooleanValue(value) => BooleanValue(!value)
          case _                   => throw RuntimeError(s"Cannot negate not a boolean value: $evaluated")
        }

      case UnaryNegation(innerExpr) =>
        val evaluated = evaluate(innerExpr)
        evaluated match {
          case DecimalValue(value) => DecimalValue(-value)
          case _                   => throw RuntimeError(s"Cannot negate not a number value: $evaluated")
        }
    }

  override def visitBooleanLiteral(expr: Expr.BooleanLiteral): RuntimeValue =
    expr match {
      case Expr.True  => BooleanValue(true)
      case Expr.False => BooleanValue(false)
    }

  override def visitNil(expr: Expr.Nil.type): RuntimeValue = RuntimeValue.NilValue

  override def visitMultiplication(expr: Expr.Multiplication): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    (left, right) match {
      case (DecimalValue(x), DecimalValue(y)) => DecimalValue(x * y)
      case (_, DecimalValue(_))               => throw RuntimeError(s"Cannot multiple not decimal value: [$left]")
      case (DecimalValue(_), _)               => throw RuntimeError(s"Cannot multiple not decimal value: [$right]")
      case (_, _)                             => throw RuntimeError(s"Cannot multiple not decimal values: [$left, $right]")
    }
  }

  override def visitDivision(expr: Expr.Division): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    (left, right) match {
      case (DecimalValue(_), DecimalValue(0)) => UndefinedValue
      case (DecimalValue(x), DecimalValue(y)) => DecimalValue(x / y)
      case (_, DecimalValue(_))               => throw RuntimeError(s"Cannot divide not decimal value: [$left]")
      case (DecimalValue(_), _)               => throw RuntimeError(s"Cannot divide not decimal value: [$right]")
      case (_, _)                             => throw RuntimeError(s"Cannot divide not decimal values: [$left, $right]")
    }
  }

  override def visitAddition(expr: Expr.Addition): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    (left, right) match {
      case (StringValue(x), StringValue(y))   => StringValue(x + y)
      case (DecimalValue(x), StringValue(y))  => StringValue(x.toString + y)
      case (StringValue(x), DecimalValue(y))  => StringValue(x + y)
      case (DecimalValue(x), DecimalValue(y)) => DecimalValue(x + y)
      case (_, DecimalValue(_))               => throw RuntimeError(s"Cannot add not decimal value: [$left]")
      case (DecimalValue(_), _)               => throw RuntimeError(s"Cannot add not decimal value: [$right]")
      case (_, _)                             => throw RuntimeError(s"Cannot add not decimal values: [$left, $right]")
    }
  }

  override def visitSubtraction(expr: Expr.Subtraction): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    (left, right) match {
      case (DecimalValue(x), DecimalValue(y)) => DecimalValue(x - y)
      case (_, DecimalValue(_))               => throw RuntimeError(s"Cannot subtract not decimal value: [$left]")
      case (DecimalValue(_), _)               => throw RuntimeError(s"Cannot subtract not decimal value: [$right]")
      case (_, _)                             => throw RuntimeError(s"Cannot subtract not decimal values: [$left, $right]")
    }
  }

  override def visitComparison(expr: Expr.Comparison): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    (left, right) match {
      case (DecimalValue(x), DecimalValue(y)) =>
        expr match {
          case GreaterEqual(_, _) => BooleanValue(x >= y)
          case Greater(_, _)      => BooleanValue(x > y)
          case Less(_, _)         => BooleanValue(x < y)
          case LessEqual(_, _)    => BooleanValue(x <= y)
        }
      case (_, DecimalValue(_)) => throw RuntimeError(s"Cannot compare not decimal value: [$left]")
      case (DecimalValue(_), _) => throw RuntimeError(s"Cannot compare not decimal value: [$right]")
      case (_, _)               => throw RuntimeError(s"Cannot compare not decimal values: [$left, $right]")
    }
  }

  override def visitEquality(expr: Expr.Equality): RuntimeValue = {
    val left  = evaluate(expr.left)
    val right = evaluate(expr.right)
    val isEquality =
      expr match {
        case Equal(_, _)   => true
        case Unequal(_, _) => false
      }
    (left, right) match {
      case (DecimalValue(x), DecimalValue(y)) =>
        if (isEquality) {
          BooleanValue(x == y)
        } else {
          BooleanValue(x != y)
        }

      case (StringValue(x), StringValue(y)) =>
        if (isEquality) {
          BooleanValue(x == y)
        } else {
          BooleanValue(x != y)
        }

      case (BooleanValue(x), BooleanValue(y)) =>
        if (isEquality) {
          BooleanValue(x == y)
        } else {
          BooleanValue(x != y)
        }

      case (NilValue, NilValue) =>
        if (isEquality) {
          BooleanValue(true)
        } else {
          BooleanValue(false)
        }

      case (UndefinedValue, UndefinedValue) =>
        if (isEquality) {
          BooleanValue(true)
        } else {
          BooleanValue(false)
        }

      case (x, y) =>
        throw RuntimeError(s"Cannot compare mismatching types: [$x, ${y}]")
    }
  }

  override def visitExpressionStatement(statement: Statement.ExpressionStatement): RuntimeValue =
    evaluate(statement.expr)

  override def visitPrintStatement(statement: Statement.PrintStatement): RuntimeValue = {
    val argument = evaluate(statement.expr)
    println(stringify(argument))
    RuntimeValue.UnitValue
  }

}
