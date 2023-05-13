package hyps.lang.compiler.util.tree

import scala.collection.mutable

/** A compiler pass is a transformation that is applied to the AST.
  * It is a function that takes an AST node and returns a new AST node.
  * It can be applied to the AST node itself and to all its children.
  * It can also be applied before or after the transformation of the children.
  */
abstract class TreeRewriter[A] {

  private val beforeFns                           = mutable.ListBuffer.empty[PartialFunction[A, Unit]]
  private val afterFns                            = mutable.ListBuffer.empty[PartialFunction[A, Unit]]
  private var transformFns: PartialFunction[A, A] = { case node => node }

  final def enterNode(node: A): Unit = beforeFns.foreach(_.applyOrElse(node, (_: A) => ()))

  final def exitNode(node: A): Unit = afterFns.foreach(_.applyOrElse(node, (_: A) => ()))

  final def transformNode(node: A): A = transformFns.applyOrElse(node, identity[A])

  final protected def before(compilerPass: PartialFunction[A, Unit]): Unit = beforeFns += compilerPass

  final protected def after(compilerPass: PartialFunction[A, Unit]): Unit = afterFns += compilerPass

  final protected def transform(compilerPass: PartialFunction[A, A]): Unit = transformFns = compilerPass

}
