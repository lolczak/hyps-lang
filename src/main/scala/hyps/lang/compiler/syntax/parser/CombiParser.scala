package hyps.lang.compiler.syntax.parser

import scala.util.parsing.combinator._

/** A parser designed for parsing Hyps source code, employs parser combinators, a widely-used library for parsing
  * in Scala. The parser implements a backtracking strategy to make parsing decisions and affords the flexibility of
  * arbitrary lookahead.
  */
class CombiParser extends Parsers with PackratParsers {

  override type Elem = Token

}
