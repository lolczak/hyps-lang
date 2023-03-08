package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.ast.Expr

/**
  * Hyps expression parser.
  * The precedence rules going from lowest to highest:
  * {{{
  *   | Name       | Operator  | Associates |
  *   | Equality   | == !=     | Left       |
  *   | Comparison | > < >= <= | Left       |
  *   | Term       | + -       | Left       |
  *   | Factor     | * /       | Left       |
  *   | Unary      | ! -       | Right      |
  * }}}
  */
trait ExprParser { this: BacktrackingParser =>

  protected def expression(): Expr = equality()

  /**
    * Parses equality expression:
    * {{{
    *   equality = comparison (( "!=" | "==" ) comparison )* ;
    * }}}
    * @return
    */
  protected def equality(): Expr = {
    var expr = comparison()
    while (check(Tokens.BANG_EQUAL, Tokens.EQUAL_EQUAL)) {
      val operator = consume()
      val equalityOperation =
        operator.kind match {
          case Tokens.BANG_EQUAL  => Expr.Unequal(_, _)
          case Tokens.EQUAL_EQUAL => Expr.Equal(_, _)
        }
      val right = comparison()
      expr = equalityOperation(expr, right)
    }
    expr
  }

  /**
    * Parses comparison expression:
    * {{{
    *   comparison -> term (( ">" | "<" | "<=" | ">=" ) term )* ;
    * }}}
    * @return
    */
  protected def comparison(): Expr = {
    var expr = term()
    while (check(Tokens.GREATER, Tokens.GREATER_EQUAL, Tokens.LESS, Tokens.LESS_EQUAL)) {
      val operator = consume()
      val comparisonOperation =
        operator.kind match {
          case Tokens.GREATER       => Expr.Greater(_, _)
          case Tokens.GREATER_EQUAL => Expr.GreaterEqual(_, _)
          case Tokens.LESS          => Expr.Less(_, _)
          case Tokens.LESS_EQUAL    => Expr.LessEqual(_, _)
        }
      val right = term()
      expr = comparisonOperation(expr, right)
    }
    expr
  }

  /**
    * Parses addition and subtraction operations:
    * {{{
    *   term -> factor (("-" / "+" ) factor )* ;
    * }}}
    * @return
    */
  protected def term(): Expr = {
    var expr = factor()
    while (check(Tokens.PLUS, Tokens.MINUS)) {
      val operator = consume()
      val termOperation =
        operator.kind match {
          case Tokens.PLUS  => Expr.Addition(_, _)
          case Tokens.MINUS => Expr.Subtraction(_, _)
        }
      val right = factor()
      expr = termOperation(expr, right)
    }
    expr
  }

  /**
    * Parses multiplication and division operations:
    * {{{
    *   factor -> unary (("*" / "/" ) unary )*
    * }}}
    * @return factor operation
    */
  protected def factor(): Expr = {
    var expr = unary()
    while (check(Tokens.STAR, Tokens.SLASH)) {
      val operator = consume()
      val factoryOperation =
        operator.kind match {
          case Tokens.STAR  => Expr.Multiplication(_, _)
          case Tokens.SLASH => Expr.Division(_, _)
        }
      val right = unary()
      expr = factoryOperation(expr, right)
    }
    expr
  }

  /**
    * Parses unary operator:
    * {{{
    *   unary -> ( "!" | "-" ) unary | primary ;
    * }}}
    * @return unary expression
    */
  protected def unary(): Expr = {
    val next = peek(1)
    next.kind match {
      case Tokens.BANG =>
        val expr = unary()
        Expr.LogicalNot(expr)

      case Tokens.MINUS =>
        val expr = unary()
        Expr.UnaryNegation(expr)

      case _ =>
        primary()
    }
  }

  /**
    * Parses the below rule:
    * {{{
    *   primary -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;
    * }}}
    * @return primary expression
    */
  protected def primary(): Expr = {
    val next = peek(1)
    val expr =
      next.kind match {
        case Tokens.TRUE   => Expr.True
        case Tokens.FALSE  => Expr.False
        case Tokens.NIL    => Expr.Nil
        case Tokens.NUMBER => Expr.NumberLiteral(next.lexeme)
        case Tokens.STRING => Expr.StringLiteral(next.lexeme)
        case Tokens.LEFT_PAREN =>
          consume()
          val innerExpr = expression()
          if (peek(1).kind != Tokens.RIGHT_PAREN) {
            throw CompilerError(peek(1).position, "Expected ')' after expression.")
          }
          innerExpr
        case _ => throw CompilerError(next.position, "Cannot match primary expression")
      }
    consume()
    expr
  }

}
