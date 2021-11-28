package hyps.lang.compiler

case class CompilerError(position: Position, message: String) extends Exception(message)