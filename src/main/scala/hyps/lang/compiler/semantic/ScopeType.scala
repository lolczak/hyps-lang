package hyps.lang.compiler.semantic

trait ScopeType

object ScopeType {

  case object GlobalScope extends ScopeType
  case object LocalScope extends ScopeType
  case object BuiltInScope extends ScopeType
  case object ClassScope extends ScopeType

}