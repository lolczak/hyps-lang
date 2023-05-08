package hyps.lang.compiler.syntax.parser

import hyps.lang.compiler.Compiler
import org.scalatest.flatspec.AnyFlatSpec

class PclParserSpec extends AnyFlatSpec {

  "Hyps parser" should "parse function declaration" in {
    val sourceCode =
      """fn main(): Unit {
        |  var message = "Hello, world!"
        |  println(message)
        |}
        |""".stripMargin

    val result = Compiler.compile(sourceCode)
    println(result)
  }

}
