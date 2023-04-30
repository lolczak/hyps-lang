package hyps.lang.compiler.semantic

trait SymbolType

object SymbolType {

  case object VariableSymbol extends SymbolType
  case object FunctionSymbol extends SymbolType
  case object ClassSymbol extends SymbolType
  case object BuiltInSymbol extends SymbolType

}
