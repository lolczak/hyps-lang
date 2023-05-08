package hyps.lang.compiler.semantic.types

object Scopes {

  val BuiltInScope: Scope = {
    val scope = new Scope("BuiltInScope", None)
    scope.declare(Symbol.BuiltInTypeSymbol("Int"))
    scope.declare(Symbol.BuiltInTypeSymbol("Float"))
    scope.declare(Symbol.BuiltInTypeSymbol("String"))
    scope.declare(Symbol.BuiltInTypeSymbol("Boolean"))
    scope
  }

  val GlobalScope: Scope = new Scope("GlobalScope", Some(BuiltInScope))

  val EmptyScope: Scope = new Scope("EmptyScope", None)

}
