package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.Origin

case class Token(kind: Int, lexeme: String, position: Origin)
