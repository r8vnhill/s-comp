package cl.ravenhill.scum
package exceptions

class NumberOverflowExceptionTest extends AbstractNumberExceptionTest {

  `test constructor sets the message`(NumberOverflowException(_, _), """Number overflow: (-?\d+) > (-?\d+)""", ">")
}
