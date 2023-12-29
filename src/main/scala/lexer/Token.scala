package cl.ravenhill.scomp
package lexer

/** Represents a lexical token in a programming language.
  *
  * This sealed trait is the base type for different kinds of tokens in a programming language parser. Tokens are
  * fundamental lexical elements like numbers, identifiers, keywords, and symbols used in parsing the language.
  */
sealed trait Token

/** Represents a numeric token.
  *
  * Numeric tokens consist of an integer value.
  *
  * @param value
  *   The integer value of the numeric token.
  */
case class NumberToken(value: Int) extends Token

/** Represents an identifier token.
  *
  * Identifier tokens typically represent names of variables or functions in the code.
  *
  * @param name
  *   The string representation of the identifier.
  */
case class IdentifierToken(name: String) extends Token

/** Represents an open parenthesis token '(' used for grouping expressions. */
case object OpenParenthesisToken extends Token

/** Represents a close parenthesis token ')' used for closing a grouping of expressions. */
case object CloseParenthesisToken extends Token

/** Represents an open brace token '{' used for starting a block of code or expressions. */
case object OpenBraceToken extends Token

/** Represents a close brace token '}' used for ending a block of code or expressions. */
case object CloseBraceToken extends Token

/** Represents a decrement token '--' used for decrementing a value. */
case object DecrementToken extends Token

/** Represents an increment token '++' used for incrementing a value. */
case object IncrementToken extends Token

/** Represents a doubling token, typically indicating a value should be doubled. */
case object DoubledToken extends Token

/** Represents the 'if' keyword token in conditional statements. */
case object IfToken extends Token

/** Represents the 'else' keyword token in conditional statements. */
case object ElseToken extends Token

/** Represents the 'let' keyword token, typically used in variable declarations. */
case object LetToken extends Token

/** Represents a dot token '.', often used in object-oriented or module syntax. */
case object DotToken extends Token

/** Represents an arrow token '->', commonly used in function expressions and mappings. */
case object ArrowToken extends Token
