package hyps.lang.compiler

import hyps.lang.compiler.semantic.analysis.{SymbolTableConstructionPass, TypeInterferencePass}
import hyps.lang.compiler.syntax.ast.AST
import hyps.lang.compiler.syntax.parser.PclParser

object Compiler {

  def compile(sourceCode: String): AST = {
    val result = PclParser.parse("main", sourceCode)
    result match {
      case PclParser.Success(program, _) =>
        val boundProgram = program.transformEverywhere(new SymbolTableConstructionPass)
        val typedProgram = inferTypes(boundProgram)
        typedProgram
      case failure: PclParser.NoSuccess =>
        throw CompilerError(Origin.begin("main"), failure.msg)
    }
  }

  private def inferTypes(ast: AST): AST = {
    var footprintBefore = ast.hashCode()
    var current         = ast
    do {
      footprintBefore = current.hashCode()
      current         = current.transformEverywhere(TypeInterferencePass)
    } while (footprintBefore != current.hashCode())
    current
  }

}
