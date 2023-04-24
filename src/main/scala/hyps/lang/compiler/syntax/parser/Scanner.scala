package hyps.lang.compiler.syntax.parser

import scala.util.parsing.input.{OffsetPosition, Position, Reader}

class Scanner(lexer: Lexer) extends Reader[Token] {

  private lazy val current = lexer.nextToken()

  override def first: Token = current

  override def rest: Reader[Token] = new Scanner(lexer)

  override def pos: Position = OffsetPosition(lexer.sourceCode, current.position.offset)

  override def atEnd: Boolean = current.kind == Tokens.EOF
}
