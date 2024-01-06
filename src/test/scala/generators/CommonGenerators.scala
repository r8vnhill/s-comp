package cl.ravenhill.scum
package generators

import org.scalacheck.Gen

/** Trait providing common generators for property-based testing.
  *
  * This trait includes a collection of generator methods that can be used to produce test data for various types, such
  * as integers, strings, and environments. These generators are particularly useful for property-based testing where a
  * wide range of input data is required to thoroughly test the functionality.
  */
trait CommonGenerators {

  /** Generates a random long value within a specified range.
    *
    * This method creates a generator for Long values, allowing the specification of minimum and maximum bounds. It is
    * designed to produce a uniformly distributed range of long values within the given bounds, inclusive. This is
    * particularly useful in property-based testing where you need to test functionalities with a wide variety of long
    * integer inputs.
    *
    * @param min
    *   The minimum value (inclusive) of the range. Defaults to `Long.MinValue`.
    * @param max
    *   The maximum value (inclusive) of the range. Defaults to `Long.MaxValue`.
    * @return
    *   A generator that produces random Long values within the specified range.
    */
  def generateLong(min: Long = Long.MinValue, max: Long = Long.MaxValue): Gen[Long] = Gen.chooseNum(min, max)

  /** Generates a random integer value within a specified range.
    *
    * This method creates a generator for Int values that are uniformly distributed within the specified range, defined
    * by the `min` and `max` parameters. The range includes both the minimum and maximum values. This generator is
    * particularly useful in property-based testing where diverse integer inputs are required to thoroughly test
    * numerical computations, boundary conditions, and other functionalities that operate on integers.
    *
    * @param min
    *   The minimum value (inclusive) of the range. Defaults to `Int.MinValue`.
    * @param max
    *   The maximum value (inclusive) of the range. Defaults to `Int.MaxValue`.
    * @return
    *   A generator that produces random Int values within the specified range.
    */
  def generateInt(min: Int = Int.MinValue, max: Int = Int.MaxValue): Gen[Int] = Gen.choose(min, max)

  /** Generates a random string label.
    *
    * The label is composed of a combination of upper and lower case alphabetic characters, numerals, and underscores.
    * The first character is guaranteed to be an alphabetic character, and the label will be non-empty.
    *
    * @return
    *   A generator for string labels.
    */
  def generateStringLabel: Gen[String] = {
    for {
      list <- Gen.resize(
        16,
        Gen.nonEmptyListOf(
          Gen.frequency(
            (5, Gen.alphaChar.map(_.toUpper)),
            (25, Gen.alphaChar.map(_.toLower)),
            (2, Gen.numChar),
            (1, Gen.const('_'))
          )
        )
      )
      if list.nonEmpty
      if !list.head.isDigit
    } yield list.mkString
  }

  /** Generates an `Environment` with random bindings.
    *
    * Each binding in the environment is a pair of a randomly generated string label and an integer value.
    *
    * @return
    *   A generator for `Environment` objects.
    */
  def generateEnvironment(): Gen[Environment] = for {
    bindings <- Gen.listOf(Gen.zip(generateStringLabel, generateInt()))
  } yield Environment(bindings: _*)

  /** Generates a non-empty `Environment` with random bindings.
    *
    * Similar to `generateEnvironment`, but guarantees that the generated `Environment` is not empty.
    *
    * @return
    *   A generator for non-empty `Environment` objects.
    */
  def generateNonEmptyEnvironment: Gen[Environment] = generateEnvironment().suchThat(_.boundNames.nonEmpty)
}
