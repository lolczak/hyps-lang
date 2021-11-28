package hyps.lang.compiler

import hyps.lang.compiler.parser.{Lexer, Token, Tokens}

import scala.collection.mutable.ListBuffer

object Compiler {

  def compile(input: String): List[Token] = {
    val list= new ListBuffer[Token]()
    val lexer = new Lexer(input)
    var token = lexer.nextToken()
    while (token.`type` != Tokens.EOF) {
      list.append(token)
      token = lexer.nextToken()
    }
    list.toList
  }

}
