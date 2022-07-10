package hyps.lang.compiler.parser

import hyps.lang.compiler.Position

case class Token(kind: Int, lexeme: String, position: Position)
