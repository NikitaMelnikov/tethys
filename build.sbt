val commonDependencies = Seq(
  organization := "tethys",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val core = project.in(file("./modules/core"))
  .settings(commonDependencies)
  .settings(
  name := "tethys-core",
  libraryDependencies ++= Seq(
    "com.fasterxml.jackson.core" % "jackson-core" % "2.9.1"
  )
)

lazy val `macro-derivation` = project.in(file("./modules/macro-derivation"))
  .settings(commonDependencies)
  .settings(
    name := "tethys-macro-derivation",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
    )
  ).dependsOn(core)