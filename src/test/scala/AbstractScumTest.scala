package cl.ravenhill.scum

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

abstract class AbstractScumTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks 