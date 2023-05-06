package hyps.lang.compiler.semantic

/** Used to track symbol definitions and identify them later on. */
trait SymbolTable {

  /** Saves a symbol in the current scope. */
  def declare(symbol: Symbol): Unit

  /** Searches for a symbol in the current scope and its enclosing scopes. */
  def resolve(symbol: String): Option[Symbol]

}
