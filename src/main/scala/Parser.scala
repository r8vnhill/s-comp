package cl.ravenhill.scomp

import ast.Expr

/** Parses a string representation of an expression into an `Expr` object.
  *
  * This function takes a string that represents an arithmetic expression and parses it into an `Expr` object
  * which can then be used for further processing like evaluation or transformation. The parsing supports basic
  * arithmetic operations (addition and multiplication) and handles both numerical values and variables.
  *
  * @param stringExpr The string representation of the expression to be parsed.
  * @return An `Expr` object representing the parsed expression.
  * @throws IllegalArgumentException If the format of the expression is invalid.
  */
def parse(stringExpr: String): Expr = {

  /** Attempts to parse a binary operation from the expression string.
    *
    * This helper function looks for a specific operator character at the beginning of the expression string and, if 
    * found, attempts to split the string into two parts representing the left and right operands of the binary 
    * operation. It then recursively parses these operands and applies the provided constructor function to create an 
    * `Expr` object representing the operation.
    *
    * @param opChar    The character representing the binary operator.
    * @param construct A function that takes two `Expr` objects (operands) and constructs an `Expr` object
    *                  representing the binary operation.
    * @return An `Option[Expr]` containing the constructed `Expr` object if parsing is successful, or `None`
    *         if the string does not start with the specified operator.
    */
  def binaryOpParse(opChar: Char, construct: (Expr, Expr) => Expr): Option[Expr] = {
    if (stringExpr.startsWith(s"$opChar ")) {
      val expr = stringExpr.substring(2).trim
      val parts = expr.split(" ", 2)
      if (parts.length == 2) {
        val left = parse(parts(0))
        val right = parse(parts(1))
        Some(construct(left, right))
      } else None
    } else None
  }

  stringExpr match {
    case s if s.startsWith("+ ") =>
      binaryOpParse('+', ast.Plus.apply)
        .getOrElse(throw new IllegalArgumentException("Invalid format for + expression"))
    case s if s.startsWith("* ") =>
      binaryOpParse('*', ast.Times.apply)
        .getOrElse(throw new IllegalArgumentException("Invalid format for * expression"))
    case _ => stringExpr.toIntOption match {
      case Some(value) => ast.Num(value)
      case None => ast.Var(stringExpr)
    }
  }
}
