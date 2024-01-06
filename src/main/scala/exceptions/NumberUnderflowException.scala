
package cl.ravenhill.scum
package exceptions

/**
 * Exception class representing a number underflow condition.
 * 
 * @param n
 *   The numeric value that triggered the underflow.
 * @param min
 *   The minimum permissible value that `n` fell below.
 */
class NumberUnderflowException(n: Long, min: Long) extends Exception(s"Number underflow: $n < $min")
