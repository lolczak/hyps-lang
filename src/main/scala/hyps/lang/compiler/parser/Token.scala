package hyps.lang.compiler.parser

import hyps.lang.compiler.Position

case class Token(`type`: Int, lexeme: String, position: Position)
