package cl.ravenhill.scomp
package ast

/** Represents an 'if' conditional expression with an annotation in an abstract syntax tree (AST).
  *
  * This case class models the 'if-else' construct, a fundamental control flow structure in many programming languages,
  * and now includes an annotation of type `A`. The `If` class takes four parameters: a condition (`cond`), a 'then'
  * branch (`thenBranch`), an 'else' branch (`elseBranch`), and an annotation (`annotation`). If `cond` evaluates to a
  * non-zero (or 'true') value, `thenBranch` is evaluated; otherwise, `elseBranch` is evaluated. This construct allows
  * for the conditional execution of expressions based on the evaluation of the condition, with the ability to carry
  * additional information through the annotation.
  *
  * The `toString` method provides a string representation of the 'if-else' construct, formatted in a way familiar from
  * high-level programming languages, aiding in readability and debugging.
  *
  * @param cond
  *   The conditional expression to evaluate. If this evaluates to non-zero, `thenBranch` is executed.
  * @param thenBranch
  *   The expression to execute if `cond` evaluates to non-zero.
  * @param elseBranch
  *   The expression to execute if `cond` evaluates to zero.
  * @param annotation
  *   The annotation associated with this 'if-else' expression, of type `A`.
  * @tparam A
  *   The type of annotation associated with this instance of the 'if-else' expression.
  */
case class If[A](cond: Expr[A], thenBranch: Expr[A], elseBranch: Expr[A], annotation: A) extends Expr[A] {

  /** Returns a string representation of the 'if-else' construct.
    *
    * This method overrides the `toString` method to represent the 'if' conditional expression in a format familiar from
    * high-level programming languages. The format is "if (condition) { thenBranch } else { elseBranch }".
    *
    * @return
    *   A string depicting the 'if-else' expression, aiding in understanding the expression's structure.
    */
  override def toString: String = s"if ($cond) { $thenBranch } else { $elseBranch }"
}
