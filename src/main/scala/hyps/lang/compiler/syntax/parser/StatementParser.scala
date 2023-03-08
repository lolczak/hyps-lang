package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.ast.Statement

trait StatementParser { this: BacktrackingParser with ExprParser =>

  protected def statement(): Statement =
    if (check(Tokens.PRINT)) {
      printStatement()
    } else {
      expressionStatement()
    }

  protected def printStatement(): Statement.PrintStatement = {
    matchToken(Tokens.PRINT)
    val argument = expression()
    matchToken(Tokens.SEMICOLON, Some("Expect ';' after statement."))
    Statement.PrintStatement(argument)
  }

  protected def expressionStatement(): Statement.ExpressionStatement = {
    val expr = expression()
    matchToken(Tokens.SEMICOLON, Some("Expect ';' after statement."))
    Statement.ExpressionStatement(expr)
  }

}
