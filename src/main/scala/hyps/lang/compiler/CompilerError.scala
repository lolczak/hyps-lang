package hyps.lang.compiler

case class CompilerError(offset: Int, message: String) extends Exception(message)