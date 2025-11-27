package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.syntax.ast.Name.SimpleName

trait Reference extends Expression

object Reference {

  case class SymbolReference(name: SimpleName) extends Reference {

    override def traverse(traverser: Traverser): Unit =
      traverser.visit(name)

    override def transform(transformer: Transformer): AST =
      SymbolReference(transformer.transform(name).asInstanceOf[SimpleName])
  }

}
