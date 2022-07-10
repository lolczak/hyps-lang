package hyps.lang

import hyps.lang.compiler.{Compiler, CompilerError}

import java.io.{BufferedReader, InputStreamReader}
import scala.util.control.NonFatal

object Hyps {

  def main(args: Array[String]): Unit =
    runRepl()

  def runRepl(): Unit = {
    val input  = new InputStreamReader(System.in)
    val reader = new BufferedReader(input)

    while (true) {
      print("> ")
      val line = reader.readLine()
      if (line == null) {
        System.exit(0)
      }
      run(line)
    }
  }

  private def run(source: String): Unit =
    try {
      val ast = Compiler.compile(source)
      println(ast)
    } catch {
      case CompilerError(position, msg) =>
        System.err.println(s"[${position.line}:${position.column}] $msg")

      case NonFatal(ex) => ex.printStackTrace()
    }

}
