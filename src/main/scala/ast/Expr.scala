package cl.ravenhill.scomp
package ast

/** Represents an expression in a simple expression language or compiler.
  *
  * The `Expr` trait serves as a base for various types of expressions in a domain-specific language or a subset of
  * Scala. It's an abstract interface for all expression types that can be evaluated or manipulated within the system.
  *
  * Implementers of this trait must define the [[toPrefix]] method, which is intended to convert the expression into its
  * prefix notation. Prefix notation (also known as Polish notation) is a way of writing down arithmetic, logical, or
  * other mathematical expressions in which the operators precede their operands. This form can be useful for parsing or
  * evaluating expressions in a systematic way.
  *
  * Example Implementations:
  *
  *   - Literal constants (e.g., numbers, booleans)
  *   - Mathematical operations (e.g., addition, multiplication)
  *   - Logical operations (e.g., AND, OR)
  *   - Variables or identifiers
  *
  * Example Usage:
  *
  * Suppose `Plus` is an implementation of `Expr` representing an addition operation. The `toPrefix` method for `Plus`
  * might convert an expression like `Plus(Literal(1), Literal(2))` into the string "+ 1 2".
  */
trait Expr {

  /** Converts the expression into its prefix notation.
    *
    * This method should be implemented by all concrete expression types to return a string representation of the
    * expression in prefix form. The specific format and structure of the output may vary depending on the type of
    * expression.
    *
    * @return A `String` representing the expression in prefix notation.
    */
  def toPrefix: String
}
