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

    /** Generates a random string label suitable for variable names, identifiers, or labels.
      *
      * This method creates a generator for string labels that are composed of a mix of uppercase and lowercase letters,
      * numerals, and underscores. The generated strings conform to common programming language identifier rules: they
      * must be non-empty, cannot start with a digit, and are limited to a reasonable length for readability and
      * practicality.
      *
      * The method uses ScalaCheck's `Gen` functionality to create strings with a balanced frequency of character types.
      * Uppercase letters are less frequent compared to lowercase ones, numerals and underscores are even less common.
      * This distribution aims to produce realistic and varied identifiers.
      *
      * The length of the generated string is capped at 16 characters to maintain readability and manageability in test
      * cases.
      *
      * @return
      *   A `Gen[String]` that produces random string labels adhering to typical identifier rules and composition.
      */
    def stringLabel: Gen[String] = {
      for {
        list <- Gen.resize(
          16,
          Gen.nonEmptyListOf(
            Gen.frequency(
              (5, Gen.alphaChar.map(_.toUpper)),  // Uppercase letters with lower frequency
              (25, Gen.alphaChar.map(_.toLower)), // Lowercase letters with higher frequency
              (2, Gen.numChar),                   // Numerals with moderate frequency
              (1, Gen.const('_'))                 // Underscores with lower frequency
            )
          )
        )
        if list.nonEmpty      // Ensure the generated list is not empty
        if !list.head.isDigit // First character must not be a digit
      } yield list.mkString   // Combine the characters into a single string
    }

    /** Generates a random [[Environment]] instance, possibly empty.
      *
      * This method creates a generator for `Environment` instances. It produces environments with a list of variable
      * name and slot number pairs. The variable names are generated as string labels, and slot numbers are generated as
      * integers. The pairs are then used to construct an `Environment` object.
      *
      * This generator can produce both empty and non-empty environments, making it suitable for a wide range of testing
      * scenarios where different environment states are required.
      *
      * @return
      *   A `Gen[Environment]` that produces `Environment` instances with randomly generated variable mappings.
      */
    def environment(): Gen[Environment] = for {
      pairs <- gen.listOf(gen.zip(gen.stringLabel, gen.int()))
    } yield Environment(pairs: _*)

    /** Generates a non-empty `Environment` instance.
      *
      * This method creates a generator for `Environment` instances, ensuring that the generated environment is not
      * empty. It leverages the `environment` method to generate random environments and then filters out any that are
      * empty, guaranteeing that the resulting `Environment` contains at least one variable mapping.
      *
      * This generator is particularly useful in testing scenarios where an environment with at least one variable is
      * required to adequately test functionality.
      *
      * @return
      *   A `Gen[Environment]` that produces non-empty `Environment` instances.
      */
    def nonEmptyEnvironment(): Gen[Environment] = environment().suchThat(_.boundNames.nonEmpty)
  }
}
