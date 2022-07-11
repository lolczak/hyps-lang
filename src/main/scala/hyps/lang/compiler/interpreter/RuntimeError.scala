package hyps.lang.compiler.interpreter

case class RuntimeError(message: String) extends Exception(message)
