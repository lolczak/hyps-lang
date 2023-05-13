package hyps.lang.compiler.semantic.types

import hyps.lang.compiler.syntax.ast.AST

/**
  * Represents a program entity that must be identified by name.
  */
trait Symbol {
  def name: String
}

object Symbol {

  /** Represents built-in types that are available in the language without compiling definition. */
  case class BuiltInTypeSymbol(qualifiedName: String) extends Symbol with Type {
    override def name: String = qualifiedName
  }

  case class VariableSymbol(name: String, ref: AST, dataType: Option[Type]) extends Symbol
  case class FunctionSymbol(name: String, ref: AST)                         extends Symbol
//  case object ClassSymbol    extends Symbol
//  case object FunctionSymbol extends Symbol
//  case object MethodSymbol   extends Symbol

}
