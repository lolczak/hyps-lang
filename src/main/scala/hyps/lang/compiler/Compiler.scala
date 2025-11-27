package hyps.lang.compiler

import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.parser.PclParser

object Compiler {

  def compile(sourceCode: String): AST = {
    val result = PclParser.parseStatement("main", sourceCode)
    result match {
      case PclParser.Success(program, _) =>
        program
      case failure: PclParser.NoSuccess =>
        throw CompilerError(Origin.begin("main"), failure.msg)
    }
  }

}
