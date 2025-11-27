package hyps.lang.compiler.syntax.ast

trait Transformer {

  def transform(ast: AST): AST

  def transformStatement(stmt: Statement): Statement = ???

}
