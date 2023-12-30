package cl.ravenhill.scum

import scala.util.Success

class EnvironmentTest extends AbstractScumTest {
  "An Environment instance" - {
    "when accessing a variable" - {
      "should return a success with the value" in {
        val env = Environment(Map("x" -> 1))
        env("x") should matchPattern { case util.Success(1) => }
      }

      "should return a failure if the variable is not defined" in {
        val env = Environment(Map("x" -> 1))
        env("y") should matchPattern { case util.Failure(_) => }
      }
    }
  }
}
