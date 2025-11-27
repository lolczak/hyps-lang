package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.Compiler
import org.scalatest.flatspec.AnyFlatSpec

class PclParserSpec extends AnyFlatSpec {

  "Hyps parser" should "parse function declaration" in {
    val sourceCode =
      """namespace Main {
        |  fn main(): Unit = {
        |    val message = "Hello, world!"
        |    println(message)
        |  }
        |}
        |""".stripMargin

    val result = Compiler.compile(sourceCode)
    println(result)
  }

}
