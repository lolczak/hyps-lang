package hyps.lang.compiler.syntax.ast

/* A top-level construct in the source code used represent the structure of program. */
sealed trait TopLevelConstruct extends AST

object TopLevelConstruct {

  //todo add Module

  case class Program(modules: List[SourceFile]) extends TopLevelConstruct {
    override def children(): List[AST] = modules

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      Program(newChildren.collect { case m: SourceFile => m })
  }

  /**
    * A source file containing a source code.
    *
    * @param name        the name of the source file
    * @param definitions the statements in the source file
    */
  case class SourceFile(name: String, definitions: List[Statement]) extends TopLevelConstruct {
    override def children(): List[AST] = definitions

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      this.copy(name = name, definitions = newChildren.collect { case s: Statement => s })
  }

}
