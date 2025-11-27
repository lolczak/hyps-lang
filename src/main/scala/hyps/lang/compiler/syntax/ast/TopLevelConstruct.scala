package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.syntax.ast.Declaration.FunctionDeclaration

/* A top-level construct in the source code used represent the structure of program. */
sealed trait TopLevelConstruct extends AST

object TopLevelConstruct {

  case class NamespaceDeclaration(name: Name, body: List[FunctionDeclaration]) extends TopLevelConstruct {

    override def traverse(traverser: Traverser): Unit = {
      traverser.visit(name)
      body.foreach(traverser.visit)
    }

    override def transform(transformer: Transformer): AST =
      NamespaceDeclaration(
        transformer.transform(name).asInstanceOf[Name],
        body.map(f => transformer.transform(f).asInstanceOf[FunctionDeclaration])
      )
  }

}
