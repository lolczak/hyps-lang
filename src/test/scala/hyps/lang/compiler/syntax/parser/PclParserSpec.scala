package hyps.lang.compiler.syntax.parser

import org.scalatest.flatspec.AnyFlatSpec

class PclParserSpec extends AnyFlatSpec {

  "Hyps parser" should "parse function declaration" in {
    val sourceCode =
      """fn main(): Unit {
        |  println("Hello, world!")
        |}
        |""".stripMargin

    val lexer = new Lexer("test", sourceCode)
    var token: Token = null
    do {
      token = lexer.nextToken()
      println(token)
    } while (token.kind != Tokens.EOF)

    val result = PclParser.parse("main", sourceCode)
    println(result)
  }

}
