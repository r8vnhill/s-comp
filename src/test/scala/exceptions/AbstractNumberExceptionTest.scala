package cl.ravenhill.scum
package exceptions

abstract class AbstractNumberExceptionTest extends AbstractScumTest {

  /** Tests that the constructor of a given exception sets the appropriate message.
    *
    * This method is designed to automate the testing of exception message formatting for different types of exceptions.
    * It accepts a function to create an exception, a regular expression pattern for the expected message format, and a
    * string representing the comparison sign used in the message. The method then uses property-based testing to verify
    * that the message set by the exception's constructor matches the expected format.
    *
    * __Usage:__ This method is used in test classes for exceptions to validate that the exception message is correctly
    * formatted according to the specifications. It is particularly useful for testing exceptions that format messages
    * with dynamic content, such as numeric values or comparison signs.
    *
    * @param exceptionCreator
    *   A function that takes two Long parameters and returns an Exception. This function is used to create instances of
    *   the exception with different values for testing.
    * @param messageRegex
    *   A string representing the regular expression pattern that the exception message is expected to match. This
    *   pattern should include placeholders for the dynamic parts of the message.
    * @param comparisonSign
    *   A string representing the comparison sign (e.g., ">", "<") used in the exception message.
    * @example
    *   {{{
    * protected def testConstructorSetsTheMessage(): Unit = {
    *   testConstructorSetsTheMessage(
    *     (n, max) => NumberOverflowException(n, max),
    *     """Number overflow: (-?\d+) > (-?\d+)""",
    *     ">"
    *   )
    * }
    *   }}}
    */
  protected def `test constructor sets the message`(
      exceptionCreator: (Long, Long) => Exception,
      messageRegex: String,
      comparisonSign: String
  ): Unit = {
    s"${exceptionCreator.getClass.getSimpleName}'s constructor" - {
      "sets the message" in {
        forAll { (n: Long, max: Long) =>
          val exception = exceptionCreator(n, max)
          exception.getMessage should fullyMatch regex (messageRegex.r withGroups (n.toString, max.toString))
        }
      }
    }
  }

}
