package hyps.lang.compiler.parser

import hyps.lang.compiler.parser.Lexer.{EOF, Keywords}
import hyps.lang.compiler.parser.Tokens._
import hyps.lang.compiler.{CompilerError, Position}

import scala.annotation.switch
import scala.reflect.internal.Chars.{CR, LF}

class Lexer(sourceCode: String) {

  private var position: Position = Position.Begin

  private var lookaheadChar: Char = sourceCode.charAt(position.offset)

  private def consume(isNewLine: Boolean = false): Unit = {
    if (isNewLine) {
      position = position.advanceLine()
    } else {
      position = position.advanceColumn()
    }
    if (position.offset < sourceCode.length) {
      lookaheadChar = sourceCode.charAt(position.offset)
    } else {
      lookaheadChar = EOF
    }
  }

  def nextToken(): Token = {
    val LF = 10.toChar
    val CR = 13.toChar
    while (lookaheadChar != Lexer.EOF) {
      implicit val startPosition: Position = position
      (lookaheadChar) match {
        case ' ' | '\t' | '\r' =>
          consume()
        case `LF` | `CR` =>
          return newLine()
        case '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' =>
          return number()
        case '(' =>
          consume()
          return newToken(LEFT_PAREN)
        case ')' =>
          consume()
          return newToken(RIGHT_PAREN)
        case '{' =>
          consume()
          return newToken(LEFT_BRACE)
        case '}' =>
          consume()
          return newToken(RIGHT_BRACE, "}")
        case ',' =>
          consume()
          return newToken(COMMA)
        case '+' =>
          consume()
          return newToken(PLUS)
        case '-' =>
          consume()
          return newToken(Tokens.MINUS)
        case '*' =>
          consume()
          return newToken(STAR)
        case ':' =>
          consume()
          return newToken(Tokens.COLON)
        case '.' =>
          consume()
          return newToken(DOT)
        case ';' =>
          consume()
          return newToken(Tokens.SEMICOLON)
        case '!' =>
          consume()
          if (lookaheadChar == '=') {
            consume()
            return newToken(BANG_EQUAL, "!=")
          } else {
            return newToken(BANG)
          }
        case '=' =>
          consume()
          if (lookaheadChar == '=') {
            consume()
            return newToken(EQUAL_EQUAL, "==")
          } else {
            return newToken(EQUAL_EQUAL)
          }
        case '<' =>
          consume()
          if (lookaheadChar == '=') {
            consume()
            return newToken(LESS_EQUAL, "<=")
          } else {
            return newToken(LESS)
          }
        case '>' =>
          consume()
          if (lookaheadChar == '=') {
            consume()
            return newToken(GREATER_EQUAL, ">=")
          } else {
            return newToken(GREATER)
          }
        case 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' | 'N' | 'O' | 'P' | 'Q' | 'R' |
            'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z' | '_' | 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' |
            'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' |
            'y' | // scala-mode: need to understand multi-line case patterns
            'z' =>
          return identifier()
        case '"' =>
          return string()
        case '/' =>
          consume()
          if (lookaheadChar == '/') {
            while (lookaheadChar != Lexer.EOF && lookaheadChar != CR && lookaheadChar != LF) {
              consume()
            }
          } else {
            return newToken(SLASH)
          }
        case other =>
          throw CompilerError(position, s"Unrecognized character: $other")
      }
    }
    Token(Tokens.EOF, "<EOF>", position)
  }

  private def number(): Token = {
    val startPosition = position
    val buffer        = new StringBuilder()
    do {
      buffer.append(lookaheadChar)
      consume()
    } while (lookaheadChar.isDigit)
    Token(NUMBER, buffer.toString, startPosition)
  }

  private def string(): Token = {
    val startPosition = position
    val buffer        = new StringBuilder()
    do {
      buffer.append(lookaheadChar)
      consume()
    } while (lookaheadChar != '"' && lookaheadChar != Lexer.EOF)
    consume()
    Token(STRING, buffer.toString(), startPosition)
  }

  private def identifier(): Token = {
    val startPosition = position
    val buffer        = new StringBuilder()
    do {
      buffer.append(lookaheadChar)
      consume()
    } while (lookaheadChar.isLetter || lookaheadChar.isDigit || lookaheadChar == '_')
    buffer.toString() match {
      case keyword if Keywords.contains(keyword) => Token(Keywords(keyword), keyword, startPosition)
      case identifier                            => Token(IDENTIFIER, identifier, startPosition)
    }
  }

  private def newLine(): Token = {
    val startPosition = position
    (lookaheadChar: @switch) match {
      case LF =>
        consume(true)
        Token(Tokens.NEW_LINE, "<EOL>", startPosition)
      case CR =>
        if (peekNext() == LF) {
          consume()
          consume(true)
          Token(Tokens.NEW_LINE, "<EOL>", startPosition)
        } else {
          consume(true)
          Token(Tokens.NEW_LINE, "<EOL>", startPosition)
        }
    }
  }

  /**
    * Peeks a second character of lookahead.
    *
    * @return a second character of lookahead or '\0' char.
    */
  private def peekNext(): Char =
    if (position.offset + 1 < sourceCode.length) {
      sourceCode.charAt(position.offset + 1)
    } else {
      Lexer.EOF
    }

  private def newToken(`type`: Int)(implicit startPosition: Position): Token =
    newToken(`type`, sourceCode.charAt(startPosition.offset).toString)

  private def newToken(`type`: Int, lexeme: String)(implicit startPosition: Position): Token =
    Token(`type`, lexeme, startPosition)

}

object Lexer {

  final val EOF: Char = '\u0000'

  final val Keywords: Map[String, Int] =
    Map(
      "and"    -> AND,
      "class"  -> CLASS,
      "else"   -> ELSE,
      "false"  -> FALSE,
      "for"    -> FOR,
      "fn"     -> FN,
      "if"     -> IF,
      "nil"    -> NIL,
      "or"     -> OR,
      "return" -> RETURN,
      "super"  -> SUPER,
      "this"   -> THIS,
      "true"   -> TRUE,
      "var"    -> VAR,
      "let"    -> LET,
      "while"  -> WHILE
    )

}
