package hyps.lang.compiler.ast

trait AstVisitor[A] {

  def visitStringLiteral(literal: Expr.StringLiteral): A

  def visitNumberLiteral(literal: Expr.NumberLiteral): A

  def visitExprGrouping(expr: Expr.Grouping): A

  def visitUnaryExpr(expr: Expr.Unary): A

  def visitBooleanLiteral(expr: Expr.BooleanLiteral): A

  def visitNil(expr: Expr.Nil.type): A

  def visitMultiplication(expr: Expr.Multiplication): A

  def visitDivision(expr: Expr.Division): A

  def visitAddition(expr: Expr.Addition): A

  def visitSubtraction(expr: Expr.Subtraction): A

  def visitComparison(expr: Expr.Comparison): A

  def visitEquality(expr: Expr.Equality): A

}
