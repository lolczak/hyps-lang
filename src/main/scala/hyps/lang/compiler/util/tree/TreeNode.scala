package hyps.lang.compiler.util.tree

trait TreeNode[A <: TreeNode[A]] { self: A =>

  def children(): List[A]

  def withNewChildrenInternal(newChildren: List[A]): A

  def transformEverywhere(compilerPass: TreeRewriter[A]): A = {
    compilerPass.enterNode(self)
    val afterRule = compilerPass.transformNode(self)
    val finalNode = afterRule.mapChildren(compilerPass)
    compilerPass.exitNode(finalNode)
    finalNode
  }

  def mapChildren(compilerPass: TreeRewriter[A]): A =
    withNewChildrenInternal(children().map(_.transformEverywhere(compilerPass)))

}
