package cl.ravenhill.scum

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

abstract class AbstractScumTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks {
  private var counter = 0
  given Metadata[String] with {
    override def data: String = { // Simple gensym implementation for testing
      counter += 1
      s"metadata $counter"
    }
    
    override def toString: String = data
  }
}