package hyps.lang.compiler.semantic

import hyps.lang.compiler.syntax.ast.AST

/**
  * Represents a program entity that must be identified by name.
  */
trait Symbol {
  def name: String
}

object Symbol {

  /** Represents built-in types that are available in the language without compiling definition. */
  case class BuiltInTypeSymbol(name: String) extends Symbol with Type

  case class VariableSymbol(name: String, ref: AST, dataType: Option[Type]) extends Symbol
//  case object ClassSymbol    extends Symbol
//  case object FunctionSymbol extends Symbol
//  case object MethodSymbol   extends Symbol

}
