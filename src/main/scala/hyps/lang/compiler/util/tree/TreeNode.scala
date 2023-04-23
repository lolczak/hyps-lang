package hyps.lang.compiler.util.tree

import hyps.lang.compiler.prelude._

trait TreeNode[A <: TreeNode[A]] { self: A =>

  def children(): List[A]

  def withNewChildrenInternal(newChildren: List[A]): A

  def transformEverywhere(compilerPass: A =|> A): A = {
    val afterRule = compilerPass.applyOrElse(self, identity[A])
    afterRule.mapChildren(_.transformEverywhere(compilerPass))
  }

  def mapChildren(compilerPass: A =|> A): A =
    withNewChildrenInternal(children().map(compilerPass.applyOrElse(_, identity[A])))

}
