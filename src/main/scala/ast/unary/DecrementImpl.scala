package cl.ravenhill.scum
package ast.unary

/** Represents a decrement operation implementation in an abstract syntax tree (AST).
  *
  * `DecrementImpl` is a trait that encapsulates the behavior of a decrement operation (`--`). It takes an expression
  * from the AST as its argument, indicating the expression to be decremented. The primary functionality of this trait
  * is to provide a custom string representation for the decrement operation, which is useful for displaying or
  * debugging the AST.
  *
  * __Usage:__ This trait can be extended by classes or objects that represent a decrement operation in an AST. The
  * expression to be decremented is passed at instantiation.
  *
  * @example
  *   {{{
  * object DecrementX extends DecrementImpl(Var("x"))
  * println(DecrementX) // Outputs: --(x)
  *   }}}
  *
  * @param expr
  *   The expression to be decremented, represented as an `ast.Expression[_]`.
  */
trait DecrementImpl(expr: ast.Expression[_]) {

  /** Provides a string representation of the decrement operation.
    *
    * This method overrides the default `toString` method to return a custom string that represents the decrement
    * operation on an expression. The format of the returned string is `--(expr)`, where `expr` is the string
    * representation of the expression being decremented. This format is helpful in visualizing the decrement operation
    * in the context of an abstract syntax tree (AST) or when debugging.
    *
    * @return
    *   A string representing the decrement operation on the expression.
    */
  override def toString: String = s"--($expr)"
}
