package hyps.lang.compiler.builtin

import hyps.lang.compiler.semantic.types.Symbol.BuiltInTypeSymbol

object BuiltInTypes {

  val IntType     = BuiltInTypeSymbol("Int")
  val FloatType   = BuiltInTypeSymbol("Float")
  val StringType  = BuiltInTypeSymbol("String")
  val BooleanType = BuiltInTypeSymbol("Boolean")
  val UnitType    = BuiltInTypeSymbol("Unit")

}
