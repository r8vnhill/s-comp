package cl.ravenhill.scum
package generators

import ast.*

import org.scalacheck.Gen

/** Trait providing generators for creating random instances of various abstract syntax tree (AST) elements.
  *
  * This trait extends `CommonGenerators` and includes a suite of generator methods to produce random AST elements such
  * as numeric literals, identifier literals, and various types of expressions (like increment, decrement, let, and if
  * expressions). These generators are particularly useful for property-based testing where diverse AST instances are
  * required to thoroughly test the functionalities of compilers or interpreters.
  */
trait AstGenerators extends CommonGenerators {

  /** Generates a random numeric literal for use in abstract syntax tree (AST) testing.
    *
    * This method creates a generator for `NumericLiteral[String]` instances. It is used to generate random numeric
    * literals, which are fundamental elements in ASTs for most programming languages. The method leverages a specified
    * integer generator to create the numeric value of the literal.
    *
    * @param value
    *   A generator for integer values that will be used to create the numeric value of the literal. By default, it uses
    *   `generateInt()`, which generates any integer.
    * @return
    *   A generator that produces instances of `NumericLiteral[String]`, each containing a randomly generated integer.
    * @see
    *   [[cl.ravenhill.scum.generators.CommonGenerators.generateLong]]
    */
  def generateNumericLiteral(value: Gen[Long] = generateLong()): Gen[NumericLiteral[String]] = for {
    v <- value
    n <- Gen.const(NumericLiteral[String](v))
  } yield n

  def generateIdLiteral(environment: Environment): Gen[IdLiteral[String]] = for {
    idLiteral <- Gen.oneOf(environment.boundNames.toSeq).map(IdLiteral[String](_, None))
  } yield idLiteral

  def generateTerminal(environment: Environment): Gen[Expression[String]] =
    Gen.oneOf(generateNumericLiteral(), generateIdLiteral(environment))

  def generateIncrement(maxDepth: Int = 10, environment: Environment): Gen[Increment[String]] = for {
    expr <- generateExpression(maxDepth - 1, environment)
    inc  <- Gen.const(Increment(expr))
  } yield inc

  def generateDecrement(maxDepth: Int = 10, environment: Environment): Gen[Decrement[String]] = for {
    expr <- generateExpression(maxDepth - 1, environment)
    dec  <- Gen.const(Decrement(expr))
  } yield dec

  def generateLet(maxDepth: Int = 10, environment: Environment): Gen[Let[String]] = for {
    name <- generateStringLabel
    expr <- generateExpression(maxDepth - 1)
    body <- generateExpression(maxDepth - 1, environment + name)
    let  <- Gen.const(Let(name, expr, body))
  } yield let

  def generateIf(maxDepth: Int = 10, environment: Environment): Gen[If[String]] = for {
    cond     <- generateExpression(maxDepth - 1, environment)
    thenExpr <- generateExpression(maxDepth - 1, environment)
    elseExpr <- generateExpression(maxDepth - 1, environment)
    ifExpr   <- Gen.const(If(cond, thenExpr, elseExpr))
  } yield ifExpr

  def generateFunction(maxDepth: Int = 10, environment: Environment): Gen[Expression[String]] =
    Gen.frequency(
      (1, generateIncrement(maxDepth, environment)),
      (1, generateDecrement(maxDepth, environment)),
      (3, generateLet(maxDepth, environment)),
      (2, generateIf(maxDepth, environment))
    )

  def generateExpression(
      maxDepth: Int = 10,
      environment: Environment = Environment("ZERO" -> 0)
  ): Gen[Expression[String]] = for {
    expr <- maxDepth match {
      case 0 => generateTerminal(environment)
      case _ => Gen.frequency((2, generateTerminal(environment)), (1, generateFunction(maxDepth, environment)))
    }
  } yield expr
}
