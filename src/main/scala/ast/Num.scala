package cl.ravenhill.scomp
package ast

/** Represents a numerical constant in an expression.
  *
  * The `Num` case class is a concrete implementation of the `Expr` trait, specifically for representing integer
  * constants within expressions. As a case class, `Num` benefits from Scala's case class features such as automatic
  * creation of `apply` and `unapply` methods, and default implementations of `equals`, `hashCode`, and `toString`.
  *
  * @param n The integer value this `Num` instance represents.
  */
case class Num(n: Int) extends Expr {

  /** Converts the numerical expression into its prefix notation.
    *
    * In the context of `Num`, the prefix notation is simply the string representation of the integer itself. This is
    * because numerical constants do not have operators associated with them and are leaves in an expression tree.
    *
    * @return A `String` representing the numerical constant. For instance, if `n` is 5, the `toPrefix` method will
    *         return "5".
    */
  override def toPrefix: String = n.toString
}
