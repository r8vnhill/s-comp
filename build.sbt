ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "s-comp",
    idePackagePrefix := Some("cl.ravenhill.scomp")
  )
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
