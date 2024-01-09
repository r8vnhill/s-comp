package cl.ravenhill.scum

import generators.AstGenerators

import cl.ravenhill.scum.ToStringMode.DEBUG
import cl.ravenhill.scum.ast.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.prop.TableFor2

import scala.sys.process.*

class CompilerTest extends AbstractScumTest with BeforeAndAfterEach with CompileUtils("build/test") with AstGenerators {

  override protected def beforeEach(): Unit = {
    createBuildDirectories()
  }

  override protected def afterEach(): Unit = {
    removeBuildOutput()
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
    toStringMode = DEBUG
    val expressions: TableFor2[Expression[Int], Expression[Int]] = Table(
      ("expr", "expected"),
      (IdLiteral("a"), IdLiteral("a", Some(0))),
      (NumericLiteral(1), NumericLiteral(1, Some(0))),
      (Decrement(IdLiteral("a")), Decrement(IdLiteral("a", Some(0)), Some(1))),
      (Decrement(Decrement(IdLiteral("a"))), Decrement(Decrement(IdLiteral("a", Some(0)), Some(1)), Some(2))),
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
      (If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")), If(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), IdLiteral("c", Some(2)), Some(3))),
      (
        If(If(IdLiteral("a"), IdLiteral("b"), IdLiteral("c")), IdLiteral("d"), IdLiteral("e")),
        If(If(IdLiteral("a", Some(0)), IdLiteral("b", Some(1)), IdLiteral("c", Some(2)), Some(3)), IdLiteral("d", Some(4)), IdLiteral("e", Some(5)), Some(6))
      ),
      (Let("a", IdLiteral("b"), IdLiteral("c")), Let("a", IdLiteral("b", Some(0)), IdLiteral("c", Some(1)), Some(2))),
      (
        Let("a", Let("b", IdLiteral("c"), IdLiteral("d")), IdLiteral("e")),
        Let("a", Let("b", IdLiteral("c", Some(0)), IdLiteral("d", Some(1)), Some(2)), IdLiteral("e", Some(3)), Some(4))
      ),
    )
    forAll(expressions) { (expr, expected) =>
      val annotated = compiler.annotate(expr)
      annotated should be(expected)
    }
  }
}
