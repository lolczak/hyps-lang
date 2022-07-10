package hyps.lang.compiler

import hyps.lang.compiler.ast.AST
import hyps.lang.compiler.parser.Parser

object Compiler {

  def compile(input: String): AST = {
    val parser = new Parser(input)
    parser.parse()
  }

}
