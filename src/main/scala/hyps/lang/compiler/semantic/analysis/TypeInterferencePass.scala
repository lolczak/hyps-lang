package hyps.lang.compiler.semantic.analysis

import hyps.lang.compiler.syntax.ast.Declaration.VariableDeclaration
import hyps.lang.compiler.syntax.ast.{AST, Typed}
import hyps.lang.compiler.util.tree.TreeRewriter

/** A transformation that infers the type of untyped constructs.
  */
object TypeInterferencePass extends TreeRewriter[AST] {

  rewrite {
    case varDecl @ VariableDeclaration(name, None, initializer) =>
      initializer match {
        case typedExpr: Typed => Typed.TypedVariableDeclaration(name, initializer, typedExpr.`type`)
        case _                => varDecl
      }
  }

}
