package hyps.lang.compiler.syntax.ast

sealed trait ProgramQuark extends AST

object ProgramQuark {

  sealed trait Annotation extends ProgramQuark

  case class GenericAnnotation(name: String) extends Annotation {
    override def children(): List[AST] = List.empty

    override def withNewChildrenInternal(newChildren: List[AST]): AST = this
  }

  case class Program(modules: List[Module]) extends ProgramQuark {
    override def children(): List[AST] = modules

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      Program(newChildren.collect { case m: Module => m })
  }

  case class Module(name: String, definitions: List[Definition]) extends ProgramQuark {
    override def children(): List[AST] = definitions

    override def withNewChildrenInternal(newChildren: List[AST]): AST =
      this.copy(name = name, definitions = newChildren.collect { case d: Definition => d })
  }

}
