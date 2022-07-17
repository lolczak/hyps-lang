package hyps.lang

import hyps.lang.compiler.interpreter.RuntimeError
import hyps.lang.compiler.{Compiler, CompilerError}

import java.io.{BufferedReader, InputStreamReader}
import scala.util.control.NonFatal

object Hyps {

  def main(args: Array[String]): Unit =
    runRepl()

  def runRepl(): Unit = {
    val input  = new InputStreamReader(System.in)
    val reader = new BufferedReader(input)

    var source = ""
    while (true) {
      print("> ")
      val line = reader.readLine()
      if (line == null) {
        System.exit(0)
      }
      if (line.endsWith("\\")) {
        val stripped = line.substring(0, line.length - 2)
        source = s"$source\n$stripped"
      } else {
        source = s"$source\n$line"
        run(line)
        source = ""
      }
    }
  }

  private def run(source: String): Unit =
    try {
      val value = Compiler.compile(source)
      println(s"= $value")
    } catch {
      case CompilerError(position, msg) =>
        println(s"[${position.line}:${position.column}] $msg")

      case RuntimeError(msg) =>
        println(s"Runtime error: $msg")

      case NonFatal(ex) => ex.printStackTrace()
    }

}
