package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.Position

case class Token(kind: Int, lexeme: String, position: Position)
