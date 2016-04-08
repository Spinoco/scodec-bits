import com.typesafe.tools.mima.core._
import com.typesafe.tools.mima.plugin.MimaKeys._

lazy val commonSettings = Seq(
  scodecModule := "scodec-bits",
  rootPackage := "scodec.bits",
  contributors ++= Seq(Contributor("mpilquist", "Michael Pilquist"), Contributor("pchiusano", "Paul Chiusano")),
  scalaVersion := "2.12.0-M4",
  crossScalaVersions := List("2.12.0-M4")
)

lazy val root = project.in(file(".")).aggregate(coreJVM).settings(commonSettings: _*).settings(
  publishArtifact := false
)

lazy val core = crossProject.in(file("core")).
  enablePlugins(BuildInfoPlugin).
  settings(commonSettings: _*).
  settings(scodecPrimaryModule: _*).
  jvmSettings(scodecPrimaryModuleJvm: _*).
  settings(
    scodecModule := "scodec-bits",
    rootPackage := "scodec.bits",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
      "org.scalatest" %%% "scalatest" % "3.0.0-M16-SNAP3" % "test",
      "org.scalacheck" %%% "scalacheck" % "1.12.5" % "test")
  ).
  jvmSettings(
    docSourcePath := new File(baseDirectory.value, "../.."),
    libraryDependencies ++= Seq(
      "com.google.guava" % "guava" % "16.0.1" % "test",
      "com.google.code.findbugs" % "jsr305" % "2.0.3" % "test" // required for guava
    ),
    OsgiKeys.privatePackage := Nil,
    OsgiKeys.exportPackage := Seq("scodec.bits.*;version=${Bundle-Version}"),
    OsgiKeys.importPackage := Seq(
      """scala.*;version="$<range;[==,=+)>"""",
      "*"
    ),
    binaryIssueFilters ++= Seq(
    ).map { method => ProblemFilters.exclude[MissingMethodProblem](method) },
    binaryIssueFilters ++= Seq(
    )
)

lazy val coreJVM = core.jvm

