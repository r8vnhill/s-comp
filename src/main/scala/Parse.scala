package cl.ravenhill.scomp

import ast.Expr

import scala.util.{Failure, Success, Try}

extension (s: String) {

  /** Extension method for the `String` class to check if a string represents an integer value.
    *
    * This method extends the `String` class with additional functionality to determine whether the string conforms to
    * the format of an integer. The method checks if the string matches a regular expression that defines the general
    * structure of integer numbers, including handling for negative numbers and leading zeros.
    *
    * The regular expression used (`-?\b0*\d+\b`) checks for:
    *   - An optional negative sign (`-?`).
    *   - Zero or more leading zeros (`0*`).
    *   - One or more digits (`\d+`).
    *   - Word boundaries to ensure the entire string is checked (`\b` at both ends).
    *
    * @return
    *   `true` if the string matches the pattern of an integer, `false` otherwise.
    * @example
    *   {{{
    *   "123".isInt  // returns true
    *   "-123".isInt // returns true
    *   "0123".isInt // returns true
    *   "123abc".isInt // returns false
    *   "abc123".isInt // returns false
    *   }}}
    */
  def isInt: Boolean = s.matches("""-?\b0*\d+\b""")
}

def parse(s: String): Try[Expr] = {

  def parseTokens(tokens: Seq[String]): Try[(Expr, Seq[String])] = tokens match {
    case Seq() => Failure(ParserException("Empty input"))
    case Seq(n, rest*) if n.isInt => Success((ast.Num(n.toInt), rest))
    case Seq("+", rest*) =>
      for {
        (leftExpr, afterLeft) <- parseTokens(rest)
        (rightExpr, afterRight) <- parseTokens(afterLeft)
      } yield (ast.Plus(leftExpr, rightExpr), afterRight)
    case Seq("*", rest*) =>
      for {
        (leftExpr, afterLeft) <- parseTokens(rest)
        (rightExpr, afterRight) <- parseTokens(afterLeft)
      } yield (ast.Times(leftExpr, rightExpr), afterRight)
    case _ => Failure(ParserException(s"Invalid input: ${tokens.mkString(" ")}"))
  }

  val input = s.trim.split("\\s+")
  if (input.isEmpty || (input.size == 1 && input.head.isEmpty)) Success(ast.EOF)
  else parseTokens(input).flatMap {
    case (expr, Seq()) => Success(expr)
    case (_, remaining) => Failure(ParserException(s"Unparsed input remaining: ${remaining.mkString(" ")}"))
  }
}


/** A `Try` wrapper for the `EOF` object from the `ast` package.
  *
  * `tryEof` is a private value that encapsulates the `EOF` object within a `Try` context. This is primarily used to
  * handle scenarios where an End-Of-File (EOF) marker needs to be returned or processed in a safe, exception-handling
  * manner. Wrapping `EOF` in `Try` allows for consistent error handling in functions or methods that might encounter or
  * return `EOF` as part of normal operation, especially in parsing or interpreting contexts where EOF signifies the end
  * of input.
  *
  * The `EOF` object is obtained from the `ast` package, and it represents an End-Of-File marker in expression parsing
  * or evaluation scenarios. By wrapping it in `Try`, `tryEof` ensures that any operations involving `EOF` can leverage
  * Scala's built-in error handling mechanisms, enhancing robustness and reliability.
  *
  * As a private value, `tryEof` is intended for internal use within its containing class or object, encapsulating the
  * specifics of handling an EOF condition in a way that's abstracted away from external components.
  */
private val tryEof = Try(ast.EOF)
