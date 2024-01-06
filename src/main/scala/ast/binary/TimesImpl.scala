package cl.ravenhill.scum
package ast.binary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of a multiplication ('times') operation in an abstract syntax tree (AST).
  *
  * The `TimesImpl` trait encapsulates the behavior of a multiplication operation. It takes two expressions, [[left]]
  * and [[right]], from the AST as its arguments, representing the operands of the multiplication. The primary
  * functionality of this trait is to provide a custom string representation for the multiplication operation, which is
  * useful for displaying or debugging the AST.
  *
  * __Usage:__ This trait can be extended by classes or objects that represent a multiplication operation in an AST. The
  * expressions to be multiplied (left and right operands) are passed at instantiation.
  *
  * @example
  *   {{{
  * object MultiplyExample extends TimesImpl(Var("x"), Num(2))
  * println(MultiplyExample) // Outputs: (x * 2)
  *   }}}
 * @param left
  *   The left-hand side expression of the multiplication.
  * @param right
  *   The right-hand side expression of the multiplication.
  */
trait TimesImpl(left: Expression[_], right: Expression[_]) {

  /** Provides a string representation of the multiplication operation.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the multiplication
    * operation. The output format is `($left * $right)`, where [[left]] and [[right]] are the string representations of
    * the corresponding expressions in the multiplication operation. This representation is particularly useful for
    * visualizing the operation within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the multiplication operation.
    */
  override def toString: String = s"($left * $right)"
}
