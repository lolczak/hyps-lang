package hyps.lang.compiler.parser

import hyps.lang.compiler.CompilerError

import java.util
import scala.annotation.unused

/** An abstract backtracking parser. The inheriting classes overcome lookahead issues by allowing arbitrary lookahead.
  * This parses uses backtracking strategy for a parsing decision. It speculatively attempt the alternatives in order
  * until it finds one that matches. Upon success, the parser rewinds the input and uses parsing rules normally.
  * Upon failing to match an alternative, the parser rewinds the input and tries the next one. */
abstract class BacktrackingParser(input: Lexer) {

  /** Index of current lookahead token from the last consumed token. */
  private var lookaheadOffset = 0

  /** Stack of index markers into lookahead buffer. The algorithm uses it to backtrack when a non-terminal rule doesn't
    * match. */
  private val markers = new util.ArrayList[Int]()

  /** Dynamically-sized lookahead buffer. */
  private val lookaheadBuffer = new util.ArrayList[Token]()

  /** Appends `count` number of new tokens to the `lookaheadBuffer`. */
  private def fill(count: Int): Unit = {
    (1 to count).foreach(_ => lookaheadBuffer.add(input.nextToken()))
  }

  /** Looks `count` tokens ahead. It makes sure that the `lookaheadBuffer` has valid tokens from index `lookaheadOffset`
    * to `lookaheadOffset + count - 1`. */
  private def lookAhead(count: Int): Unit = {
    if (lookaheadOffset + count - 1 > lookaheadBuffer.size() - 1) {
      val n = (lookaheadOffset + count - 1) - (lookaheadBuffer.size() - 1)
      fill(n)
    }
  }

  /** Consumes the first lookahead token and advances one token forward. */
  protected def consume(): Unit = {
    lookaheadOffset += 1
    if (lookaheadOffset == lookaheadBuffer.size() && !isSpeculating()) {
      lookaheadOffset = 0
      lookaheadBuffer.clear()
    }
    lookAhead(1)
  }

  /** Marks the current position. The marker is used to backtrack to this position. */
  @unused
  private def mark(): Int = {
    markers.add(lookaheadOffset)
    lookaheadOffset
  }

  /** Releases the last marked position. */
  @unused
  private def release(): Unit = {
    val marker = markers.get(markers.size() - 1)
    markers.remove(markers.size() - 1)
    seek(marker)
  }

  /** Changes the lookahead offset. */
  protected def seek(idx: Int): Unit = {
    lookaheadOffset = idx
  }

  private def isSpeculating(): Boolean = !markers.isEmpty

  /** Peeks `idx` tokens ahead. */
  protected def peek(idx: Int): Token = {
    lookAhead(idx)
    lookaheadBuffer.get(lookaheadOffset + idx - 1)
  }

  /** Matches the next token. Upon success, return void. Otherwise, throws a compiler exception. */
  @unused
  protected def matchToken(tokenType: Int): Unit = {
    if (peek(1).`type` == tokenType) {
      consume()
    } else {
      throw CompilerError(peek(1).position, s"Cannot match token $tokenType")
    }
  }

  /**
    * It speculatively attempt the rule. Upon success, it returns true. Otherwise false.
    *
    * @param rule the non-terminal rule that is attempted to match
    * @return the matching result
    */
  protected def speculate(rule: => Nothing): Boolean = {
    mark()
    try {
      rule
    }
    catch {
      case _: CompilerError => return false
    } finally {
      release()
    }
    true
  }

}
