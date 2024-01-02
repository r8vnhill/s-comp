package cl.ravenhill.scum

import org.scalacheck.Gen

package object generators {

  extension (gen: Gen.type) {

    /** Generates a random integer within the specified range.
      *
      * This method extends the `Gen` object to create a generator that produces random integers within a specified
      * range. The range is defined by a minimum and maximum value.
      *
      * @param min
      *   The minimum value of the range. Defaults to `Int.MinValue`.
      * @param max
      *   The maximum value of the range. Defaults to `Int.MaxValue`.
      * @return
      *   A `Gen[Int]` that produces random integers within the specified range.
      */
    def int(min: Int = Int.MinValue, max: Int = Int.MaxValue): Gen[Int] = gen.choose(min, max)

    /** Generates random string labels consisting of ASCII characters.
      *
      * This method creates a generator for string labels using ScalaCheck's `Gen.asciiStr`. It ensures that the
      * generated strings are non-empty and consist only of ASCII characters, including alphanumeric characters (both
      * uppercase and lowercase) and underscores. The generated labels conform to a pattern often used in programming
      * contexts, such as variable names, identifiers, or labels in assembly language.
      *
      * The method applies two constraints:
      *   1. The string must be non-empty. 2. The string must match the regular expression "^[a-zA-Z0-9_]+$", which
      *      includes letters (both cases), digits, and underscores, starting from the beginning (^) to the end ($) of
      *      the string.
      *
      * This generator is particularly useful for property-based testing scenarios where valid identifiers or labels are
      * required.
      *
      * @return
      *   A `Gen[String]` that produces non-empty ASCII strings matching the specified regular expression.
      */
    def stringLabel: Gen[String] = Gen.asciiStr
      .suchThat(_.nonEmpty)
      .suchThat(_.matches("^[a-zA-Z0-9_]+$"))
  }
}
