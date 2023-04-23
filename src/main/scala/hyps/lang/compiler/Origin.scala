package hyps.lang.compiler

case class Origin(path: String, offset: Int, line: Int, column: Int) {

  def advanceColumn(): Origin = this.copy(offset = this.offset + 1, column = this.column + 1)

  def advanceLine(): Origin = this.copy(offset = offset + 1, line = line + 1, column = 1)

}

object Origin {

  def begin(path: String) = Origin(path, 0, 1, 1)

}
