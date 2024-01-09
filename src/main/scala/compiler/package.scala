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
      case Decrement(expr, _) => (Decrement(exprAnnotated, curAnnotated), curAnnotated + 1)
      case Increment(expr, _) => (Increment(exprAnnotated, curAnnotated), curAnnotated + 1)
      case Doubled(expr, _)   => (Doubled(exprAnnotated, curAnnotated), curAnnotated + 1)
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
      case Plus(_, _, _)  => (Plus(leftAnnotated, rightAnnotated, cur), rightCur + 1)
      case Minus(_, _, _) => (Minus(leftAnnotated, rightAnnotated, cur), rightCur + 1)
      case Times(_, _, _) => (Times(leftAnnotated, rightAnnotated, cur), rightCur + 1)
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
    (If(predicateAnnotated, thenAnnotated, elseAnnotated, cur), elseCur + 1)
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
    (Let(letExpr.sym, exprAnnotated, bodyAnnotated, cur), bodyCur + 1)
  }
}
