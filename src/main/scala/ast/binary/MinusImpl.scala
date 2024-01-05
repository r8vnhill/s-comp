package cl.ravenhill.scum
package ast.binary

/** Represents an implementation of a subtraction ('minus') operation in an abstract syntax tree (AST).
  *
  * The `MinusImpl` trait encapsulates the behavior of a subtraction operation. It takes two expressions from the AST,
  * [[left]] and [[right]], as its arguments, representing the operands of the subtraction. The primary functionality of
  * this trait is to provide a custom string representation for the subtraction operation, which is useful for
  * displaying or debugging the AST.
  *
  * __Usage:__ This trait can be extended by classes or objects that represent a subtraction operation in an AST. The
  * expressions to be subtracted (left and right operands) are passed at instantiation.
  *
  * @example
  *   {{{
  * object SubtractExample extends MinusImpl(Var("x"), Num(1))
  * println(SubtractExample) // Outputs: (x - 1)
  *   }}}
  * @param left
  *   The left-hand side expression of the subtraction.
  * @param right
  *   The right-hand side expression of the subtraction.
  */
trait MinusImpl(left: ast.Expression[_], right: ast.Expression[_]) {

  /** Provides a string representation of the subtraction operation.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the subtraction
    * operation. The output format is `(${left.toString} - ${right.toString})`, where `left` and `right` are the string
    * representations of the corresponding expressions in the subtraction operation. This representation is particularly
    * useful for visualizing the operation within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the subtraction operation.
    */
  override def toString: String = s"(${left.toString} - ${right.toString})"
}
