package hyps.lang.compiler.interpreter

sealed trait RuntimeValue

object RuntimeValue {

  case class StringValue(value: String)   extends RuntimeValue
  case class BooleanValue(value: Boolean) extends RuntimeValue
  case class DecimalValue(value: Long)    extends RuntimeValue
  case object NilValue                    extends RuntimeValue
  case object UndefinedValue              extends RuntimeValue

}
