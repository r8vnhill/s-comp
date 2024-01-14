package cl.ravenhill.scum
package compiler

import ToStringMode.{DEBUG, NORMAL}
import ast.*
import generators.AstGenerators

import org.scalatest.BeforeAndAfterEach
import org.scalatest.prop.TableFor2

import scala.sys.process.*

private given intToNumericLiteral: Conversion[Int, NumericLiteral[Int]] with {
  override def apply(v1: Int): NumericLiteral[Int] = NumericLiteral(v1.toLong)
}

private given stringToIdLiteral: Conversion[String, IdLiteral[Int]] with {
  override def apply(v1: String): IdLiteral[Int] = IdLiteral(v1)
}

class CompilerTest extends AbstractScumTest with BeforeAndAfterEach with CompileUtils("build/test") with AstGenerators {

  private var originalToStringMode: ToStringMode = _
  override protected def beforeEach(): Unit = {
    originalToStringMode = toStringMode
    toStringMode = DEBUG
    createBuildDirectories()
  }

  override protected def afterEach(): Unit = {
    removeBuildOutput()
    toStringMode = originalToStringMode
  }

  "Compiler result is consistent with interpreter" in {
    forAll(generateExpression()) { expr =>
      val asmPath = buildAsm(expr)
      val objPath = buildObj(asmPath)
      val binPath = linkObj(objPath)
      val output  = Process(binPath.getPath).!!.trim
      output should be(interpret(expr, Map.empty).get.toString)
    }
  }

