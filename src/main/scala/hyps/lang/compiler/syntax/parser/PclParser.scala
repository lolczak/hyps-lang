package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.syntax.ast.Declaration.VariableDeclaration
import hyps.lang.compiler.syntax.ast.ProgramQuark.{Module, Program}
import hyps.lang.compiler.syntax.ast.{Declaration, Expression, Statement}

import scala.util.parsing.combinator._

/** A parser designed for parsing Hyps source code, employs parser combinators, a widely-used library for parsing
  * in Scala. The parser implements a backtracking strategy to make parsing decisions and affords the flexibility of
  * arbitrary lookahead.
  */
object PclParser extends Parsers with PackratParsers {

  override type Elem = Token

  def parse(name: String, sourceCode: String): ParseResult[Program] = {
    val lexer       = new Lexer(name, sourceCode)
    val scanner     = new Scanner(lexer)
    val matchModule = repsep(declaration, statementDelimiter).apply(_) //syntactic sugar
    matchModule(scanner).map(declarations => Program(List(Module(name, declarations))))
  }

  private lazy val statementDelimiter: Parser[Unit] =
    rep1(matchToken(Tokens.SEMICOLON) | matchToken(Tokens.NEW_LINE)) ^^^ ()

  private lazy val declaration: Parser[Declaration] = functionDeclaration | variableDeclaration

  private lazy val block: Parser[Statement.Block] =
    (lbrace ~ opt(statementDelimiter)) ~> separatedSequence(statement, statementDelimiter, blockEnd) map Statement.Block

  private lazy val blockEnd = rbrace | (statementDelimiter ~ rbrace)

  private lazy val statement: Parser[Statement] =
    variableDeclaration | printlnStatement

  private lazy val printlnStatement: Parser[Statement.PrintlnStatement] =
    (matchToken(Tokens.PRINTLN) ~> (lparen ~> expression) <~ rparen) map Statement.PrintlnStatement

  private lazy val functionDeclaration: Parser[Declaration.FunctionDeclaration] =
    matchToken(Tokens.FN) ~> identifier ~ (lparen ~> repsep(parameterDeclaration, comma)) ~
    (rparen ~> colon ~> identifier <~ opt(statementDelimiter)) ~ block map {
      case name ~ parameters ~ returnType ~ body =>
        Declaration.FunctionDeclaration(name.lexeme, List.empty, parameters, returnType.lexeme, body)
    }

  private lazy val parameterDeclaration: Parser[Declaration.ParameterDeclaration] =
    identifier ~ (colon ~> identifier) map {
      case name ~ parameterType => Declaration.ParameterDeclaration(name.lexeme, parameterType.lexeme)
    }

  private lazy val variableDeclaration: Parser[VariableDeclaration] =
    matchToken(Tokens.VAR) ~> identifier ~ (colon ~> identifier) ~ (matchToken(Tokens.EQUAL) ~> expression) map {
      case name ~ varType ~ initializer => VariableDeclaration(name.lexeme, varType.lexeme, initializer)
    }

  private lazy val expression: Parser[Expression] = stringLiteral | nullLiteral //todo add symbol

  /** Token matchers */
  private lazy val stringLiteral: Parser[Expression] = matchToken(Tokens.STRING) map { token =>
      Expression.StringLiteral(token.lexeme)
    }

  private lazy val nullLiteral: Parser[Expression] = matchToken(Tokens.NULL) ^^^ Expression.NullLiteral
  private lazy val colon: Parser[Token]            = matchToken(Tokens.COLON)
  private lazy val comma: Parser[Token]            = matchToken(Tokens.COMMA)
  private lazy val lparen: Parser[Token]           = matchToken(Tokens.LEFT_PAREN)
  private lazy val rparen: Parser[Token]           = matchToken(Tokens.RIGHT_PAREN)
  private lazy val lbrace: Parser[Token]           = matchToken(Tokens.LEFT_BRACE)
  private lazy val rbrace: Parser[Token]           = matchToken(Tokens.RIGHT_BRACE)
  private lazy val identifier: Parser[Token]       = matchToken(Tokens.IDENTIFIER)

  /**
    * Parses a single token of the given kind.
    *
    * @param kind the kind of token to parse
    * @return a matching token
    */
  def matchToken(kind: Int): Parser[Token] = acceptIf(_.kind == kind)(found => s"Expected $kind, but found ${found}")

  /**
    * The `eot` parser succeeds if we are at the end of input, and fails otherwise.
    * This is useful for parsers that expect to parse all of the input, such as
    * parsers for entire files.
    */
  lazy val eot: Parser[Unit] =
    Parser { in =>
      if (in.atEnd) Success((), in)
      else Failure("no end of input", in)
    }

  /**
    * A parser combinator for parsing a sequence of terms separated by a separator.
    * It tries to match term zero or more times until the parser `end` succeeds
    *
    * @param termParser parses a single term
    * @param end when this parser succeeds, the `separatedSequence` parser will succeed
    * @return the resulting list of terms
    */
  def repTill[T](termParser: => Parser[T], end: => Parser[Any]): Parser[List[T]] =
    end ^^^ List.empty | (termParser ~ repTill(termParser, end)) ^^ { case x ~ xs => x :: xs }

  /**
    * A parser combinator for parsing a sequence of terms separated by a separator.
    * It tries to match term one or more times until the parser `end` succeeds
    *
    * @param termParser parses a single term
    * @param separator separates terms
    * @param end when this parser succeeds, the `separatedSequence` parser will succeed
    * @return the resulting list of terms
    */
  def separatedSequence[T](termParser: => Parser[T], separator: => Parser[Any], end: => Parser[Any]): Parser[List[T]] =
    for {
      x  <- termParser
      xs <- repTill(separator ~> termParser, end)
    } yield x :: xs

}
