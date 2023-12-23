package cl.ravenhill.scomp
package ast

/** Represents a variable in an expression.
  *
  * The `Var` case class is a concrete implementation of the `Expr` trait, specifically designed for representing
  * variables in expressions. It encapsulates a `sym` which is a string symbol of the variable. As a case class,
  * `Var` benefits from Scala's case class features, such as automatic creation of `apply` and `unapply` methods,
  * and default implementations of `equals`, `hashCode`, and `toString`.
  *
  * @param sym The string symbol representing the variable. This symbol is used to identify the variable within
  *            an expression.
  */
case class Var(sym: String) extends Expr {

  /** Converts the variable expression into its prefix notation.
    *
    * In the case of `Var`, the prefix notation is simply the variable's symbol itself. As variables are basic
    * elements in an expression and do not have operators associated with them, the `toPrefix` method returns the
    * symbol representing the variable.
    *
    * @return A `String` representing the variable in prefix notation. For example, if `sym` is "x", the `toPrefix`
    *         method will return "x".
    */
  override def toPrefix: String = sym
}
