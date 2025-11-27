package hyps.lang.compiler.syntax.ast

import hyps.lang.compiler.semantic.types.DataType

/** Represents a program construct that is a typed.
  * It is used to distinguish between typed and other untyped constructs.
  */
trait Typed extends AST {

  def dataType: DataType

}

object Typed {}
