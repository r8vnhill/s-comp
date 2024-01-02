package cl.ravenhill.scum
package ast.terminal

import ast.Expr

/** Represents a variable in an expression with an additional annotation.
  *
  * The `Var` case class extends the `Expr[A]` trait, tailored to represent variables within expressions. It includes a
  * type parameter `A` for annotations, which can be used to attach additional information such as type metadata,
  * evaluation context, or any other relevant data. This class maintains the name of the variable (`sym`) and the
  * associated annotation. Like other case classes in Scala, `Var` automatically provides implementations for methods
  * like `apply`, `unapply`, `equals`, `hashCode`, and `toString`.
  *
  * @param sym
  *   The name of the variable represented by this instance.
  * @param metadata
  *   The annotation associated with this variable, of type `A`.
  * @tparam A
  *   The type of annotation associated with this variable instance.
  */
case class Var[A](sym: String)(using override val metadata: Metadata[A]) extends Expr[A] {

  /** Returns the string representation of the variable name.
    *
    * This method overrides the `toString` method to return the name of the variable (`sym`), facilitating easy
    * identification and readability of the variable in textual formats.
    *
    * @return
    *   A string representing the name of the variable.
    */
  override def toString: String = sym
}
