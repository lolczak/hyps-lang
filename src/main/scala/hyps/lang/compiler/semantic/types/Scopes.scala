package hyps.lang.compiler.semantic.types

import hyps.lang.compiler.builtin.BuiltInTypes

object Scopes {

  val BuiltInScope: Scope = {
    val scope = new Scope("BuiltInScope", None)
    scope.declare(BuiltInTypes.IntType)
    scope.declare(BuiltInTypes.FloatType)
    scope.declare(BuiltInTypes.StringType)
    scope.declare(BuiltInTypes.BooleanType)
    scope
  }

  val GlobalScope: Scope = new Scope("GlobalScope", Some(BuiltInScope))

  val EmptyScope: Scope = new Scope("EmptyScope", None)

}
