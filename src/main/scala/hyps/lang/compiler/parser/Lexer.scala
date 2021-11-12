package hyps.lang.compiler.parser

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.parser.Lexer.EOF

import scala.annotation.{switch, unused}
import scala.reflect.internal.Chars.{CR, LF}

class Lexer(input: String) {

  private var offset: Int = 0;

  @unused
  private var lookaheadChar: Char = input.charAt(offset)

  @unused
  private def consume(): Unit = {
    offset += 1
    if (offset < input.length) {
      lookaheadChar = input.charAt(offset)
    } else {
      lookaheadChar = EOF
    }
  }

  def nextToken(): Token = {
    while (lookaheadChar != EOF) {
      (lookaheadChar: @switch) match {
        case ' ' | '\t' =>
          consume()
        case LF | CR =>
          return newLine();
        case '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' =>
          return number()
        case '(' =>
          consume()
          return Token(Tokens.LeftParenthesis, offset - 1, "(")
        case ')' =>
          consume()
          return Token(Tokens.RightParenthesis, offset - 1, ")")
        case '{' =>
          consume()
          return Token(Tokens.LeftBrace, offset - 1, "{")
        case '}' =>
          consume()
          return Token(Tokens.RightBrace, offset - 1, "}")
        case 'A' | 'B' | 'C' | 'D' | 'E' |
             'F' | 'G' | 'H' | 'I' | 'J' |
             'K' | 'L' | 'M' | 'N' | 'O' |
             'P' | 'Q' | 'R' | 'S' | 'T' |
             'U' | 'V' | 'W' | 'X' | 'Y' |
             'Z' | '_' |
             'a' | 'b' | 'c' | 'd' | 'e' |
             'f' | 'g' | 'h' | 'i' | 'j' |
             'k' | 'l' | 'm' | 'n' | 'o' |
             'p' | 'q' | 'r' | 's' | 't' |
             'u' | 'v' | 'w' | 'x' | 'y' | // scala-mode: need to understand multi-line case patterns
             'z' =>
          return identifier()
        case '+' =>
          consume()
          return Token(Tokens.Plus, offset - 1, "+")
        case ':' =>
          consume()
          return Token(Tokens.Colon, offset - 1, ":")
        case other =>
          throw CompilerError(offset, s"Unrecognized character: $other")
      }
    }
    Token(Tokens.EOF, offset, "<EOF>")
  }

  private def number(): Token = {
    val numberOffset = offset
    val buffer = new StringBuilder()
    do {
      buffer.append(lookaheadChar)
      consume()
    } while (lookaheadChar.isDigit)
    Token(Tokens.IntLiteral, numberOffset, buffer.toString)
  }

  private def identifier(): Token = {
    val identifierOffset = offset
    val buffer = new StringBuilder()
    do {
      buffer.append(lookaheadChar)
      consume()
    } while (lookaheadChar.isLetter || lookaheadChar.isDigit || lookaheadChar == '_')
    buffer.toString() match {
      case "fn" => Token(Tokens.Fn, identifierOffset, "fn")
      case identifier => Token(Tokens.Identifier, identifierOffset, identifier)
    }
  }

  private def newLine(): Token = {
    (lookaheadChar: @switch) match {
      case LF =>
        consume()
        Token(Tokens.NewLine, offset - 1, "<EOL>")
      case CR =>
        consume()
        if (lookaheadChar == LF) {
          consume()
          Token(Tokens.NewLine, offset - 2, "<EOL>")
        } else {
          Token(Tokens.NewLine, offset - 1, "<EOL>")
        }
    }
  }

}

object Lexer {

  final val EOF: Char = (-1).toChar


}
