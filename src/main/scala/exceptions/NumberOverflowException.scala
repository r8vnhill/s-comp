package cl.ravenhill.scum
package exceptions

/** Exception class representing a number overflow condition.
  *
  * @param n
  *   The numeric value that caused the overflow.
  * @param max
  *   The maximum allowed value that `n` exceeded.
  */
class NumberOverflowException(n: Long, max: Long) extends Exception(s"Number overflow: $n > $max")
