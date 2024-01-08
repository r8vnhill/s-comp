package cl.ravenhill.scum

import generators.AstGenerators

import org.scalatest.BeforeAndAfterEach

import scala.sys.process.*

class CompileTest extends AbstractScumTest with BeforeAndAfterEach with CompileUtils("build/test") with AstGenerators {
  
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
      val output = Process(binPath.getPath).!!.trim
      output should be(interpret(expr, Map.empty).get.toString)
    }
  }
}
