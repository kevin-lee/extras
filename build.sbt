ThisBuild / scalaVersion       := props.ProjectScalaVersion
ThisBuild / organization       := props.Org
ThisBuild / organizationName   := "Kevin's Code"
ThisBuild / crossScalaVersions := props.CrossScalaVersions

ThisBuild / testFrameworks ~=
  (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct)

ThisBuild / developers := List(
  Developer(
    props.GitHubUser,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUser}"),
  )
)

ThisBuild / homepage   := Some(url(s"https://github.com/${props.GitHubUser}/${props.RepoName}"))
ThisBuild / scmInfo    :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUser}/${props.RepoName}"),
      s"git@github.com:${props.GitHubUser}/${props.RepoName}.git",
    )
  )

ThisBuild / licenses   := props.licenses

lazy val extras = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name                := props.RepoName,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(extrasScalaIo, extrasConcurrent, extrasConcurrentTesting, extrasCats)

lazy val extrasScalaIo = subProject("scala-io")
  .settings(
    libraryDependencies ++= libs.hedgehog,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value / "src/main"
      if (scalaVersion.value.startsWith("2.11") || scalaVersion.value.startsWith("2.12"))
        List(
          sharedSourceDir / "scala-2.11_2.12",
        )
      else
        Seq.empty
    },
    Test / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value / "src/test"
      if (scalaVersion.value.startsWith("2.11") || scalaVersion.value.startsWith("2.12"))
        List(
          sharedSourceDir / "scala-2.11_2.12",
        )
      else
        Seq.empty
    },
  )

lazy val extrasConcurrent = subProject("concurrent")
  .settings(
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )

lazy val extrasConcurrentTesting = subProject("concurrent-testing")
  .settings(
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )
  .dependsOn(extrasConcurrent)

lazy val extrasCats = subProject("cats")
  .settings(
    libraryDependencies ++= (if (scalaVersion.value.startsWith("3")) {
                               List(libs.catsLatest, libs.catsEffectLatest % Test)
                             } else if (scalaVersion.value.startsWith("2.11")) {
                               List(libs.catsOld, libs.catsEffectOld % Test)
                             } else {
                               List(libs.cats, libs.catsEffect % Test)
                             }) ++ List("org.slf4j" % "slf4j-api" % "1.7.32"),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )
  .dependsOn(extrasConcurrentTesting % Test)

lazy val docs       = (project in file("generated-docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    name                := prefixedProjectName("docs"),
    mdocIn              := file("docs"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables       := Map(
      "VERSION"                  -> {
        import sys.process._
        "git fetch --tags".!
        val tag = "git rev-list --tags --max-count=1".!!.trim
        s"git describe --tags $tag".!!.trim.stripPrefix("v")
      },
      "SUPPORTED_SCALA_VERSIONS" -> {
        val versions = props
          .CrossScalaVersions
          .map(CrossVersion.binaryScalaVersion)
          .map(binVer => s"`$binVer`")
        if (versions.length > 1)
          s"${versions.init.mkString(", ")} and ${versions.last}"
        else
          versions.mkString
      },
    ),
    docusaurDir         := (ThisBuild / baseDirectory).value / "website",
    docusaurBuildDir    := docusaurDir.value / "build",
  )
  .settings(noPublish)
  .dependsOn(extrasConcurrent, extrasConcurrentTesting, extrasCats)

// scalafmt: off

def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

def subProject(projectName: String): Project = {
  val prefixedName = prefixedProjectName(projectName)
  Project(projectName, file(prefixedName))
    .settings(
      name := prefixedName,
      libraryDependencies ++= libs.hedgehog,
      testFrameworks ~=
        (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
    )
}

def removeScala3Incompatible(scalaVersion: String, libraryDependencies: Seq[ModuleID]): Seq[ModuleID] =
  if (scalaVersion.startsWith("3")) {
    libraryDependencies.filterNot(props.isScala3Incompatible)
  } else {
    libraryDependencies
  }

lazy val props = new {

  private val GitHubRepo = findRepoOrgAndName

  final val Org        = "io.kevinlee"
  final val GitHubUser = GitHubRepo.fold("Kevin-Lee")(_.orgToString)
  final val RepoName   = GitHubRepo.fold("extras")(_.nameToString)

  final val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

  final val Scala2Versions = List(
    "2.13.5",
    "2.12.13",
    "2.11.12",
  )
  final val Scala2Version  = Scala2Versions.head

  final val Scala3Versions = List("3.0.0")
  final val Scala3Version  = Scala3Versions.head

  final val ProjectScalaVersion = Scala2Version
//  final val ProjectScalaVersion = Scala3Version

  final val CrossScalaVersions =
    (Scala3Versions ++ Scala2Versions).distinct

  final val CatsLatestVersion = "2.6.1"
  final val CatsVersion       = "2.3.1"
  final val Cats2_0_0Version  = "2.0.0"

  final val CatsEffectLatestVersion = "3.2.2"
  final val CatsEffectVersion       = "2.3.1"
  final val CatsEffect2_0_0Version  = "2.0.0"

  final val HedgehogVersion = "0.7.0"

  val isScala3Incompatible: ModuleID => Boolean =
    m =>
      m.name == "wartremover" ||
        m.name == "ammonite" ||
        m.name == "kind-projector" ||
        m.name == "better-monadic-for" ||
        m.name == "mdoc"

}

lazy val libs = new {
  lazy val catsLatest = "org.typelevel" %% "cats-core" % props.CatsLatestVersion
  lazy val cats       = "org.typelevel" %% "cats-core" % props.CatsVersion
  lazy val catsOld    = "org.typelevel" %% "cats-core" % props.Cats2_0_0Version

  lazy val catsEffectLatest = "org.typelevel" %% "cats-effect" % props.CatsEffectLatestVersion
  lazy val catsEffect       = "org.typelevel" %% "cats-effect" % props.CatsEffectVersion
  lazy val catsEffectOld    = "org.typelevel" %% "cats-effect" % props.CatsEffect2_0_0Version

  lazy val hedgehog = List(
    "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion,
    "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion,
    "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion,
  ).map(_ % Test)

}
