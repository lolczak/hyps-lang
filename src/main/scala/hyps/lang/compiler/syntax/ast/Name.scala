package hyps.lang.compiler.syntax.ast

sealed trait Name extends AST

object Name {

    case class SimpleName(identifier: String) extends Name {
      override def traverse(traverser: Traverser): Unit = ()
      override def transform(transformer: Transformer): AST = this
    }

//  case class QualifiedName(qualifier: Name, identifier: String) extends Name


  case class TypeName(name: String) extends Name {
    override def traverse(traverser: Traverser): Unit = ()
    override def transform(transformer: Transformer): AST = this
  }


}
