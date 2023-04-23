package hyps.lang.compiler

case class CompilerError(position: Origin, message: String) extends Exception(message)