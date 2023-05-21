package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.semantic.types.{Scope, Scopes}

/** Represents a program construct that is embedded in a recognized scope.
  * It is used to distinguish between scoped and other unscoped constructs.
  */
trait ScopeHolder {

  private var scope: Scope = Scopes.EmptyScope

  def getScope: Scope = scope

  def setScope(scope: Scope): Unit =
    this.scope = scope

}
