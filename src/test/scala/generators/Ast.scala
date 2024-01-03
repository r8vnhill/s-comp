package cl.ravenhill.scum
package generators

import org.scalacheck.Gen

extension (gen: Gen.type) {

  /** Generates an instance of the `Num` class with a potentially randomized numeric value.
    *
    * This method extends the [[Gen]] object to create a generator for [[ast.terminal.Num]] objects. It encapsulates a
    * numeric value, which can be either provided or generated randomly. The method primarily serves in contexts such as
    * property-based testing where instances of `Num` with varying values are required.
    *
    * @param value
    *   An optional generator for the integer value of the `Num` instance. If not provided, the method defaults to using
    *   a random integer generator provided by `Gen.int()`.
    * @return
    *   A `Gen[ast.terminal.Num]` that produces instances of `ast.terminal.Num`. The numeric value within each `Num`
    *   instance is either the supplied value or a random value generated by `value.sample.get`.
    */
  def num(value: Gen[Int] = Gen.int()): Gen[ast.terminal.Num[String]] = for {
    v <- value
    n <- Gen.const(ast.terminal.Num[String](v))
  } yield n

  /** Generates a random `Var` expression using string labels.
   *
   * This function creates a generator for `Var` expressions, which represent variable references in an abstract syntax
   * tree (AST). Each `Var` expression is characterized by a name, which is a string label in this context. The
   * generator uses [[Gen.stringLabel]] to create random string labels that conform to typical identifier patterns
   * (alphanumeric characters and underscores).
   *
   * The generated string label is then used to create a `Var` expression instance. This method is particularly useful
   * in property-based testing scenarios where diverse instances of variable expressions are beneficial for thorough
   * evaluation of expression handling logic.
   *
   * @return
   * A `Gen[ast.terminal.Var[String]]` that produces randomly generated `Var` expressions with string labels.
   */
  def varExpr: Gen[ast.terminal.Var[String]] = for {
    name <- Gen.stringLabel
    varExpr <- Gen.const(ast.terminal.Var(name))
  } yield varExpr

  /** Generates an instance of the `Num` class from the `ast.terminal` package.
    *
    * This method extends the `Gen` object to create a generator specifically for generating instances of
    * `ast.terminal.Num`. It utilizes the `num` method defined in the `Gen` object to produce instances of `Num`, which
    * encapsulate numeric values. This generator is particularly useful in contexts such as property-based testing,
    * where varying instances of terminal numeric expressions are needed.
    *
    * The `terminal` method provides a streamlined way to generate `Num` instances without needing to specify the
    * details of the numeric value generation, which are handled by the `num` method.
    *
    * @return
    *   A `Gen[ast.terminal.Num]` that produces instances of `ast.terminal.Num`. The numeric values within these
    *   instances are determined by the underlying logic of the `num` method, which may involve randomized value
    *   generation.
    */
  def terminal: Gen[ast.Expr[String]] = gen.oneOf(gen.num(), gen.varExpr)

  /** Generates an instance of the `Increment` class from the `ast.unary` package.
    *
    * This method extends the `Gen` object to create a generator for `ast.unary.Increment` objects. It is used to
    * generate instances of the `Increment` class, which represent an increment operation applied to an expression. The
    * method can be particularly useful in property-based testing, where it is beneficial to have varying instances of
    * unary increment expressions.
    *
    * The depth of the expression to be incremented is controlled by the `maxDepth` parameter, allowing for the
    * generation of increment expressions with varying complexities. This approach ensures that generated expressions do
    * not exceed a specified depth, thereby avoiding excessively deep recursive structures in tests.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree. Defaults to 5. It determines how complex the nested expressions can
    *   be.
    * @return
    *   A `Gen[ast.unary.Increment]` that produces instances of `ast.unary.Increment`. Each instance contains an
    *   expression to be incremented, generated based on the specified maximum depth.
    */
  def increment(maxDepth: Int = 10): Gen[ast.unary.Increment[String]] = for {
    expr <- gen.expr(maxDepth - 1)
    inc  <- gen.const(ast.unary.Increment(expr))
  } yield inc

  /** Generates an instance of the `Decrement` class from the `ast.unary` package.
    *
    * This method extends the `Gen` object to create a generator for `ast.unary.Decrement` objects, representing a
    * decrement operation applied to an expression. The generator is designed for use in contexts such as property-based
    * testing, where it's beneficial to create varying instances of unary decrement expressions for comprehensive
    * evaluation of expression-handling logic.
    *
    * The complexity of the expression subjected to the decrement operation is governed by the `maxDepth` parameter.
    * This approach allows for the creation of decrement expressions with varying levels of nested expression
    * complexity, thus aiding in testing different scenarios involving decrement operations.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree. Defaults to 5. It determines the complexity of the nested expressions
    *   within the generated `Decrement` instance. A `maxDepth` of 0 results in a simple expression, while greater
    *   depths allow for more complex, nested expressions.
    * @return
    *   A `Gen[ast.unary.Decrement]` that produces instances of `ast.unary.Decrement`. Each generated instance contains
    *   an expression subject to decrement, determined based on the specified maximum depth.
    */
  def decrement(maxDepth: Int = 10): Gen[ast.unary.Decrement[String]] = for {
    expr <- gen.expr(maxDepth - 1)
    dec  <- gen.const(ast.unary.Decrement(expr))
  } yield dec

