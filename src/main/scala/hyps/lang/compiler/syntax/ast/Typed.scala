package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.semantic.types.DataType
import hyps.lang.compiler.syntax.ast.Declaration.Decorator
import hyps.lang.compiler.syntax.ast.Statement.Block

/** Represents a program construct that is a typed.
  * It is used to distinguish between typed and other untyped constructs.
  */
trait Typed extends AST {

  def dataType: DataType

}

object Typed {

  case class TypedParameterDeclaration(name: String, dataType: DataType) extends Declaration with Typed {
    override def children(): List[AST] = Nil

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class TypedVariableDeclaration(name: String, initializer: Expression, dataType: DataType)
      extends Declaration
      with Typed {
    override def children(): List[AST] = List(initializer)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      if (newChildren.isEmpty) this
      else this.copy(initializer = newChildren.head.asInstanceOf[Expression])
  }

  case class TypedVariableReference(symbol: String, dataType: DataType) extends Reference {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class TypedFunctionDeclaration(identifier: String,
                                      annotations: List[Decorator],
                                      parameters: List[TypedParameterDeclaration],
                                      returnType: DataType,
                                      body: Block)
      extends Declaration {
    override def children(): List[AST] = List[AST](body)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      this.copy(body = newChildren.head.asInstanceOf[Block])
  }

}
