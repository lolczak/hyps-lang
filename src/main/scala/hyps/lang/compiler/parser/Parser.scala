package hyps.lang.compiler.parser
import hyps.lang.compiler.ast.AST

class Parser(input: String) extends BacktrackingParser(new Lexer(input)) with ExprParser {

  def ast(): AST = expression()

}
