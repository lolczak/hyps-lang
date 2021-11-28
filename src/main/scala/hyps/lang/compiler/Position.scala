package hyps.lang.compiler

case class Position(offset: Int, line: Int, column: Int) {

  def advanceColumn(): Position = this.copy(offset = this.offset + 1, column = this.column + 1)

  def advanceLine(): Position = Position(offset + 1, line + 1, 1)

}

object Position {

  val Begin = Position(0, 1, 1)

}
