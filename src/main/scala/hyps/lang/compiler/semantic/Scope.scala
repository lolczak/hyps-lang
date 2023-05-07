package hyps.lang.compiler.semantic

import scala.collection.mutable

/**
  * A scope represents a code region with well-defined boundaries.
  * It groups together all the symbol definitions in a dictionary associated with that code section.
  */
class Scope(name: String, parent: Option[Scope] = None) extends SymbolTable {

  private val members: mutable.Map[String, Symbol] = mutable.Map.empty

  def getScopeName(): String = name

  def getEnclosingScope(): Option[Scope] = parent

  def declare(symbol: Symbol): Unit =
    members.update(symbol.name, symbol)

  def resolve(symbol: String): Option[Symbol] =
    members.get(symbol).orElse(parent.flatMap(_.resolve(symbol)))

  def resolveType(symbol: String): Option[Type] =
    resolve(symbol).flatMap {
      case symbol: Type => Some(symbol)
      case _            => None
    }
}
