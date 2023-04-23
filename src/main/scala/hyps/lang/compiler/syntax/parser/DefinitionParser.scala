package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.syntax.ast.Definition._
import hyps.lang.compiler.syntax.ast.ProgramQuark.Annotation

trait DefinitionParser { this: BacktrackingParser =>

  def functionDef(): FunctionDef = {
    val annotations = matchList { annotation() }
    val _ = matchToken(Tokens.FN)
    val identifier = matchToken(Tokens.IDENTIFIER)
    val parameters = matchListDelimited(parameter(), Tokens.COMMA)
    val returnType = returnType()

    FunctionDef(identifier, annotations, parameters, body)
  }

  def annotation(): Annotation = {
  }

}
