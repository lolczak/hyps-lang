package hyps.lang.compiler.semantic.types

import hyps.lang.compiler.syntax.ast.AST

/**
  * Represents a program entity that must be identified by name.
  */
trait Symbol {
  def name: String
}

object Symbol {

  trait SymbolType extends Symbol with DataType

  /** Represents built-in types that are available in the language without compiling definition. */
  case class BuiltInTypeSymbol(qualifiedName: String) extends SymbolType {
    override def name: String = qualifiedName
  }

  case class VariableSymbol(name: String, declaration: AST, dataType: Option[DataType] = None) extends Symbol
  case class FunctionSymbol(name: String, declaration: AST, dataType: Option[DataType] = None) extends Symbol

}
