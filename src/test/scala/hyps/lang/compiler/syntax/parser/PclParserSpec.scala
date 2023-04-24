package hyps.lang.compiler.syntax.parser

import org.scalatest.flatspec.AnyFlatSpec

class PclParserSpec extends AnyFlatSpec {

  "Hyps parser" should "parse function declaration" in {
    val sourceCode =
      """fn main(): Unit {
        |  println("Hello, world!")
        |}
        |""".stripMargin
    val result = PclParser.parse("main", sourceCode)
    println(result)
  }

}
