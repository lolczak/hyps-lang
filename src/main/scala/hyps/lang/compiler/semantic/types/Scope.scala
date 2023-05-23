package hyps.lang.compiler.semantic.types

import java.util.UUID
import scala.collection.mutable

/**
  * A scope represents a code region with well-defined boundaries.
  * It groups together all the symbol definitions in a dictionary associated with that code section.
  */
class Scope(parent: Option[Scope] = None) extends SymbolTable {

  private val id: String = UUID.randomUUID.toString

  private val members: mutable.Map[String, Symbol] = mutable.Map.empty

  def getScopeId(): String = id

  def getEnclosingScope(): Option[Scope] = parent

  def declare(symbol: Symbol): Unit =
    members.update(symbol.name, symbol)

  def redefine(symbol: Symbol): Unit =
    if (members.contains(symbol.name)) {
      members.update(symbol.name, symbol)
    } else {
      throw new Exception(s"Symbol not found $symbol")
    }

  def resolve(symbol: String): Option[Symbol] =
    members.get(symbol).orElse(parent.flatMap(_.resolve(symbol)))

  def resolveType(symbol: String): Option[DataType] =
    resolve(symbol).flatMap {
      case symbol: DataType => Some(symbol)
      case _                => None
    }
}
