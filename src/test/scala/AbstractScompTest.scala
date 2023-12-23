
package cl.ravenhill.scomp

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

abstract class AbstractScompTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks
