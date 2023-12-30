ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "scum",
    idePackagePrefix := Some("cl.ravenhill.scum")
  )
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-17" % "3.2.17.0" % "test"
