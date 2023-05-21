package hyps.lang.compiler.semantic.types

import hyps.lang.compiler.syntax.ast.AST

/**
  * Represents a program entity that must be identified by name.
  */
trait Symbol {
  def name: String
}

object Symbol {

  trait SymbolType extends Symbol with Type

  /** Represents built-in types that are available in the language without compiling definition. */
  case class BuiltInTypeSymbol(qualifiedName: String) extends SymbolType {
    override def name: String = qualifiedName
  }

  trait SymbolReference extends Symbol {

    var dataType: Option[Type] = None
    //var memoryLocation: Option[MemoryLocation] = None

  }

  case class VariableSymbol(name: String, declaration: AST) extends SymbolReference
  case class FunctionSymbol(name: String, declaration: AST) extends SymbolReference

}
