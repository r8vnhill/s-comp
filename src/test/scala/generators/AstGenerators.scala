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
  def generateNumericLiteral(value: Gen[Int] = generateLong()): Gen[NumericLiteral[String]] = for {
    v <- value
    n <- Gen.const(NumericLiteral[String](v))
  } yield n
  
  def generateIdLiteral(environment: Environment = Environment.empty): Gen[IdLiteral[String]] = for {
    name <- Gen.frequency(
      (1, generateStringLabel),
      (10, if environment.isEmpty then generateStringLabel else Gen.oneOf(environment.boundNames.toSeq))
    )
    idLiteral <- Gen.const(IdLiteral(name))
  } yield idLiteral

  def generateTerminal: Gen[Expression[String]] = Gen.oneOf(generateNumericLiteral(), generateIdLiteral())

  def generateIncrement(maxDepth: Int = 10): Gen[Increment[String]] = for {
    expr <- generateExpression(maxDepth - 1)
    inc  <- Gen.const(Increment(expr))
  } yield inc

  def generateDecrement(maxDepth: Int = 10): Gen[Decrement[String]] = for {
    expr <- generateExpression(maxDepth - 1)
    dec  <- Gen.const(Decrement(expr))
  } yield dec

  def generateLet(maxDepth: Int = 10): Gen[Let[String]] = for {
    name <- generateStringLabel
    expr <- generateExpression(maxDepth - 1)
    body <- generateExpression(maxDepth - 1)
    let  <- Gen.const(Let(name, expr, body))
  } yield let

  def generateIf(maxDepth: Int = 10): Gen[If[String]] = for {
    cond     <- generateExpression(maxDepth - 1)
    thenExpr <- generateExpression(maxDepth - 1)
    elseExpr <- generateExpression(maxDepth - 1)
    ifExpr   <- Gen.const(If(cond, thenExpr, elseExpr))
  } yield ifExpr

  def generateFunction(maxDepth: Int = 10): Gen[Expression[String]] =
    Gen.frequency(
      (1, generateIncrement(maxDepth)),
      (1, generateDecrement(maxDepth)),
      (3, generateLet(maxDepth)),
      (2, generateIf(maxDepth))
    )

  def generateExpression(maxDepth: Int = 10): Gen[Expression[String]] = maxDepth match {
    case 0 => generateTerminal
    case _ => Gen.frequency((4, generateTerminal), (1, generateFunction(maxDepth)))
  }
}
