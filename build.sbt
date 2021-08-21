ThisBuild / scalaVersion       := props.ProjectScalaVersion
ThisBuild / organization       := props.Org
ThisBuild / crossScalaVersions := props.CrossScalaVersions

lazy val catsExtra = (project in file("."))
  .settings(
    name := props.RepoName
  )
  .aggregate(core)

lazy val core = subProject("core", "core", file("core"))

// scalafmt: off
def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

def subProject(id: String, projectName: String, file: File): Project =
  Project(id, file)
    .settings(
      name := prefixedProjectName(projectName),
      libraryDependencies ++= (if (scalaVersion.value.startsWith("3")) {
                                 List(libs.catsLatest)
                               } else if (scalaVersion.value.startsWith("2.11")) {
                                 List(libs.catsOld)
                               } else {
                                 List(libs.cats)
                               }) ++ libs.hedgehog
    )

lazy val props = new {

  final val Org        = "io.kevinlee"
  final val GitHubUser = "Kevin-Lee"
  final val RepoName   = "cats-extra"

  final val Scala2Versions = List(
    "2.13.5",
    "2.12.13",
  )
  final val Scala2Version  = Scala2Versions.head

  final val Scala3Versions = List("3.0.0")
  final val Scala3Version  = Scala3Versions.head

  final val ProjectScalaVersion = Scala2Version

  final val CrossScalaVersions =
    (Scala3Versions ++ Scala2Versions).distinct

  final val CatsLatestVersion = "2.6.1"
  final val CatsVersion       = "2.3.1"
  final val Cats2_0_0Version  = "2.0.0"

  final val HedgehogVersion = "0.7.0"
}

lazy val libs = new {
  lazy val catsLatest = "org.typelevel" %% "cats-core" % props.CatsLatestVersion
  lazy val cats       = "org.typelevel" %% "cats-core" % props.CatsVersion
  lazy val catsOld    = "org.typelevel" %% "cats-core" % props.Cats2_0_0Version

  lazy val hedgehog = List(
    "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion,
    "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion,
    "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion,
  ).map(_ % Test)

}
