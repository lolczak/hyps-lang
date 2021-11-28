package hyps.lang.compiler.parser

import hyps.lang.compiler.CompilerError

import java.util
import scala.annotation.unused

class Parser(input: Lexer) {

  private var lookaheadOffset = 0
  private val markers = new util.ArrayList[Int]()
  private val lookaheadBuffer = new util.ArrayList[Token]()

  private def fill(length: Int): Unit = {
    (1 to length).foreach(_ => lookaheadBuffer.add(input.nextToken()))
  }

  private def sync(idx: Int): Unit = {
    if (lookaheadOffset + idx - 1 > lookaheadBuffer.size() - 1) {
      val n = (lookaheadOffset + idx - 1) - (lookaheadBuffer.size() - 1)
      fill(n)
    }
  }

  private def consume(): Unit = {
    lookaheadOffset += 1
    if (lookaheadOffset == lookaheadBuffer.size() && !isSpeculating()) {
      lookaheadOffset = 0
      lookaheadBuffer.clear()
    }
    sync(1)
  }

  @unused
  private def mark(): Int = {
    markers.add(lookaheadOffset)
    lookaheadOffset
  }

  @unused
  private def release(): Unit = {
    val marker = markers.get(markers.size() - 1)
    markers.remove(markers.size()-1)
    seek(marker)
  }

  private def seek(idx: Int): Unit = {
    lookaheadOffset = idx
  }

  private def isSpeculating(): Boolean = !markers.isEmpty

  private def lookahead(idx: Int): Token = {
    sync(idx)
    lookaheadBuffer.get(lookaheadOffset + idx - 1)
  }

  @unused
  private def matchToken(tokenType: Int): Unit = {
    if (lookahead(1).`type` == tokenType) {
      consume()
    } else {
      throw CompilerError(lookahead(1).position, s"Cannot match token $tokenType")
    }
  }

}
