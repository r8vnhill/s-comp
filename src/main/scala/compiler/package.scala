package cl.ravenhill.scum

import ast.*

/** Package object for the compiler.
  *
  * This package object includes various methods and utilities used in the process of compiling an abstract syntax tree
  * (AST). It contains functions for annotating AST nodes with unique integer identifiers, which is a crucial step in
  * the compilation process.
  */
package object compiler {

  /** Implicit conversion from `Int` to `Option[Int]`. */
  private given intToOption: Conversion[Int, Option[Int]] = Some(_)

  /** Annotates an entire abstract syntax tree (AST) starting from a given expression.
    *
    * This method serves as the entry point for the annotation process of an AST. It takes an expression as input and
    * initiates the recursive annotation of the entire tree, starting from this expression. The method delegates the
    * annotation task to the `annotateNode` function, which handles the annotation of individual nodes in the AST. The
    * initial annotation index is set to 0, ensuring that the entire AST is annotated with a unique sequence of
    * integers. This process is essential for maintaining the traceability and unique identification of each node within
    * the AST, which can be beneficial for various subsequent operations like optimization, debugging, or
    * transformation.
    *
    * @param expression
    *   The root expression of the AST to be annotated.
    * @return
    *   The annotated version of the input expression, which is the root of the newly annotated AST.
    * @example
    *   {{{
    *     val originalExpr = Plus(NumericLiteral(3), NumericLiteral(4))
    *     val annotatedExpr = annotate(originalExpr)
    *     // annotatedExpr is now the root of the annotated AST.
    *   }}}
    */
  def annotate(expression: ast.Expression[Int]): ast.Expression[Int] = annotateNode(expression, 0)._1

  extension [A](expression: Expression[A]) {

    /** Determines if an expression is immediate within the context of the compiler.
      *
      * This method assesses whether a given expression is classified as an immediate expression. In the context of this
      * compiler, immediate expressions are those that are either numerical literals or identifier literals, as they can
      * be evaluated or referenced directly without further computation or dereferencing.
      *
      * @return
      *   `true` if the expression is a `NumericLiteral` or an `IdLiteral`, indicating that it is immediate; `false`
      *   otherwise.
      */
    private[compiler] def isImmediate: Boolean = expression match {
      case NumericLiteral(_, _) | IdLiteral(_, _) => true
      case _                                      => false
    }

    /** Determines if an expression is in A-Normal Form (ANF).
      *
      * This method checks whether a given expression conforms to the rules of A-Normal Form (ANF) within the compiler's
      * context. ANF is a canonical form of representing expressions where complex operations are broken down into
      * simpler ones, typically using 'Let' bindings. In ANF, every argument of an operation must be a simple
      * expression.
      *
      * The method evaluates different types of expressions:
      *   - For `UnaryOperation`, it checks if the operand is an immediate expression.
      *   - For `BinaryOperation`, it verifies that both the left and right operands are immediate expressions.
      *   - For `Let` expressions, it ensures that the bound expression is immediate, and the body of the 'Let' is also
      *     in ANF.
      *   - For `If` expressions, it checks that the predicate is immediate, and both the 'then' and 'else' branches are
      *     in ANF.
      *   - For other types of expressions, it defaults to checking if they are immediate.
      *
      * @return
      *   `true` if the expression is in ANF, `false` otherwise.
      */
    private[compiler] def isAnf: Boolean = expression match {
      case e: UnaryOperation[A]                     => e.expr.isImmediate
      case e: BinaryOperation[A]                    => e.left.isImmediate && e.right.isImmediate
      case Let(_, expr, body, _)                    => expr.isAnf && body.isAnf
      case If(predicate, thenBranch, elseBranch, _) => predicate.isAnf && thenBranch.isAnf && elseBranch.isAnf
      case _                                        => expression.isImmediate
    }

    def toAnf: Expression[A] = {
      def wrapWithLet(expr: Expression[A], context: List[(String, Expression[A])]): Expression[A] = {
        context.foldRight(expr) { case ((sym, expr), body) =>
          Let(sym, expr, body)
        }
      }
      val (anfExpr, context, _) = transformExpressionToAnf(expression, List.empty, 0)
      wrapWithLet(anfExpr, context)
    }
  }
}
