package cl.ravenhill.scomp

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
  }
}
