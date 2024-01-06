package cl.ravenhill.scum

import generators.*

import org.scalacheck.Gen

import scala.util.{Failure, Success}

class EnvironmentTest extends AbstractScumTest with CommonGenerators {
  "Environment instance" - {
    "can be created with" - {
      "a map" in {
        val gen = Gen.nonEmptyMap(Gen.zip(generateStringLabel, generateInt()))
        forAll(gen) { map =>
          val env = Environment(map)
          env.boundNames should contain theSameElementsAs map.keys
        }
      }

      "a list of pairs" in {
        val gen = Gen.nonEmptyListOf(Gen.zip(generateStringLabel, generateInt()))
        forAll(gen) { list =>
          val env = Environment(list: _*)
          env.boundNames should contain theSameElementsAs list.toMap.keys
        }
      }

      "its companion object" - {
        "as empty" in {
          val env = Environment.empty
          env.boundNames shouldBe empty
        }
      }
    }

    "when checking emptiness" - {
      "is empty when created with no bindings" in {
        val env = Environment.empty
        env.isEmpty shouldBe true
      }

      "is not empty when created with bindings" in {
        forAll(generateNonEmptyEnvironment) { env =>
          env.isEmpty shouldBe false
        }
      }
    }

    "can be extended with a new name" - {
      "when the name is not already bound" in {
        forAll(
          Gen
            .mapOf(Gen.zip(generateStringLabel, generateInt()))
            .flatMap(map => generateStringLabel.filterNot(map.contains).map(_ -> map))
        ) { case (name, map) =>
          val env      = Environment(map)
          val extended = env + name
          extended.boundNames should contain theSameElementsAs (map.keys.toList :+ name)
        }
      }

      "when the name is already bound" in {
        forAll(
          Gen
            .nonEmptyMap(Gen.zip(generateStringLabel, generateInt()))
            .flatMap(map => Gen.oneOf(map.keys.toList).map(_ -> map))
        ) { case (name, map) =>
          whenever(map.contains(name)) {
            val env      = Environment(map)
            val extended = env + name
            extended.boundNames should contain theSameElementsAs map.keys.toList
          }
        }
      }
    }

    "when looking up a name" - {
      "returns the value bound to the name" in {
        forAll(
          generateNonEmptyEnvironment
            .flatMap(env => Gen.oneOf(env.boundNames).map(_ -> env))
        ) { case (name, env) =>
          env(name) match
            case Success(value) => env.bindings(name) shouldBe value
            case _              => fail("The name should be bound")
        }
      }

      "fails when the name is not bound" in {
        forAll(
          generateEnvironment()
            .flatMap(env => generateStringLabel.filterNot(env.bindings.contains).map(_ -> env))
        ) { case (name, env) =>
          env(name) should matchPattern { case Failure(_: NoSuchElementException) => }
        }
      }
    }
  }
}
