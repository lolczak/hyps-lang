package hyps.lang.compiler.syntax.parser

import scala.util.parsing.input.{OffsetPosition, Position, Reader}

class Scanner(lexer: Lexer, tokens: List[Token]) extends Reader[Token] {

  private lazy val current = tokens.head

  override def first: Token = current

  override def rest: Reader[Token] = new Scanner(lexer, tokens.tail)

  override def pos: Position = OffsetPosition(lexer.sourceCode, current.position.offset)

  override def atEnd: Boolean = current.kind == Tokens.EOF
}

object Scanner {

  def apply(lexer: Lexer): Scanner = {
    val tokens = LazyList
      .continually(lexer.nextToken())
      .takeWhile(_.kind != Tokens.EOF)
      .appended(Token(Tokens.EOF, "<EOF>", lexer.position))

    new Scanner(lexer, tokens.toList)
  }
}
