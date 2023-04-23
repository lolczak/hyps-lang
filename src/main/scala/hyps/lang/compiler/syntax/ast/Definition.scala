package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.syntax.ast.ProgramQuark.Annotation
import hyps.lang.compiler.syntax.ast.Statement.Block

sealed trait Definition extends AST

object Definition {

  case class ParameterDef(identifier: String, parameterType: String) extends Definition {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class FunctionDef(identifier: String, annotations: List[Annotation], parameters: List[ParameterDef], body: Block)
      extends Definition {
    override def children(): List[AST] = List[AST](body)

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      this.copy(body = newChildren.head.asInstanceOf[Block])
  }

  case class VariableDecl(identifier: String, varType: String, maybeInitializer: Option[Expr]) extends Definition {
    override def children(): List[AST] = maybeInitializer.toList

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      if (newChildren.isEmpty) this
      else this.copy(maybeInitializer = Some(newChildren.head.asInstanceOf[Expr]))
  }

}
