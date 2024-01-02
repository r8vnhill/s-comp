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
  def num(value: Gen[Int] = Gen.int()): Gen[ast.terminal.Num[String]] =
    gen.const(ast.terminal.Num[String](value.sample.get))

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
  def terminal: Gen[ast.terminal.Num[String]] = gen.num()

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
  def increment(maxDepth: Int = 5): Gen[ast.unary.Increment[String]] =
    gen.const(ast.unary.Increment(expr(maxDepth - 1).sample.get))

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
  def decrement(maxDepth: Int = 5): Gen[ast.unary.Decrement[String]] =
    gen.const(ast.unary.Decrement(expr(maxDepth - 1).sample.get))

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
  def let(maxDepth: Int = 5): Gen[ast.Let[String]] =
    gen.const(ast.Let(Gen.alphaNumStr.sample.get, expr(maxDepth - 1).sample.get, expr(maxDepth - 1).sample.get))

  /** Generates a random instance of a unary function expression, either an increment, a decrement, or a let binding,
    * within the `ast` package.
    *
    * This method extends the `Gen` object to create a generator for unary function expressions. It now includes
    * instances of increment, decrement, and let binding operations encapsulated within the `Expr` class. The generated
    * expressions are ideal for contexts like property-based testing, where a diverse range of expressions is beneficial
    * for a comprehensive evaluation of expression handling logic.
    *
    * The complexity of the nested expressions within these unary functions is controlled by the `maxDepth` parameter.
    * This parameter enables the creation of expressions with various levels of complexity, accommodating both
    * straightforward and more intricate expression structures.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree, defaulting to 5. This parameter influences the complexity of the
    *   nested expressions within the generated unary function expression. A `maxDepth` of 0 results in simple
    *   expressions, while higher values allow for more complex, nested expressions.
    * @return
    *   A `Gen[ast.Expr]` that produces instances of unary function expressions in the `ast` package, specifically
    *   increment, decrement, or let binding operations. The type of unary function and the complexity of its nested
    *   expression are determined based on the specified maximum depth.
    */
  def function(maxDepth: Int = 5): Gen[ast.Expr[String]] =
    gen.frequency((1, increment(maxDepth)), (1, decrement(maxDepth)), (3, let(maxDepth)))

  /** Generates an instance of the `Expr` class from the `ast` package, representing an expression.
    *
    * This method extends the `Gen` object to create a generator for `ast.Expr` objects, which represent expressions in
    * a simple expression language. The complexity of the generated expression is controlled by the `maxDepth`
    * parameter. This generator is versatile, producing both simple terminal expressions and more complex
    * function-invoking expressions, depending on the specified depth.
    *
    * The method is particularly useful in property-based testing contexts, where it's beneficial to test the expression
    * language implementation with a wide range of expression complexities and structures.
    *
    * @param maxDepth
    *   The maximum depth of the expression tree, defaulting to 5. It determines the complexity of the generated
    *   expression. A `maxDepth` of 0 results in a terminal expression (e.g., a number), whereas greater depths allow
    *   for more complex expressions, including functions.
    * @return
    *   A `Gen[ast.Expr]` that produces instances of `ast.Expr`. The type of expression generated (terminal or
    *   function-invoking) is based on the specified maximum depth.
    */
  def expr(maxDepth: Int = 10): Gen[ast.Expr[String]] = maxDepth match {
    case 0 => gen.terminal
    case _ => gen.frequency((2, gen.terminal), (1, gen.function(maxDepth)))
  }
}
