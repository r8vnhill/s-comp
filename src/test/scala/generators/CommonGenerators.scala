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

  /** Generates a random integer within a specified range.
    *
    * @param min
    *   The minimum value of the range (inclusive). Defaults to `Int.MinValue`.
    * @param max
    *   The maximum value of the range (inclusive). Defaults to `Int.MaxValue`.
    * @return
    *   A generator for integers within the specified range.
    */
  def generateLong(min: Long = Long.MinValue, max: Long = Long.MaxValue): Gen[Int] = Gen.long

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
    bindings <- Gen.listOf(Gen.zip(generateStringLabel, generateLong()))
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
