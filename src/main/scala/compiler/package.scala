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
    *
    * @example
    *   {{{
    * val originalExpr = Plus(NumericLiteral(3), NumericLiteral(4))
    * val annotatedExpr = annotate(originalExpr)
    * // annotatedExpr is now the root of the annotated AST.
    *   }}}
    */
  def annotate(expression: ast.Expression[Int]): ast.Expression[Int] = annotateNode(expression, 0)._1

  /** Annotates an expression node in an abstract syntax tree (AST).
    *
    * This method is a dispatcher that handles the annotation of different types of expression nodes in an AST.
    * Depending on the type of the node, it delegates the annotation task to specific functions designed for each
    * expression type. This method is crucial for maintaining the unique identification and processing of each node in
    * the AST, aiding in tasks such as optimization, code generation, and debugging. It supports a variety of
    * expressions including literals, unary operations, binary operations, conditional 'if' expressions, and 'let'
    * bindings.
    *
    * @param node
    *   The expression node to be annotated.
    * @param cur
    *   The current annotation index, used for tagging expressions uniquely.
    * @return
    *   A tuple consisting of the annotated expression node and the next annotation index. The annotation process varies
    *   based on the type of the expression node.
    */
  private def annotateNode(node: ast.Expression[Int], cur: Int): (ast.Expression[Int], Int) = {
    node match {
      case lit: Literal[_]        => annotateLiteral(lit, cur)
      case op: UnaryOperation[_]  => annotateUnaryOperation(op, cur)
      case op: BinaryOperation[_] => annotateBinaryOperation(op, cur)
      case ifExpr: If[_]          => annotateIf(ifExpr, cur)
      case letExpr: Let[_]        => annotateLet(letExpr, cur)
    }
  }

  /** Annotates a literal expression in an abstract syntax tree (AST).
    *
    * This method is responsible for annotating literal expressions, such as `NumericLiteral` and `IdLiteral`. Literals
    * are the basic, constant values in a language and do not contain any sub-expressions. The method assigns a unique
    * integer tag to each literal expression. This annotation process is crucial for maintaining the unique
    * identification of each expression in the AST, particularly for literals which often serve as the base cases in
    * many programming constructs.
    *
    * @param lit
    *   The literal expression to be annotated.
    * @param cur
    *   The current annotation index, used for tagging the expression uniquely.
    * @return
    *   A tuple consisting of the annotated literal expression and the next annotation index. The annotation index is
    *   incremented after annotating the literal.
    */
  private def annotateLiteral(lit: ast.Literal[Int], cur: Int): (ast.Expression[Int], Int) = {
    lit match {
      case NumericLiteral(n, _) => (NumericLiteral(n, cur), cur + 1)
      case IdLiteral(sym, _)    => (IdLiteral(sym, cur), cur + 1)
    }
  }

  /** Annotates a unary operation expression in an abstract syntax tree (AST).
    *
    * This method is responsible for annotating unary operation expressions, such as `Decrement`, `Increment`, and
    * `Doubled`. Each unary operation consists of a single operand expression. The method annotates this operand
    * expression and then assigns a unique integer tag to the unary operation expression. This annotation process is
    * crucial for maintaining the unique identification of each expression in the AST, particularly for unary operations
    * which are common in many programming constructs.
    *
    * The operand expression of the unary operation is annotated recursively before assigning the tag to the unary
    * operation itself. This ensures that the entire subtree rooted at the operand is also properly annotated.
    *
    * @param op
    *   The unary operation expression to be annotated.
    * @param cur
    *   The current annotation index, used for tagging the expression uniquely.
    * @return
    *   A tuple consisting of the annotated unary operation expression and the next annotation index. The annotation
    *   index is incremented after annotating the unary operation.
    */
  private def annotateUnaryOperation(op: UnaryOperation[Int], cur: Int): (UnaryOperation[Int], Int) = {
    val (exprAnnotated, curAnnotated) = annotateNode(op.expr, cur)
    op match {
      case Decrement(_, _) => (Decrement(exprAnnotated, curAnnotated), curAnnotated + 1)
      case Increment(_, _) => (Increment(exprAnnotated, curAnnotated), curAnnotated + 1)
      case Doubled(_, _)   => (Doubled(exprAnnotated, curAnnotated), curAnnotated + 1)
    }
  }

  /** Annotates a binary operation expression in an abstract syntax tree (AST).
    *
    * This method is responsible for annotating binary operation expressions, which include operations like addition
    * (`Plus`), subtraction (`Minus`), and multiplication (`Times`). Each binary operation consists of a left and right
    * expression. The method recursively annotates these expressions, ensuring that each sub-expression within them is
    * also annotated. It is crucial for maintaining the unique identification of each expression in the AST,
    * particularly for binary operations which are fundamental components of many programming constructs.
    *
    * @param op
    *   The binary operation expression to be annotated.
    * @param cur
    *   The current annotation index, used for tagging expressions uniquely.
    * @return
    *   A tuple consisting of the annotated binary operation expression and the next annotation index. The returned
    *   expression contains the annotated left and right expressions, along with the annotation index assigned to the
    *   binary operation itself.
    */
  private def annotateBinaryOperation(op: BinaryOperation[Int], cur: Int): (BinaryOperation[Int], Int) = {
    val (leftAnnotated, leftCur)   = annotateNode(op.left, cur)
    val (rightAnnotated, rightCur) = annotateNode(op.right, leftCur)
    op match {
      case Plus(_, _, _)  => (Plus(leftAnnotated, rightAnnotated, rightCur), rightCur + 1)
      case Minus(_, _, _) => (Minus(leftAnnotated, rightAnnotated, rightCur), rightCur + 1)
      case Times(_, _, _) => (Times(leftAnnotated, rightAnnotated, rightCur), rightCur + 1)
    }
  }

  /** Annotates an 'If' expression in an abstract syntax tree (AST).
    *
    * This method is responsible for annotating an 'If' expression, which consists of three parts: a predicate, a 'then'
    * branch, and an 'else' branch. The annotation process involves assigning a unique integer tag to each part of the
    * 'If' expression. The method recursively annotates the predicate, then branch, and else branch, ensuring that each
    * expression within these branches is also annotated. This is crucial for maintaining the unique identification of
    * each expression in the AST, which can be beneficial for various applications like optimization, debugging, or
    * further transformation of the AST.
    *
    * @param ifExpr
    *   The 'If' expression to be annotated.
    * @param cur
    *   The current annotation index, used for tagging expressions uniquely.
    * @return
    *   A tuple consisting of the annotated 'If' expression and the next annotation index. The returned 'If' expression
    *   contains the annotated predicate, then branch, and else branch, along with the annotation index assigned to the
    *   'If' expression itself.
    */
  private def annotateIf(ifExpr: If[Int], cur: Int): (If[Int], Int) = {
    val (predicateAnnotated, predCur) = annotateNode(ifExpr.predicate, cur)
    val (thenAnnotated, thenCur)      = annotateNode(ifExpr.thenBranch, predCur)
    val (elseAnnotated, elseCur)      = annotateNode(ifExpr.elseBranch, thenCur)
    (If(predicateAnnotated, thenAnnotated, elseAnnotated, elseCur), elseCur + 1)
  }

  /** Annotates a 'let' expression within an abstract syntax tree (AST).
    *
    * This method is responsible for annotating a 'let' expression, which involves binding a value to a variable and
    * then using this variable within a body expression. The annotation process assigns a unique integer tag to each
    * expression within the 'let' construct. The method recursively annotates both the expression to be bound (`expr`)
    * and the body of the 'let' expression where the bound variable is used.
    *
    * @param letExpr
    *   The 'let' expression to annotate.
    * @param cur
    *   The current annotation count, used to assign a unique integer to each expression.
    * @return
    *   A tuple containing the annotated 'let' expression and the updated annotation count. The 'let' expression
    *   includes the original symbol, the annotated expression and body, and the current annotation count as metadata.
    *   The annotation count is incremented after processing the body of the 'let' expression.
    */
  private def annotateLet(letExpr: Let[Int], cur: Int): (Let[Int], Int) = {
    val (exprAnnotated, exprCur) = annotateNode(letExpr.expr, cur)
    val (bodyAnnotated, bodyCur) = annotateNode(letExpr.body, exprCur)
    (Let(letExpr.sym, exprAnnotated, bodyAnnotated, bodyCur), bodyCur + 1)
  }

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

  private def transformExpressionToAnf[A](
      expr: Expression[A],
      context: List[(String, Expression[A])],
      counter: Int
  ): (Expression[A], List[(String, Expression[A])], Int) = expr match {
    case immediate @ (NumericLiteral(_, _) | IdLiteral(_, _)) => (immediate, context, counter)
    case e: UnaryOperation[A]                                 => transformUnaryOperationToAnf(e, context, counter)
    case e: BinaryOperation[A]                                => transformBinaryOperationToAnf(e, context, counter)
  }

  private def transformUnaryOperationToAnf[A](
      op: UnaryOperation[A],
      context: List[(String, Expression[A])],
      counter: Int
  ): (Expression[A], List[(String, Expression[A])], Int) = {
    val (newExpr, newContext, newCounter) = transformExpressionToAnf(op.expr, context, counter)
    val tempVar                           = s"${op.getClass.getSimpleName.toLowerCase}$$$newCounter"
    val updatedContext                    = newContext :+ (tempVar -> newExpr)
    op match
      case Decrement(_, _) => (Decrement(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
      case Increment(_, _) => (Increment(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
      case Doubled(_, _)   => (Doubled(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
  }

  private def transformBinaryOperationToAnf[A](
      op: BinaryOperation[A],
      context: List[(String, Expression[A])],
      counter: Int
  ): (Expression[A], List[(String, Expression[A])], Int) = {
    val (leftExpr, leftContext, leftCounter)    = transformExpressionToAnf(op.left, context, counter)
    val (rightExpr, rightContext, rightCounter) = transformExpressionToAnf(op.right, leftContext, leftCounter)
    val tempVar1                                = s"${leftExpr.getClass.getSimpleName.toLowerCase}$$$leftCounter"
    val tempVar2                                = s"${rightExpr.getClass.getSimpleName.toLowerCase}$$$rightCounter"
    op match
      case Plus(_, _, _)  => (Plus(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
      case Minus(_, _, _) => (Minus(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
      case Times(_, _, _) => (Times(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
  }
}
