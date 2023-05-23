package hyps.lang.compiler.semantic.types

/** Used to track symbol definitions and identify them later on. */
trait SymbolTable {

  /** Saves a symbol in the current scope. */
  def declare(symbol: Symbol): Unit

  /** Enriches a symbol with additional information in the current scope. */
  def redefine(symbol: Symbol): Unit

  /** Searches for a symbol in the current scope and its enclosing scopes. */
  def resolve(symbol: String): Option[Symbol]

}
