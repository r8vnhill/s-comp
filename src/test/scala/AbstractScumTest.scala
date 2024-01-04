package cl.ravenhill.scum

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.util.concurrent.atomic.AtomicInteger

abstract class AbstractScumTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks {
  private val counter: AtomicInteger = AtomicInteger(0)
  given Metadata[String] with {
    override def data: String = { // Simple gensym implementation for testing
      counter.getAndAdd(1)
      s"metadata $counter"
    }

    override def toString: String = data
  }

  given PropertyCheckConfiguration = // Override default property check configuration
    PropertyCheckConfiguration(minSuccessful = 1000, workers = 4)
}
