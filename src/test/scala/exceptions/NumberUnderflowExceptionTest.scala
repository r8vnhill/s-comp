package cl.ravenhill.scum
package exceptions

class NumberUnderflowExceptionTest extends AbstractNumberExceptionTest {

  `test constructor sets the message`(NumberUnderflowException(_, _), """Number underflow: (-?\d+) < (-?\d+)""", "<")
}
