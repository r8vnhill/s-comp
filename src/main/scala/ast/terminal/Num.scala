package cl.ravenhill.scum
package ast.terminal

import ast.Expr

/** Represents a numerical constant in an expression with an additional annotation.
 *
 * The `Num` case class is a concrete implementation of the `Expr[A]` trait, specifically designed to represent integer
 * constants within expressions. It now includes a type parameter `A` to carry annotations, allowing for extended
 * information such as type metadata or evaluation context. As a case class, `Num` inherits Scala's case class
 * features like automatic creation of `apply` and `unapply` methods, and default implementations of `equals`, 
 * `hashCode`, and `toString`.
 *
 * @param n The integer value this `Num` instance represents.
 * @param annotation The annotation associated with this numerical constant, of type `A`.
 * @tparam A The type of annotation associated with this `Num` instance.
 */
case class Num[A](n: Int, annotation: A) extends Expr[A] {
  
  /** Returns the string representation of the numerical constant.
   *
   * This method overrides the `toString` method to return the string representation of the integer value `n`.
   *
   * @return A string representing the integer value.
   */
  override def toString: String = n.toString
}
