package cl.ravenhill.scum
package ast.terminal

/** Trait for representing numeric literals in an abstract syntax tree (AST).
  *
  * `NumImpl` is a trait designed to encapsulate a numeric literal, commonly used in various programming languages and
  * represented here as an integer value. This trait is part of the `ast.terminal` package, indicating its role in
  * representing terminal (leaf) nodes in an AST, specifically for numeric values.
  *
  * The trait provides a simple and direct way to represent integer literals in Scala-based representations of
  * programming languages or interpreters. It takes a single parameter `n`, which is the integer value of the numeric
  * literal.
  *
  * @param n
  *   The integer value of the numeric literal.
  */
private[ast] trait NumericLiteral(n: Long) {

  /** Returns a string representation of the numeric literal.
    *
    * This method overrides the standard `toString` method to return the string representation of the integer value `n`.
    * It provides a straightforward way to convert the numeric literal to a string format, which can be useful for
    * debugging, logging, or displaying the value in a human-readable form.
    *
    * @return
    *   A string representation of the numeric literal.
    */
  override def toString: String = n.toString
}
