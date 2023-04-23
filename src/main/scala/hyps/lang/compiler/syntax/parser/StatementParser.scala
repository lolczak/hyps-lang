package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.syntax.ast.Statement

trait StatementParser { this: BacktrackingParser with ExprParser =>

  protected def statement(): Statement =
    if (check(Tokens.PRINTLN)) {
      printStatement()
    } else {
      expressionStatement()
    }

  protected def printStatement(): Statement.PrintlnStatement = {
    matchToken(Tokens.PRINTLN)
    val argument = expression()
    matchToken(Tokens.SEMICOLON, Some("Expect ';' after statement."))
    Statement.PrintlnStatement(argument)
  }

  protected def expressionStatement(): Statement.ExpressionStatement = {
    val expr = expression()
    matchToken(Tokens.SEMICOLON, Some("Expect ';' after statement."))
    Statement.ExpressionStatement(expr)
  }

}
