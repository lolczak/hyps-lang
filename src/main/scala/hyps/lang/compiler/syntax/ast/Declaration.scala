package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.syntax.ast.Statement.Block

/** A base trait of all declaration statement. */
sealed trait Declaration extends Statement

object Declaration {

  /** Decorates code constructs with additional semantic information. */
  sealed trait Decorator extends Declaration

  case class GenericAnnotation(name: String) extends Decorator {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class ParameterDeclaration(name: String, parameterType: String) extends Declaration {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class FunctionDeclaration(identifier: String,
                                 annotations: List[Decorator],
                                 parameters: List[ParameterDeclaration],
                                 returnType: String,
                                 body: Block)
      extends Declaration {
    override def children(): List[AST] = List[AST](body)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      this.copy(body = newChildren.head.asInstanceOf[Block])
  }

  case class VariableDeclaration(name: String, varType: Option[String], initializer: Expression) extends Declaration {
    override def children(): List[AST] = List(initializer)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      if (newChildren.isEmpty) this
      else this.copy(initializer = newChildren.head.asInstanceOf[Expression])
  }

}
