
val sbtVersion = "0.13.9"

val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7",
  resolvers ++= Seq(
    "typesafe-releases-extra" at "https://repo.typesafe.com/typesafe/releases"
  )
)

lazy val jsext = project
  .settings(commonSettings)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % Provided
    )
  )

lazy val root = (project in file("."))
  .aggregate(jsext)
