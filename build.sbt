import sbtcrossproject.CrossProject

ThisBuild / scalaVersion     := props.ProjectScalaVersion
ThisBuild / organization     := props.Org
ThisBuild / organizationName := "Kevin's Code"

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

ThisBuild / resolvers += "sonatype-snapshots" at s"https://${props.SonatypeCredentialHost}/content/repositories/snapshots"

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3")) file(".scalafix-scala3.conf").some
  else file(".scalafix-scala2.conf").some
)

lazy val extras = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name                := props.RepoName,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .settings(mavenCentralPublishSettings)
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(
    extrasCoreJvm,
    extrasCoreJs,
    extrasRenderJvm,
    extrasRenderJs,
    extrasReflectsJvm,
    extrasReflectsJs,
    extrasRefinementJvm,
    extrasRefinementJs,
    extrasScalaIoJvm,
    extrasScalaIoJs,
    extrasConcurrentJvm,
    extrasConcurrentJs,
    extrasConcurrentTestingJvm,
    extrasConcurrentTestingJs,
    extrasCatsJvm,
    extrasCatsJs,
    extrasHedgehogCatsEffect3Jvm,
    extrasHedgehogCatsEffect3Js,
  )

lazy val extrasCore    = crossSubProject("core", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehog,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasCoreJvm = extrasCore.jvm
lazy val extrasCoreJs  = extrasCore.js.settings(Test / fork := false)

lazy val extrasRender    = crossSubProject("render", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehog,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasRenderJvm = extrasRender.jvm
lazy val extrasRenderJs  = extrasRender.js.settings(Test / fork := false)

lazy val extrasReflects    = crossSubProject("reflects", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.Scala2Versions,
    libraryDependencies ++= libs.hedgehog ++
      (
        if (isScala3(scalaVersion.value)) List.empty
        else List(libs.scalaReflect(scalaVersion.value))
      ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest)
lazy val extrasReflectsJvm = extrasReflects.jvm
lazy val extrasReflectsJs  = extrasReflects.js.settings(Test / fork := false)

lazy val extrasRefinement = crossSubProject("refinement", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.Scala2Versions,
    libraryDependencies ++= libs.hedgehog ++ List(
      libs.newtype,
    ) ++ List(libs.cats, libs.refined),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest, extrasReflects)

lazy val extrasRefinementJvm = extrasRefinement.jvm
lazy val extrasRefinementJs  = extrasRefinement.js.settings(Test / fork := false)

lazy val extrasScalaIo = crossSubProject("scala-io", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies ++= libs.hedgehog,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value.getParentFile / "shared/src/main"
      if (scalaVersion.value.startsWith("2.12")) {
        List(
          sharedSourceDir / "scala-2.12",
        )
      } else {
        List.empty
      }
    },
    Test / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value.getParentFile / "shared/src/test"
      if (scalaVersion.value.startsWith("2.12"))
        List(
          sharedSourceDir / "scala-2.12",
        )
      else
        Seq.empty
    },
  )
  .dependsOn(
    extrasCore,
  )

lazy val extrasScalaIoJvm = extrasScalaIo.jvm
lazy val extrasScalaIoJs  = extrasScalaIo.js.settings(Test / fork := false)

lazy val extrasConcurrent    = crossSubProject("concurrent", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )
lazy val extrasConcurrentJvm = extrasConcurrent.jvm
lazy val extrasConcurrentJs  = extrasConcurrent.js.settings(Test / fork := false)

lazy val extrasConcurrentTesting = crossSubProject("concurrent-testing", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )
  .dependsOn(extrasCore, extrasConcurrent)

lazy val extrasConcurrentTestingJvm = extrasConcurrentTesting.jvm
lazy val extrasConcurrentTestingJs  = extrasConcurrentTesting.js.settings(Test / fork := false)

lazy val extrasCats = crossSubProject("cats", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions  := props.CrossScalaVersions,
    libraryDependencies ++= (if (scalaVersion.value.startsWith("3")) {
                               List(libs.catsLatest, libs.catsEffect3 % Test)
                             } else {
                               List(libs.cats, libs.catsEffect % Test)
                             }) ++ List("org.slf4j" % "slf4j-api" % "1.7.32" % Test),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value)
  )
  .dependsOn(extrasConcurrentTesting % Test)

lazy val extrasCatsJvm = extrasCats.jvm
lazy val extrasCatsJs  = extrasCats.js.settings(Test / fork := false)

