package hyps.lang.compiler

import hyps.lang.compiler.semantic.analysis.SymbolTableConstructionPass
import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.parser.PclParser

object Compiler {

  def compile(sourceCode: String): AST = {
    val result = PclParser.parse("main", sourceCode)
    result match {
      case PclParser.Success(program, _) =>
        val boundProgram = program.transformEverywhere(new SymbolTableConstructionPass)
        boundProgram
      case failure: PclParser.NoSuccess =>
        throw CompilerError(Origin.begin("main"), failure.msg)
    }
  }

}
