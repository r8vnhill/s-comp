package cl.ravenhill.scum

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.util.concurrent.atomic.AtomicInteger

abstract class AbstractScumTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks {
  given PropertyCheckConfiguration = // Override default property check configuration
    PropertyCheckConfiguration(minSuccessful = 10, workers = 4)
}
