val githubRepo = "jsext"

val repo = "http://localhost:38084"
val snapshots = "snapshots" at s"$repo/snapshots"
val releases = "releases" at s"$repo/releases"

val commonSettings = Seq(
  organization := "com.github.maprohu",
  version := "0.1.0-SNAPSHOT",

  scalaVersion := "2.11.7",
  publishMavenStyle := true,
  publishArtifact in Test := false,
  publishArtifact in (Compile, packageDoc) := false,
  publishTo := {
    if (isSnapshot.value)
      Some(snapshots)
    else
      Some(releases)
  },
  pomIncludeRepository := { _ => false },
  licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php")),
  homepage := Some(url(s"https://github.com/maprohu/${githubRepo}")),
  pomExtra := (
    <scm>
      <url>git@github.com:maprohu/{githubRepo}.git</url>
      <connection>scm:git:git@github.com:maprohu/{githubRepo}.git</connection>
    </scm>
      <developers>
        <developer>
          <id>maprohu</id>
          <name>maprohu</name>
          <url>https://github.com/maprohu</url>
        </developer>
      </developers>
    )
)

val noPublish = Seq(
  publishArtifact := false,
  publishTo := Some(Resolver.file("Unused transient repository", file("target/unusedrepo")))
)

lazy val jsext = project
  .settings(commonSettings)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "jsext",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % Provided
    )
  )

lazy val root = (project in file("."))
  .settings(noPublish)
  .aggregate(jsext)
