package hyps.lang.compiler

import hyps.lang.compiler.syntax.parser.Parser

object Compiler {

  def compile(input: String): String = {
    val parser      = new Parser(input)
    val program     = parser.parse()
    ""
  }

}
