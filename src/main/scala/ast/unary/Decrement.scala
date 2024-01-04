package cl.ravenhill.scum
package ast.unary

/** Represents a decrement operation in an expression with an additional annotation.
  *
  * The `Decrement` case class is a part of the abstract syntax tree (AST) and extends the `Expr[A]` trait. It models
  * the decrement operation (`--`) applied to an expression. This class is used to represent the unary decrement
  * operation in an AST, holding both the target expression to be decremented and an annotation of type `A`. The
  * annotation can be used to carry additional information such as type metadata or evaluation context. As a case class,
  * `Decrement` inherits Scala's features like `apply`, `unapply`, `equals`, `hashCode`, and `toString`.
  *
  * @param expr
  *   The expression to which the decrement operation is applied.
  * @param metadata
  *   The annotation associated with this decrement operation, of type `A`.
  * @tparam A
  *   The type of annotation associated with this instance of the decrement operation.
  */
case class Decrement[A](expr: ast.Expression[A])(using override val metadata: Metadata[A]) extends ast.Expression[A] {

  /** Returns the string representation of the decrement operation.
    *
    * This method overrides the `toString` method to return a string format of the decrement operation applied to the
    * expression. The format is "--(expression)", providing a clear and concise representation of the operation.
    *
    * @return
    *   A string depicting the decrement operation in a readable format.
    */
  override def toString: String = s"--($expr)"
}
