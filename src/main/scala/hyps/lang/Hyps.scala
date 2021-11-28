package hyps.lang

import java.io.{BufferedReader, InputStreamReader}
import hyps.lang.compiler.{Compiler, CompilerError}

import scala.util.control.NonFatal

object Hyps {

  def main(args: Array[String]): Unit = {
    runRepl()
  }

  def runRepl(): Unit = {
    val input = new InputStreamReader(System.in)
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

  private def run(source: String): Unit = {
    try {
      val tokens = Compiler.compile(source)
      for (token <- tokens) { print(token) }
      println()
    } catch {
      case CompilerError(position, msg) =>
        System.err.println(s"[${position.line}:${position.column}] $msg")

      case NonFatal(ex) => ex.printStackTrace()
    }
  }

}
