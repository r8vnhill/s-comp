package cl.ravenhill.scum

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Path, Paths}
import java.util.UUID
import scala.sys.process.*

trait CompileUtils(protected val buildDir: String) {

  protected def createBuildDirectories(): Unit = {
    val buildPath = Paths.get(buildDir)
    if (!Files.exists(buildPath)) {
      Files.createDirectories(buildPath)
    }
  }

  protected def buildAsm(program: ast.Expression[Int], id: String = UUID.randomUUID().toString): File = {
    val compiled = compiler.compileProgram(program)
    // write to file
    val asmPath = Paths.get(buildDir, s"$id.s")
    val file    = File(asmPath.toString)
    val writer  = PrintWriter(file)
    try {
      writer.write(compiled)
    } finally {
      writer.close()
    }
    // return path
    file
  }

  protected def buildObj(asm: File): File = {
    // Check operating system
    val os        = System.getProperty("os.name").toLowerCase()
    val isWindows = os.contains("win")
    val isMac     = os.contains("mac")
    val file      = File(asm.toString.replace(".s", ".o"))
    // Build command
    val command = Seq(
      "nasm",
      "-f",
      if (isWindows) "win64" else if (isMac) "macho64" else "elf64",
      "-g",
      "-o",
      file.toString,
      asm.toString
    )
    // Run command
    command.!!
    // Wait for file to be created
    while (!Files.exists(Paths.get(s"${asm.toString.replace(".s", ".o")}"))) {
      Thread.sleep(100)
    }
    // Return path
    file
  }

  protected def linkObj(objPath: File): File = {
    // Check operating system
    val os        = System.getProperty("os.name").toLowerCase()
    val isWindows = os.contains("win")
    val file = if (isWindows) {
      File(objPath.toString.replace(".o", ".exe"))
    } else {
      File(objPath.toString.replace(".o", ""))
    }
    // Build command
    val command = Seq(
      "clang",
      "-o",
      file.toString,
      "src/main/c/main.c",
      objPath.toString
    )
    // Run command
    command.!!
    // Wait for file to be created
    while (!Files.exists(if (isWindows) Paths.get(s"${objPath.toString.replace(".o", ".exe")}") else Paths.get(s"${objPath.toString.replace(".o", "")}"))) {
      Thread.sleep(100)
    }
    // Return path
    file
  }

  protected def removeBuildOutput(): Unit = {
    val buildPath = Paths.get(buildDir)
    deleteDirectory(buildPath)
  }

  private def deleteDirectory(path: Path): Unit = {
    if (Files.isDirectory(path)) {
      // If it's a directory, delete all its contents first
      Files.list(path).forEach(deleteDirectory)
    }
    // Delete the file or the now-empty directory
    Files.deleteIfExists(path)
  }
}