  /** Generates a `Let` expression for testing purposes.
    *
    * This method creates a generator for `Let` expressions, which are part of the AST (Abstract Syntax Tree). It uses a
    * string generator for variable names and recursively generates the expressions for `Let` bindings. This method is
    * particularly useful in property-based testing, where various instances of `Let` expressions are required.
    *
    * @param maxDepth
    *   The maximum depth for generating nested expressions within the `Let` construct.
    * @return
    *   A generator that produces `Let` expressions with randomly generated components.
    */
  def let(maxDepth: Int = 10): Gen[ast.Let[String]] = for {
    name <- Gen.stringLabel
    expr <- gen.expr(maxDepth - 1)
    body <- gen.expr(maxDepth - 1)
    let  <- Gen.const(ast.Let(name, expr, body))
  } yield let

  /** Generates a random `If` expression with a specified maximum depth.
    *
    * This function creates a generator for `If` expressions, a fundamental construct in programming representing
    * conditional logic. The `If` expression consists of a condition (`cond`), a 'then' expression (`thenExpr`), and an
    * 'else' expression (`elseExpr`). Each of these components is itself an expression, potentially comprising complex
    * nested structures.
    *
    * The generator recursively creates each part of the `If` expression with a depth one less than the specified
    * `maxDepth`, ensuring that the total depth of the resulting `If` expression doesn't exceed the provided limit. This
    * approach helps in managing the complexity of the generated expressions and prevents excessively deep recursion
    * which could lead to stack overflow errors.
    *
    * @param maxDepth
    *   The maximum depth of the generated `If` expression tree. Defaults to 10.
    * @return
    *   A `Gen[ast.If[String]]` that produces randomly generated `If` expressions within the specified depth.
    */
  def ifExpr(maxDepth: Int = 10): Gen[ast.If[String]] = for {
    cond     <- gen.expr(maxDepth - 1)
    thenExpr <- gen.expr(maxDepth - 1)
    elseExpr <- gen.expr(maxDepth - 1)
    ifExpr   <- Gen.const(ast.If(cond, thenExpr, elseExpr))
  } yield ifExpr

  /** Generates a random instance of a function expression, including unary operations and let bindings, within the
    * `ast` package.
    *
    * This method extends the `Gen` object to create a generator for various types of function expressions within the
    * `ast` package. It includes instances of increment, decrement, let binding operations, and conditional (`if`)
    * expressions, encapsulated within the `Expr` class. This variety in expression types is beneficial for contexts
    * like property-based testing, where a diverse range of expressions enhances the thoroughness of expression handling
    * logic evaluation.
    *
    * The `frequency` method from ScalaCheck's `Gen` is used to assign relative frequencies to different types of
    * expressions, ensuring a varied distribution. The complexity of the nested expressions is controlled by the
    * `maxDepth` parameter, allowing the creation of expressions ranging from simple to intricate structures.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree, defaulting to 10. This parameter influences the complexity of the
    *   nested expressions within the generated function expressions. A `maxDepth` of 0 leads to simple expressions,
    *   while higher values enable the generation of more complex, nested expressions.
    * @return
    *   A `Gen[ast.Expr[String]]` that produces instances of function expressions, including increment, decrement, let
    *   binding, and conditional (`if`) operations. The specific type of function and the complexity of its nested
    *   expression are determined based on the specified maximum depth and the assigned frequencies.
    */
  def function(maxDepth: Int = 10): Gen[ast.Expr[String]] =
    gen.frequency((1, increment(maxDepth)), (1, decrement(maxDepth)), (3, let(maxDepth)), (2, ifExpr(maxDepth)))

  /** Generates an instance of the [[ast.Expr Expr]] class from the [[ast]] package, representing an expression.
    *
    * This method extends the [[Gen]] object to create a generator for ``Expr`` objects, which represent expressions
    * in a simple expression language. The generator is capable of producing both simple terminal expressions (such as
    * numbers or variables) and more complex expressions (including function-invoking expressions like increment,
    * decrement, let bindings, and conditionals) depending on the specified depth. The complexity of the generated
    * expression is controlled by the [[maxDepth]] parameter.
    *
    * The method is particularly useful in property-based testing contexts, where it's beneficial to test the expression
    * language implementation with a wide range of expression complexities and structures. The `frequency` method from
    * ScalaCheck's `Gen` is used to assign relative frequencies to different types of expressions, ensuring a varied
    * distribution.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree, defaulting to 10. It determines the complexity of the generated
    *   expression. A `maxDepth` of 0 results in a terminal expression (e.g., a number or a variable), whereas greater
    *   depths allow for more complex expressions, including various function-invoking operations.
    * @return
    *   A `Gen[ast.Expr[String]]` that produces instances of `ast.Expr`. The specific type of expression generated
    *   (terminal or function-invoking) is based on the specified maximum depth and the assigned frequencies.
    */
  def expr(maxDepth: Int = 10): Gen[ast.Expr[String]] = maxDepth match {
    case 0 => gen.terminal
    case _ => gen.frequency((4, gen.terminal), (1, gen.function(maxDepth)))
  }
}