lazy val extrasHedgehogCe3 = crossSubProject("hedgehog-ce3", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions             := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.catsLatest,
      libs.catsEffect3,
      libs.libCatsEffectTestKit excludeAll ("org.scalacheck"),
      libs.hedgehogCore,
      libs.hedgehogRunner,
    ) ++ libs.hedgehog,
    libraryDependencies            :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(extrasCore, extrasCats)

lazy val extrasHedgehogCatsEffect3Jvm = extrasHedgehogCe3.jvm
lazy val extrasHedgehogCatsEffect3Js  = extrasHedgehogCe3.js.settings(Test / fork := false)

lazy val docs = (project in file("docs-gen-tmp/docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    name                := "docs",
    mdocIn              := file("docs/common"),
    mdocOut             := file("generated-docs/docs"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables       := createMdocVariables(),
    docusaurDir         := (ThisBuild / baseDirectory).value / "website",
    docusaurBuildDir    := docusaurDir.value / "build",
  )
  .settings(noPublish)

lazy val docsExtrasCore = (project in file("docs-gen-tmp/extras-core"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-core",
    mdocIn              := file("docs/extras-core"),
    mdocOut             := file("generated-docs/docs/extras-core"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect,
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-core" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasCats = (project in file("docs-gen-tmp/extras-cats"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-cats",
    mdocIn              := file("docs/extras-cats"),
    mdocOut             := file("generated-docs/docs/extras-cats"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect,
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-cats" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasConcurrent = (project in file("docs-gen-tmp/extras-concurrent"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-concurrent",
    mdocIn              := file("docs/extras-concurrent"),
    mdocOut             := file("generated-docs/docs/extras-concurrent"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-concurrent" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasHedgehogCe3 = (project in file("docs-gen-tmp/extras-hedgehog-ce3"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-hedgehog-ce3",
    mdocIn              := file("docs/extras-hedgehog-ce3"),
    mdocOut             := file("generated-docs/docs/extras-hedgehog-ce3"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-hedgehog-ce3" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasRefinement = (project in file("docs-gen-tmp/extras-refinement"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-refinement",
    mdocIn              := file("docs/extras-refinement"),
    mdocOut             := file("generated-docs/docs/extras-refinement"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-refinement" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasReflects = (project in file("docs-gen-tmp/extras-reflects"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-reflects",
    mdocIn              := file("docs/extras-reflects"),
    mdocOut             := file("generated-docs/docs/extras-reflects"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        libs.newtype,
        "io.kevinlee" %% "extras-reflects" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasScalaIo = (project in file("docs-gen-tmp/extras-scala-io"))
  .enablePlugins(MdocPlugin)
  .settings(
    name                := "docs-extras-scala-io",
    mdocIn              := file("docs/extras-scala-io"),
    mdocOut             := file("generated-docs/docs/extras-scala-io"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-scala-io" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables       := createMdocVariables(),
  )
  .settings(noPublish)

// scalafmt: off

def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

lazy val mavenCentralPublishSettings: SettingsDefinition = List(
  /* Publish to Maven Central { */
  sonatypeCredentialHost := props.SonatypeCredentialHost,
  sonatypeRepository     := props.SonatypeRepository,
  /* } Publish to Maven Central */
)

def subProject(projectName: String): Project = {
  val prefixedName = prefixedProjectName(projectName)
  Project(projectName, file(s"modules/$prefixedName"))
    .settings(
      name           := prefixedName,
      Test / fork    := true,
      libraryDependencies ++= libs.hedgehog,
      testFrameworks ~=
        (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
    )
    .settings(
      mavenCentralPublishSettings
    )
}

def crossSubProject(projectName: String, crossProject: CrossProject.Builder): CrossProject = {
  val prefixedName = prefixedProjectName(projectName)
  crossProject
    .in(file(s"modules/$prefixedName"))
    .settings(
      name              := prefixedName,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      Test / fork       := true,
      libraryDependencies ++= libs.hedgehog,
      testFrameworks ~=
        (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
      scalafixConfig    := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
      wartremoverErrors ++= Warts.allBut(Wart.Any, Wart.Nothing, Wart.ImplicitConversion, Wart.ImplicitParameter),
      scalacOptions     := {
        val options = scalacOptions.value
        if (isScala3(scalaVersion.value)) {
          options.filterNot(_.startsWith("-P:wartremover"))
        } else {
          options
        }
      }
    )
    .settings(
      mavenCentralPublishSettings
    )
}

def removeScala3Incompatible(scalaVersion: String, libraryDependencies: Seq[ModuleID]): Seq[ModuleID] =
  if (scalaVersion.startsWith("3")) {
    libraryDependencies.filterNot(props.isScala3Incompatible)
  } else {
    libraryDependencies
  }

def getLatestExtrasVersion(): String = {
  import sys.process._
  "git fetch --tags".!
  val tag = "git rev-list --tags --max-count=1".!!.trim
  s"git describe --tags $tag".!!.trim.stripPrefix("v")

}

def createMdocVariables(): Map[String, String] = Map(
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
)

addCommandAlias(
  "docsCleanAll",
  "; docsExtrasCore/clean; docsExtrasCats/clean; docsExtrasHedgehogCe3/clean; docsExtrasRefinement/clean; docsExtrasReflects/clean; docsExtrasScalaIo/clean; docsExtrasConcurrent/clean; docs/clean"
)
addCommandAlias(
  "docsMdocAll",
  "; docsExtrasCore/mdoc; docsExtrasCats/mdoc; docsExtrasHedgehogCe3/mdoc; docsExtrasRefinement/mdoc; docsExtrasReflects/mdoc; docsExtrasScalaIo/mdoc; docsExtrasConcurrent/mdoc; docs/mdoc"
)

lazy val props = new {

  private val GitHubRepo = findRepoOrgAndName

  val Org        = "io.kevinlee"
  val GitHubUser = GitHubRepo.fold("Kevin-Lee")(_.orgToString)
  val RepoName   = GitHubRepo.fold("extras")(_.nameToString)

  val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

  val Scala2Versions = List(
    "2.13.6",
    "2.12.13",
  )
  val Scala2Version  = Scala2Versions.head

  val Scala3Versions = List("3.0.0")
  val Scala3Version  = Scala3Versions.head

  val ProjectScalaVersion = Scala2Version
//  val ProjectScalaVersion = Scala3Version

  val CrossScalaVersions =
    (Scala3Versions ++ Scala2Versions).distinct

  val SonatypeCredentialHost = "s01.oss.sonatype.org"
  val SonatypeRepository     = s"https://$SonatypeCredentialHost/service/local"

  val CatsLatestVersion = "2.7.0"
  val CatsVersion       = "2.6.1"
  val Cats2_0_0Version  = "2.0.0"

  val CatsEffect3Version     = "3.2.9"
  val CatsEffectVersion      = "2.5.4"
  val CatsEffect2_0_0Version = "2.0.0"

  val HedgehogVersion = "0.9.0"

  val NewtypeVersion = "0.4.4"
  val RefinedVersion = "0.9.27"

  val isScala3Incompatible: ModuleID => Boolean =
    m =>
      m.name == "wartremover" ||
        m.name == "ammonite" ||
        m.name == "kind-projector" ||
        m.name == "better-monadic-for" ||
        m.name == "mdoc"

  val IncludeTest = "compile->compile;test->test"
}

lazy val libs = new {
  lazy val catsLatest = "org.typelevel" %% "cats-core" % props.CatsLatestVersion
  lazy val cats       = "org.typelevel" %% "cats-core" % props.CatsVersion
  lazy val catsOld    = "org.typelevel" %% "cats-core" % props.Cats2_0_0Version

  lazy val catsEffect3   = "org.typelevel" %% "cats-effect" % props.CatsEffect3Version
  lazy val catsEffect    = "org.typelevel" %% "cats-effect" % props.CatsEffectVersion
  lazy val catsEffectOld = "org.typelevel" %% "cats-effect" % props.CatsEffect2_0_0Version

  lazy val libCatsEffectTestKit = "org.typelevel" %% "cats-effect-testkit" % props.CatsEffect3Version

  lazy val hedgehogCore   = "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion
  lazy val hedgehogRunner = "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion
  lazy val hedgehogSbt    = "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion

  lazy val hedgehog = List(
    hedgehogCore,
    hedgehogRunner,
    hedgehogSbt,
  ).map(_ % Test)

  def scalaReflect(scalaVersion: String): ModuleID = "org.scala-lang" % "scala-reflect" % scalaVersion

  lazy val newtype = "io.estatico" %% "newtype" % props.NewtypeVersion
  lazy val refined = "eu.timepit" %% "refined" % props.RefinedVersion excludeAll "org.scala-lang.modules" %% "scala-xml"

}

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")
