package cl.ravenhill.scum
package ast.unary

import ast.Expression

/**
 * Represents an increment operation implementation in an abstract syntax tree (AST).
 *
 * `IncrementImpl` is a trait that encapsulates the behavior of an increment operation (`++`). It takes an expression
 * from the AST as its argument, indicating the expression to be incremented. The primary functionality of this trait
 * is to provide a custom string representation for the increment operation, which is useful for displaying or
 * debugging the AST.
 *
 * __Usage:__ This trait can be extended by classes or objects that represent an increment operation in an AST. The
 * expression to be incremented is passed at instantiation.
 *
 * @example
 *   {{{
 * object IncrementX extends IncrementImpl(Var("x"))
 * println(IncrementX) // Outputs: ++(x)
 *   }}}
 *
 * @param expr
 *   The expression to be incremented, represented as an `Expression[_]`.
 */
trait IncrementImpl(expr: Expression[_]) {

  /** Provides a string representation of the increment operation.
   *
   * This method overrides the default `toString` method to return a custom string that represents the increment
   * operation on an expression. The format of the returned string is `++(expr)`, where `expr` is the string
   * representation of the expression being incremented. This format is helpful in visualizing the increment operation
   * in the context of an abstract syntax tree (AST) or when debugging.
   *
   * @return
   *   A string representing the increment operation on the expression.
   */
  override def toString: String = s"++($expr)"
}
