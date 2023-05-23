package hyps.lang.compiler.syntax.ast

trait Reference extends Expression

object Reference {

  case class VariableReference(symbol: String) extends Reference {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

}
