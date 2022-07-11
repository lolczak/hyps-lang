package hyps.lang.compiler

import hyps.lang.compiler.interpreter.Interpreter
import hyps.lang.compiler.parser.Parser

object Compiler {

  def compile(input: String): String = {
    val parser      = new Parser(input)
    val program     = parser.parse()
    val interpreter = new Interpreter(program)
    interpreter.interpret()
  }

}
