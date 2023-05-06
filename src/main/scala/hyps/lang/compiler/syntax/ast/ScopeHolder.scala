package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.semantic.{Scope, Scopes}

trait ScopeHolder {

  private var scope: Scope = Scopes.EmptyScope

  def getScope: Scope = scope

  def setScope(scope: Scope): Unit =
    this.scope = scope

}
