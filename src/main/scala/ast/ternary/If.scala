package cl.ravenhill.scum
package ast.ternary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of an 'if' conditional expression in an abstract syntax tree (AST).
  *
  * The `If` trait encapsulates the behavior of an 'if' conditional expression within an AST. It takes three expressions
  * as arguments: a predicate expression, and two expressions representing the 'then' and 'else' branches of the
  * conditional. Additionally, it accepts `metadata` as an optional parameter for additional information related to the
  * conditional expression. The primary functionality of this trait is to provide a custom string representation for the
  * 'if' conditional, which is useful for displaying or debugging the AST.
  *
  * __Usage:__ Implement this trait in classes or objects that need to represent an 'if' conditional expression in the
  * AST. The predicate, 'then' branch, and 'else' branch expressions, along with optional metadata, should be provided
  * at the time of instantiation.
  *
  * @param predicate
  *   The expression representing the condition to be evaluated in the 'if' statement.
  * @param thenBranch
  *   The expression to be executed if the predicate evaluates to true.
  * @param elseBranch
  *   The expression to be executed if the predicate evaluates to false.
  * @param metadata
  *   Optional additional information related to the conditional expression.
  */
private[ast] trait If(
    val predicate: Expression[_],
    val thenBranch: Expression[_],
    val elseBranch: Expression[_],
    val metadata: Option[_]
) {

  /** Provides a string representation of the 'if' conditional expression.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'if'
    * conditional. Depending on the `toStringMode`, the output format is 'if ($predicate) { $thenBranch } else {
    * $elseBranch }' in NORMAL mode, or 'If(predicate = $predicate, thenBranch = $thenBranch, elseBranch = $elseBranch,
    * metadata = $metadata)' in DEBUG mode. This representation is particularly useful for visualizing the structure
    * within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the 'if' conditional expression.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => s"if ($predicate) { $thenBranch } else { $elseBranch }"
    case ToStringMode.DEBUG =>
      s"If(predicate = $predicate, thenBranch = $thenBranch, elseBranch = $elseBranch, metadata = $metadata)"
  }
}
