package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.CompilerError
import hyps.lang.compiler.semantic.Symbol.VariableSymbol

trait Reference extends Expression

object Reference {

  case class VariableReference(symbol: VariableSymbol) extends Reference {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      newChildren match {
        case Nil => VariableReference(symbol)
        case _   => throw CompilerError(origin, s"Cannot construct VariableReference from $newChildren")
      }
  }

}