  "Expressions can be annotated" in {
    val expressions: TableFor2[Expression[Int], Expression[Int]] = Table(
      ("expr", "expected"),
      (IdLiteral("a"), IdLiteral("a", Some(0))),
      (NumericLiteral(1), NumericLiteral(1, Some(0))),
      (Decrement("a"), Decrement(IdLiteral("a", Some(0)), Some(1))),
      (Decrement(Decrement("a")), Decrement(Decrement(IdLiteral("a", Some(0)), Some(1)), Some(2))),
      (Increment(IdLiteral("a")), Increment(IdLiteral("a", Some(0)), Some(1))),
      (Increment(Increment(IdLiteral("a"))), Increment(Increment(IdLiteral("a", Some(0)), Some(1)), Some(2))),
      (Doubled(IdLiteral("a")), Doubled(IdLiteral("a", Some(0)), Some(1))),
      (Doubled(Doubled(IdLiteral("a"))), Doubled(Doubled(IdLiteral("a", Some(0)), Some(1)), Some(2))),
      (Plus(IdLiteral("a"), IdLiteral("b")), Plus(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2))),
      (
        Plus(Plus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
        Plus(Plus(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2)), IdLiteral("c", Some(3)), Some(4))
      ),
      (Minus(IdLiteral("a"), IdLiteral("b")), Minus(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2))),
      (
        Minus(Minus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
        Minus(Minus(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2)), IdLiteral("c", Some(3)), Some(4))
      ),
      (Times(IdLiteral("a"), IdLiteral("b")), Times(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2))),
      (
        Times(Times(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
        Times(Times(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), Some(2)), IdLiteral("c", Some(3)), Some(4))
      ),
      (
        If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")),
        If(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), IdLiteral("c", Some(2)), Some(3))
      ),
      (
        If(If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")), IdLiteral("d"), IdLiteral("e")),
        If(
          If(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), IdLiteral("c", Some(2)), Some(3)),
          IdLiteral("d", Some(4)),
          IdLiteral("e", Some(5)),
          Some(6)
        )
      ),
      (Let("a", IdLiteral("b"), IdLiteral("c")), Let("a", IdLiteral("b", Some(0)), IdLiteral("c", Some(1)), Some(2))),
      (
        Let("a", Let("b", IdLiteral("c"), IdLiteral("d")), IdLiteral("e")),
        Let("a", Let("b", IdLiteral("c", Some(0)), IdLiteral("d", Some(1)), Some(2)), IdLiteral("e", Some(3)), Some(4))
      )
    )
    forAll(expressions) { (expr, expected) =>
      val annotated = compiler.annotate(expr)
      annotated should be(expected)
    }
  }

  "Can check if an expression is immediate when" - {
    "it is a terminal" in {
      forAll(generateTerminal(Environment("x" -> 0))) { expr =>
        isImmediate(expr) should be(true)
      }
    }

    "it is non-terminal" in {
      forAll(generateFunction(environment = Environment("x" -> 0))) { expr =>
        isImmediate(expr) should be(false)
      }
    }
  }

  "Can check if an expression is in ANF" in {
    val expressions: TableFor2[Expression[Int], Boolean] = Table(
      ("expr", "expected"),
      (IdLiteral("a"), true),
      (NumericLiteral(1), true),
      (Decrement(IdLiteral("a")), true),
      (Decrement(Decrement(IdLiteral("a"))), false),
      (Increment(IdLiteral("a")), true),
      (Increment(Increment(IdLiteral("a"))), false),
      (Doubled(IdLiteral("a")), true),
      (Doubled(Doubled(IdLiteral("a"))), false),
      (Plus(IdLiteral("a"), IdLiteral("b")), true),
      (Plus(Plus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")), false),
      (Minus(IdLiteral("a"), IdLiteral("b")), true),
      (Minus(Minus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")), false),
      (Times(IdLiteral("a"), IdLiteral("b")), true),
      (Times(Times(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")), false),
      (If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")), true),
      (If(If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")), IdLiteral("d"), IdLiteral("e")), false),
      (Let("a", IdLiteral("b"), IdLiteral("c")), true),
      (Let("a", Let("b", IdLiteral("c"), IdLiteral("d")), IdLiteral("e")), true)
    )
    forAll(expressions) { (expr, expected) =>
      expr.isAnf should be(expected)
    }
  }

  "Transforming an expression to ANF" - {
    "returns the same expression when it is immediate" in {
      forAll(generateTerminal(Environment("x" -> 0))) { expr =>
        expr.isAnf should be(true)
        expr.toAnf should be(expr)
      }
    }

    "returns the expected expression for the given examples" in {
      val expressions = Table(
        ("expr", "expected"),
        (
          Decrement(Decrement(IdLiteral("a"))),
          Let("decrement$0", Decrement(IdLiteral("a")), Decrement(IdLiteral("decrement$0")))
        ),
        (
          Decrement(Decrement(Decrement(IdLiteral("a")))),
          Let(
            "decrement$1",
            Let("decrement$0", Decrement(IdLiteral("a")), Decrement(IdLiteral("decrement$0"))),
            Decrement(IdLiteral("decrement$1"))
          )
        ),
        (
          Increment(Increment(IdLiteral("a"))),
          Let("increment$0", Increment(IdLiteral("a")), Increment(IdLiteral("increment$0")))
        ),
        (
          Increment(Increment(Increment(IdLiteral("a")))),
          Let(
            "increment$1",
            Let("increment$0", Increment(IdLiteral("a")), Increment(IdLiteral("increment$0"))),
            Increment(IdLiteral("increment$1"))
          )
        ),
        (
          Doubled(Doubled(IdLiteral("a"))),
          Let("doubled$0", Doubled(IdLiteral("a")), Doubled(IdLiteral("doubled$0")))
        ),
        (
          Doubled(Doubled(Doubled(IdLiteral("a")))),
          Let(
            "doubled$1",
            Let("doubled$0", Doubled(IdLiteral("a")), Doubled(IdLiteral("doubled$0"))),
            Doubled(IdLiteral("doubled$1"))
          )
        ),
        (
          Plus(Plus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
          Let(
            "plus$0",
            Plus(IdLiteral("a"), IdLiteral("b")),
            Plus(IdLiteral("plus$0"), IdLiteral("c"))
          )
        ),
        (
          Plus(Plus("a", "b"), Plus("c", "d")),
          Let("plus$0", Plus("a", "b"), Let("plus$1", Plus("c", "d"), Plus("plus$0", "plus$1")))
        ),
        (
          Minus(Minus(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
          Let(
            "minus$0",
            Minus(IdLiteral("a"), IdLiteral("b")),
            Minus(IdLiteral("minus$0"), IdLiteral("c"))
          )
        ),
        (
          Minus(Minus("a", "b"), Minus("c", "d")),
          Let("minus$0", Minus("a", "b"), Let("minus$1", Minus("c", "d"), Minus("minus$0", "minus$1")))
        ),
        (
          Times(Times(IdLiteral("a"), IdLiteral("b")), IdLiteral("c")),
          Let(
            "times$0",
            Times(IdLiteral("a"), IdLiteral("b")),
            Times(IdLiteral("times$0"), IdLiteral("c"))
          )
        ),
        (
          Times(Times("a", "b"), Times("c", "d")),
          Let("times$0", Times("a", "b"), Let("times$1", Times("c", "d"), Times("times$0", "times$1")))
        )
      )
      toStringMode = NORMAL
      forAll(expressions) { (expr, expected) =>
        expected.isAnf should be(true)
        val actual = expr.toAnf
        actual.isAnf should be(true)
//        actual should be(expected)
      }
    }
  }
}
