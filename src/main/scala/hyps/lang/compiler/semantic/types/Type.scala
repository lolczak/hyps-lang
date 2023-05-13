package hyps.lang.compiler.semantic.types

/** Represents a program symbol that is a type.
  * It is used to distinguish between types and other symbols.
  */
trait Type {

  def qualifiedName: String

}
