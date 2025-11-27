package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.syntax.ast.Name.{SimpleName, TypeName}
import hyps.lang.compiler.syntax.ast.Statement.Block

/** A base trait of all declaration statement. */
trait Declaration extends Statement

object Declaration {

  /** Decorates code constructs with additional semantic information. */
  sealed trait Decorator extends Declaration

  case class GenericAnnotation(name: SimpleName) extends Decorator {

    override def traverse(traverser: Traverser): Unit =
      traverser.visit(name)

    override def transform(transformer: Transformer): AST =
      GenericAnnotation(transformer.transform(name).asInstanceOf[SimpleName])
  }

  case class ParameterDeclaration(name: SimpleName, parameterType: TypeName) extends Declaration {

    override def traverse(traverser: Traverser): Unit = {
      traverser.visit(name)
      traverser.visit(parameterType)
    }

    override def transform(transformer: Transformer): AST =
      ParameterDeclaration(
        transformer.transform(name).asInstanceOf[SimpleName],
        transformer.transform(parameterType).asInstanceOf[TypeName]
      )
  }

  case class FunctionDeclaration(identifier: SimpleName,
                                 annotations: List[Decorator],
                                 parameters: List[ParameterDeclaration],
                                 returnType: TypeName,
                                 body: Block)
      extends Declaration {

    override def traverse(traverser: Traverser): Unit = {
      traverser.visit(identifier)
      annotations.foreach(traverser.visit)
      parameters.foreach(traverser.visit)
      traverser.visit(returnType)
      traverser.visit(body)
    }

    override def transform(transformer: Transformer): AST =
      FunctionDeclaration(
        identifier,
        annotations.map(a => transformer.transform(a).asInstanceOf[Decorator]),
        parameters.map(p => transformer.transform(p).asInstanceOf[ParameterDeclaration]),
        returnType,
        transformer.transform(body).asInstanceOf[Block]
      )
  }

  case class VariableDeclaration(name: SimpleName, varType: Option[TypeName], initializer: Expression)
      extends Declaration {

    override def traverse(traverser: Traverser): Unit = {
      traverser.visit(name)
      varType.foreach(traverser.visit)
      traverser.visit(initializer)
    }

    override def transform(transformer: Transformer): AST =
      VariableDeclaration(
        name,
        varType.map(t => transformer.transform(t).asInstanceOf[TypeName]),
        transformer.transform(initializer).asInstanceOf[Expression]
      )
  }
}
