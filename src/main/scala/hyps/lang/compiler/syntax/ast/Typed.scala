package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.semantic.types.Type

/** Represents a program construct that is a typed.
  * It is used to distinguish between typed and other untyped constructs.
  */
trait Typed extends AST {

  def `type`: Type

}

object Typed {

  case class TypedVariableDeclaration(name: String, initializer: Expression, `type`: Type)
      extends Declaration
      with Typed {
    override def children(): List[AST] = List(initializer)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      if (newChildren.isEmpty) this
      else this.copy(initializer = newChildren.head.asInstanceOf[Expression])
  }

}
