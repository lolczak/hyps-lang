package hyps.lang.compiler.parser
import hyps.lang.compiler.ast.Statement

import scala.collection.mutable.ListBuffer

class Parser(input: String) extends BacktrackingParser(new Lexer(input)) with ExprParser with StatementParser {

  def parse(): List[Statement] =
    program()

  protected def program(): List[Statement] = {
    val buffer = ListBuffer.empty[Statement]
    while (!isEnd) {
      buffer.append(statement())
    }
    buffer.toList
  }

}
