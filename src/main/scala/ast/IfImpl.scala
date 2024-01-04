package cl.ravenhill.scum
package ast

/** Represents an implementation of an 'if' conditional expression in an abstract syntax tree (AST).
  *
  * The `IfImpl` trait encapsulates the behavior of an 'if' conditional expression within an AST. It takes three
  * expressions as arguments: a predicate expression, and two expressions representing the 'then' and 'else' branches of
  * the conditional. The primary functionality of this trait is to provide a custom string representation for the 'if'
  * conditional, which is useful for displaying or debugging the AST.
  *
  * __Usage:__ Implement this trait in classes or objects that need to represent an 'if' conditional expression in the
  * AST. The predicate, 'then' branch, and 'else' branch expressions should be provided at the time of instantiation.
  *
  * @example
  *   {{{
  * object IfExample extends IfImpl(Var("condition"), Var("thenBranch"), Var("elseBranch"))
  * println(IfExample) // Outputs: if (condition) { thenBranch } else { elseBranch }
  *   }}}
  *
  * @param predicate
  *   The expression representing the condition to be evaluated in the 'if' statement.
  * @param thenBranch
  *   The expression to be executed if the predicate evaluates to true.
  * @param elseBranch
  *   The expression to be executed if the predicate evaluates to false.
  */
trait IfImpl(predicate: Expression[_], thenBranch: Expression[_], elseBranch: Expression[_]) {

  /** Provides a string representation of the 'if' conditional expression.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'if'
    * conditional. The output format is `if ($predicate) { $thenBranch } else { $elseBranch }`, where `$predicate`,
    * `$thenBranch`, and `$elseBranch` are the string representations of the corresponding expressions in the
    * conditional statement. This representation is particularly useful for visualizing the structure within the AST or
    * during debugging processes.
    *
    * @return
    *   A string representation of the 'if' conditional expression.
    */
  override def toString: String = s"if ($predicate) { $thenBranch } else { $elseBranch }"
}
