package cl.ravenhill.scomp

import ast.Expr

import scala.util.{Failure, Success, Try}

extension (s: String)

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

  /** Extension method for the `String` class to check if a string represents a boolean value.
    *
    * This method extends the `String` class with the functionality to determine whether the string matches the textual
    * representation of boolean values, specifically "true" or "false". It utilizes a regular expression to check if the
    * string exactly matches either of these two values.
    *
    * The regular expression used (`true|false`) checks for:
    *   - A direct match with the string "true".
    *   - A direct match with the string "false".
    *
    * This method is particularly useful for parsing or validating input where boolean values are expected in a textual
    * form.
    *
    * @return
    *   `true` if the string is either "true" or "false", `false` otherwise.
    * @example
    *   {{{
    *   "true".isBool  // returns true
    *   "false".isBool // returns true
    *   "True".isBool  // returns false (case-sensitive)
    *   "1".isBool     // returns false
    *   "yes".isBool   // returns false
    *   }}}
    */
  def isBool: Boolean = s.matches("""true|false""")

  def isBlank: Boolean = s.trim.isEmpty

/** Parses a string into an expression object, handling the input in prefix notation.
  *
  * This function attempts to parse a given string into an [[Expr]] object, representing a structured expression. It is
  * designed to handle input formatted in prefix notation, where the operator precedes its operands. The parsing process
  * involves splitting the input string into tokens based on whitespace and processing these tokens recursively to
  * construct the expression tree.
  *
  * The function handles the following cases:
  *   - Empty input: Returns a failure indicating that the input is empty.
  *   - Integer literals: Parses numeric strings into `Num` objects.
  *   - Boolean literals: Parses boolean strings into `True` and `False` objects.
  *   - Addition and Multiplication: Recognizes '+' and '*' as operators and recursively constructs `Plus` and `Times`
  *     expressions with the corresponding operands.
  *   - Variables: Parses variable names into `Var` objects.
  *   - Remaining cases: Returns a failure for any input that doesn't match the expected patterns.
  *
  * If the input string is empty or contains only whitespace, the function returns `EOF` (End-Of-File) marker as a
  * successful result. Otherwise, it attempts to parse the input and returns either the parsed expression or an error if
  * the input is invalid or incomplete.
  *
  * @param s
  *   The input string to be parsed.
  * @return
  *   A `Try[Expr]` representing either the successfully parsed expression (`Success[Expr]`) or an error
  *   (`Failure[ParserException]`) if the parsing fails.
  */
def parse(s: String): Try[Expr] = {
  val variablePattern = """([a-zA-Z][a-zA-Z0-9]*)""".r
  val additionPattern = """\+ (.*) (.*)""".r
  val productPattern  = """\* (.*) (.*)""".r
  s match
    case _ if s.isBlank        => Success(ast.terminal.EOF)
    case _ if s.isInt          => Success(ast.terminal.Num(s.toInt))
    case _ if s.isBool         => Success(if s.toBoolean then ast.terminal.True else ast.terminal.False)
    case variablePattern(name) => Success(ast.terminal.Var(name))
    case additionPattern(l, r) => parse(l).flatMap(l => parse(r).map(r => ast.binary.Plus(l, r)))
    case productPattern(l, r)  => parse(l).flatMap(l => parse(r).map(r => ast.binary.Times(l, r)))
    case _                     => Failure(ParserException(s"Invalid input: $s"))
}
