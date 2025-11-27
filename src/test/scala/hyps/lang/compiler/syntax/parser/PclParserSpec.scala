package hyps.lang.compiler.syntax.parser

import org.scalatest.flatspec.AnyFlatSpec

class PclParserSpec extends AnyFlatSpec {

  "Hyps parser" should "parse function declaration" in {
    val sourceCode =
      """namespace Main { fn foo(): Unit = { var msg: String = "foo"; println(msg) } }""".stripMargin

    val result = PclParser.parse("Main.hyps", sourceCode)
    println(result)
  }

}
