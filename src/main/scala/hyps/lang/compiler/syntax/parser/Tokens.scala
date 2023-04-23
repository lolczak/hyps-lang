package hyps.lang.compiler.syntax.parser

object Tokens {

  val EOF      = 0
  val NEW_LINE = 1

  //Single character tokens
  val LEFT_PAREN    = 100
  val RIGHT_PAREN   = 101
  val LEFT_BRACE    = 102
  val RIGHT_BRACE   = 103
  val LEFT_BRACKET  = 104
  val RIGHT_BRACKET = 105
  val LESS          = 106
  val GREATER       = 107
  val COMMA         = 108
  val DOT           = 109
  val MINUS         = 110
  val PLUS          = 111
  val SEMICOLON     = 112
  val SLASH         = 113
  val STAR          = 114
  val BANG          = 115
  val COLON         = 116
  val EQUAL         = 117

  //Two character tokens
  val BANG_EQUAL    = 200
  val EQUAL_EQUAL   = 201
  val GREATER_EQUAL = 202
  val LESS_EQUAL    = 203

  //Literals
  val IDENTIFIER = 300
  val STRING     = 301
  val NUMBER     = 302

  //KEYWORDS
  val FN      = 400
  val AND     = 401
  val OR      = 402
  val CLASS   = 403
  val ELSE    = 404
  val FALSE   = 405
  val FOR     = 406
  val IF      = 407
  val NULL    = 408
  val RETURN  = 409
  val SUPER   = 410
  val THIS    = 411
  val TRUE    = 412
  val VAR     = 413
  val LET     = 414
  val WHILE   = 415
  val PRINTLN = 416

}
