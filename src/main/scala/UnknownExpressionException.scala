package cl.ravenhill.scum

/** Exception class for handling unknown expression scenarios.
  *
  * This class represents an exception that is thrown when an unknown or unsupported expression is encountered in the
  * context of expression parsing or evaluation. It extends the standard [[Exception]] class in Scala, allowing it to be
  * used in typical exception handling workflows.
  *
  * `UnknownExpressionException` is typically thrown when an expression cannot be recognized or processed by the
  * expression parsing or evaluation functions. This could happen, for instance, if the expression format does not match
  * any known patterns or if it contains unsupported symbols or operations.
  *
  * @param message
  *   A descriptive message explaining the details of the exception. This message is intended to provide insights into
  *   what caused the exception, which can be particularly helpful for debugging purposes.
  */
class UnknownExpressionException(message: String) extends Exception(message)
