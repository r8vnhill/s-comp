package cl.ravenhill.scomp
package ast

/** Represents a multiplication operation in an expression.
  *
  * The `Times` case class is a concrete implementation of the `Expr` trait, used for representing binary multiplication
  * operations within expressions. Similar to the `Plus` class, `Times` encapsulates two expressions, `left` and
  * `right`, which serve as operands for the multiplication operation. As a case class, `Times` inherits Scala's
  * features like automatic creation of `apply` and `unapply` methods, and default implementations of `equals`,
  * `hashCode`, and `toString`.
  *
  * @param left  The left operand of the multiplication operation, an instance of `Expr`.
  * @param right The right operand of the multiplication operation, an instance of `Expr`.
  */
case class Times(left: Expr, right: Expr) extends Expr {

  /** Converts the multiplication expression into its prefix notation.
    *
    * The `toPrefix` method for `Times` constructs a string that represents the multiplication operation in prefix
    * notation. In this notation, the operator precedes the operands. Therefore, this method returns a string in the
    * form "(* operand1 operand2)", where `operand1` and `operand2` are the prefix representations of the `left` and
    * `right` operands, respectively.
    *
    * @return A `String` representing the multiplication operation in prefix notation. For example, if `left` is an
    *         instance of `Num(3)` and `right` is an instance of `Num(4)`, the `toPrefix` method will return "(* 3 4)".
    */
  override def toPrefix: String = s"(* ${left.toPrefix} ${right.toPrefix})"
}
