package cl.ravenhill.scomp
package ast.unary

import ast.Expr

/** Represents an increment expression in a simple expression language.
  *
  * `Increment` is a case class that extends the `Expr` trait, representing an increment operation on an expression.
  * This class encapsulates the concept of incrementing a value, which is a common operation in various programming and
  * expression languages. The increment operation is applied to another expression, encapsulated within this class.
  *
  * @param expr
  *   The expression to be incremented. This is an instance of `Expr`, allowing for nested expressions and complex
  *   expression structures.
  */
case class Increment(expr: Expr) extends Expr {

  /** Converts the increment expression into its prefix notation.
    *
    * This method overrides the `toPrefix` method from the `Expr` trait and provides the specific implementation for the
    * increment expression. In prefix notation, the increment operator (inc) precedes the operand. The method converts
    * the entire increment expression into a string representation in prefix form, suitable for parsing, interpretation,
    * or display.
    *
    * @return
    *   A `String` representing the increment expression in prefix notation. For example, incrementing a numeric literal
    *   `1` would be represented as "(inc 1)" in prefix notation.
    */
  override def toPrefix: String = s"(inc ${expr.toPrefix})"
}
