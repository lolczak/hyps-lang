package hyps.lang.compiler.semantic.types

import hyps.lang.compiler.builtin.BuiltinTypes

object Scopes {

  val BuiltInScope: Scope = {
    val scope = new Scope("BuiltInScope", None)
    scope.declare(Symbol.BuiltInTypeSymbol(BuiltinTypes.IntType))
    scope.declare(Symbol.BuiltInTypeSymbol(BuiltinTypes.FloatType))
    scope.declare(Symbol.BuiltInTypeSymbol(BuiltinTypes.StringType))
    scope.declare(Symbol.BuiltInTypeSymbol(BuiltinTypes.BooleanType))
    scope
  }

  val GlobalScope: Scope = new Scope("GlobalScope", Some(BuiltInScope))

  val EmptyScope: Scope = new Scope("EmptyScope", None)

}
