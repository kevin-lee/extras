import sbtcrossproject.CrossProject

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / organization := props.Org
ThisBuild / organizationName := "Kevin's Code"

ThisBuild / developers := List(
  Developer(
    props.GitHubUser,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUser}"),
  )
)

ThisBuild / homepage := Some(url(s"https://github.com/${props.GitHubUser}/${props.RepoName}"))
ThisBuild / scmInfo :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUser}/${props.RepoName}"),
      s"git@github.com:${props.GitHubUser}/${props.RepoName}.git",
    )
  )

ThisBuild / licenses := props.licenses

ThisBuild / resolvers += "sonatype-snapshots".at(
  s"https://${props.SonatypeCredentialHost}/content/repositories/snapshots"
)

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3")) file(".scalafix-scala3.conf").some
  else file(".scalafix-scala2.conf").some
)

ThisBuild / scalafixScalaBinaryVersion := {
  val log        = sLog.value
  val newVersion = if (scalaVersion.value.startsWith("3")) {
    (ThisBuild / scalafixScalaBinaryVersion).value
  } else {
    CrossVersion.binaryScalaVersion(scalaVersion.value)
  }

  log.info(
    s">> Change ThisBuild / scalafixScalaBinaryVersion from ${(ThisBuild / scalafixScalaBinaryVersion).value} to $newVersion"
  )
  newVersion
}

ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % "0.3.0"

lazy val extras = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name := props.RepoName,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .settings(mavenCentralPublishSettings)
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(
    extrasCatsJvm,
    extrasCatsJs,
    extrasCirceJvm,
    extrasCirceJs,
    extrasConcurrentJvm,
    extrasConcurrentJs,
    extrasConcurrentTestingJvm,
    extrasConcurrentTestingJs,
    extrasCoreJvm,
    extrasCoreJs,
    extrasDoobieNewtypeCe2Jvm,
    extrasDoobieNewtypeCe3Jvm,
    extrasDoobieToolsCe2Jvm,
    extrasDoobieToolsCe3Jvm,
    extrasFs2V2TextJvm,
    extrasFs2V2TextJs,
    extrasFs2V3TextJvm,
    extrasFs2V3TextJs,
    extrasHedgehogCirceJvm,
    extrasHedgehogCirceJs,
    extrasHedgehogCatsEffect3Jvm,
    extrasHedgehogCatsEffect3Js,
    extrasRenderJvm,
    extrasRenderJs,
    extrasRenderRefinedJvm,
    extrasRenderRefinedJs,
    extrasReflectsJvm,
    extrasReflectsJs,
    extrasRefinementJvm,
    extrasRefinementJs,
    extrasScalaIoJvm,
    extrasScalaIoJs,
    extrasStringJvm,
    extrasStringJs,
    extrasTestingToolsJvm,
    extrasTestingToolsJs,
    extrasTestingToolsCatsJvm,
    extrasTestingToolsCatsJs,
    extrasTestingToolsEffectieJvm,
    extrasTestingToolsEffectieJs,
    extrasTypeInfoJvm,
    extrasTypeInfoJs,
  )

lazy val extrasCore    = crossSubProject("core", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasCoreJvm = extrasCore.jvm
lazy val extrasCoreJs  = extrasCore.js.settings(Test / fork := false)

lazy val extrasString    = crossSubProject("string", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies += libs.cats % Test,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasStringJvm = extrasString.jvm
lazy val extrasStringJs  = extrasString.js.settings(Test / fork := false)

lazy val extrasCirce    = crossSubProject("circe", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (if (isScala3(scalaVersion.value))
                               List(
                                 libs.circeCore_0_14_3,
                                 libs.circeParser_0_14_3  % Test,
                                 libs.circeGeneric_0_14_3 % Test,
                                 libs.circeLiteral_0_14_3 % Test,
                               )
                             else
                               List(
                                 libs.circeCore,
                                 libs.circeParser         % Test,
                                 libs.circeGeneric        % Test,
                                 libs.circeLiteral        % Test,
                               )),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasCirceJvm = extrasCirce.jvm
lazy val extrasCirceJs  = extrasCirce.js.settings(Test / fork := false)

lazy val extrasDoobieNewtypeCe2    = crossSubProject("doobie-newtype-ce2", crossProject(JVMPlatform))
  .settings(
    scalacOptions := {
      val scalaV                = scalaVersion.value
      val existingScalacOptions = scalacOptions.value
      if (isScala3(scalaV))
        existingScalacOptions
      else if (scalaV.startsWith("2.12"))
        existingScalacOptions.filterNot(_ == "-Ywarn-unused:nowarn") ++ List("-Xsource:3")
      else
        existingScalacOptions ++ List("-Xsource:3")
    },
    crossScalaVersions := props.Scala2Versions.distinct,
    libraryDependencies ++=
      List(
        libs.doobieCe2Core
      ) ++
        (if (isScala3(scalaVersion.value))
           List.empty
         else
           List(libs.newtype)),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(
    extrasDoobieToolsCe2 % "test->test"
  )
lazy val extrasDoobieNewtypeCe2Jvm = extrasDoobieNewtypeCe2.jvm

lazy val extrasDoobieNewtypeCe3    = crossSubProject("doobie-newtype-ce3", crossProject(JVMPlatform))
  .settings(
    scalacOptions := {
      val scalaV                = scalaVersion.value
      val existingScalacOptions = scalacOptions.value
      if (isScala3(scalaV))
        existingScalacOptions
      else if (scalaV.startsWith("2.12"))
        existingScalacOptions.filterNot(_ == "-Ywarn-unused:nowarn") ++ List("-Xsource:3")
      else
        existingScalacOptions ++ List("-Xsource:3")
    },
    crossScalaVersions := props.Scala2Versions.distinct,
    libraryDependencies ++=
      List(
        libs.doobieCe3Core
      ) ++
        (if (isScala3(scalaVersion.value))
           List.empty
         else
           List(libs.newtype)),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(
    extrasDoobieToolsCe3 % "test->test"
  )
lazy val extrasDoobieNewtypeCe3Jvm = extrasDoobieNewtypeCe3.jvm

lazy val extrasDoobieToolsCe2    = crossSubProject("doobie-tools-ce2", crossProject(JVMPlatform))
  .settings(
    scalacOptions := {
      val scalaV                = scalaVersion.value
      val existingScalacOptions = scalacOptions.value
      if (isScala3(scalaV))
        existingScalacOptions
      else if (scalaV.startsWith("2.12"))
        existingScalacOptions.filterNot(_ == "-Ywarn-unused:nowarn") ++ List("-Xsource:3")
      else
        existingScalacOptions ++ List("-Xsource:3")
    },
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++=
      List(
        libs.doobieCe2Core,
        libs.embeddedPostgres % Test,
        libs.effectieCe2      % Test,
      ) ++
        (if (isScala3(scalaVersion.value))
           List.empty
         else
           List(
             libs.newtype          % Test,
             libs.doobieCe2Refined % Test,
           )),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore)
lazy val extrasDoobieToolsCe2Jvm = extrasDoobieToolsCe2.jvm

lazy val extrasDoobieToolsCe3    = crossSubProject("doobie-tools-ce3", crossProject(JVMPlatform))
  .settings(
    scalacOptions := {
      val scalaV                = scalaVersion.value
      val existingScalacOptions = scalacOptions.value
      if (isScala3(scalaV))
        existingScalacOptions
      else if (scalaV.startsWith("2.12"))
        existingScalacOptions.filterNot(_ == "-Ywarn-unused:nowarn") ++ List("-Xsource:3")
      else
        existingScalacOptions ++ List("-Xsource:3")
    },
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++=
      List(
        libs.doobieCe3Core,
        libs.embeddedPostgres % Test,
        libs.effectieCe3      % Test,
      ) ++
        (if (isScala3(scalaVersion.value))
           List.empty
         else
           List(libs.newtype)),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(
    extrasCore,
    extrasHedgehogCe3,
  )
lazy val extrasDoobieToolsCe3Jvm = extrasDoobieToolsCe3.jvm

lazy val extrasFs2V2Text    = crossSubProject("fs2-v2-text", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.fs2V2,
      libs.http4sServerDsl_0_22 % Test,
    ),
  )
lazy val extrasFs2V2TextJvm = extrasFs2V2Text.jvm
lazy val extrasFs2V2TextJs  = extrasFs2V2Text.js.settings(Test / fork := false)

lazy val extrasFs2V3Text    = crossSubProject("fs2-v3-text", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.fs2V3,
      libs.http4sServer_0_23    % Test,
      libs.http4sServerDsl_0_23 % Test,
    ),
  )
  .dependsOn(extrasHedgehogCe3 % Test)
lazy val extrasFs2V3TextJvm = extrasFs2V3Text.jvm
lazy val extrasFs2V3TextJs  = extrasFs2V3Text.js.settings(Test / fork := false)

lazy val extrasRender    = crossSubProject("render", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasRenderJvm = extrasRender.jvm
lazy val extrasRenderJs  = extrasRender.js.settings(Test / fork := false)

lazy val extrasRenderRefined    = crossSubProject("render-refined", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.Scala2Versions,
    libraryDependencies ++= List(
      libs.refined,
      libs.newtype     % Test,
      libs.cats        % Test,
      libs.refinedCats % Test,
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasRender)
lazy val extrasRenderRefinedJvm = extrasRenderRefined.jvm
lazy val extrasRenderRefinedJs  = extrasRenderRefined.js.settings(Test / fork := false)

lazy val extrasReflects    = crossSubProject("reflects", crossProject(JVMPlatform, JSPlatform))
  .settings(
//    crossScalaVersions  := props.Scala2Versions,
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (
      if (isScala3(scalaVersion.value)) List.empty
      else List(libs.scalaReflect(scalaVersion.value))
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest)
lazy val extrasReflectsJvm = extrasReflects.jvm
lazy val extrasReflectsJs  = extrasReflects.js.settings(Test / fork := false)

lazy val extrasTypeInfo    = crossSubProject("type-info", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (
      if (isScala3(scalaVersion.value)) List.empty
      else List(libs.scalaReflect(scalaVersion.value))
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest)
lazy val extrasTypeInfoJvm = extrasTypeInfo.jvm
lazy val extrasTypeInfoJs  = extrasTypeInfo.js.settings(Test / fork := false)

lazy val extrasRefinement = crossSubProject("refinement", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.Scala2Versions,
    libraryDependencies ++= List(
      libs.newtype.cross(CrossVersion.for3Use2_13)
    ) ++ List(libs.cats, libs.refined.cross(CrossVersion.for3Use2_13)),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest, extrasReflects)

lazy val extrasRefinementJvm = extrasRefinement.jvm
lazy val extrasRefinementJs  = extrasRefinement.js.settings(Test / fork := false)

lazy val extrasScalaIo    = crossSubProject("scala-io", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value.getParentFile / "shared/src/main"
      if (scalaVersion.value.startsWith("2.12")) {
        List(
          sharedSourceDir / "scala-2.12"
        )
      } else {
        List.empty
      }
    },
    Test / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value.getParentFile / "shared/src/test"
      if (scalaVersion.value.startsWith("2.12"))
        List(
          sharedSourceDir / "scala-2.12"
        )
      else
        Seq.empty
    },
  )
  .dependsOn(
    extrasCore
  )
lazy val extrasScalaIoJvm = extrasScalaIo.jvm
lazy val extrasScalaIoJs  = extrasScalaIo.js.settings(Test / fork := false)

lazy val extrasConcurrent    = crossSubProject("concurrent", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasConcurrentJvm = extrasConcurrent.jvm
lazy val extrasConcurrentJs  = extrasConcurrent.js.settings(Test / fork := false)

lazy val extrasConcurrentTesting = crossSubProject("concurrent-testing", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore, extrasConcurrent)

lazy val extrasConcurrentTestingJvm = extrasConcurrentTesting.jvm
lazy val extrasConcurrentTestingJs  = extrasConcurrentTesting.js.settings(Test / fork := false)

lazy val extrasCats = crossSubProject("cats", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (if (scalaVersion.value.startsWith("3")) {
                               List(libs.cats, libs.catsEffect3 % Test)
                             } else {
                               List(libs.cats, libs.catsEffect % Test)
                             }) ++ List("org.slf4j" % "slf4j-api" % "1.7.32" % Test),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasConcurrentTesting % Test)

lazy val extrasCatsJvm = extrasCats.jvm
lazy val extrasCatsJs  = extrasCats.js.settings(Test / fork := false)

lazy val extrasHedgehogCirce    = crossSubProject("hedgehog-circe", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats,
      libs.hedgehogCore,
      libs.circeCore,
      libs.circeParser,
//      libs.kittens      % Test,
      libs.circeGeneric % Test,
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(
    extrasTypeInfo
  )
lazy val extrasHedgehogCirceJvm = extrasHedgehogCirce.jvm
lazy val extrasHedgehogCirceJs  = extrasHedgehogCirce.js.settings(Test / fork := false)

lazy val extrasHedgehogCe3 = crossSubProject("hedgehog-ce3", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats,
      libs.catsEffect3,
      libs.libCatsEffectTestKit.excludeAll("org.scalacheck"),
      libs.hedgehogCore,
      libs.hedgehogRunner,
    ),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(extrasCore, extrasCats)

lazy val extrasHedgehogCatsEffect3Jvm = extrasHedgehogCe3.jvm
lazy val extrasHedgehogCatsEffect3Js  = extrasHedgehogCe3.js.settings(Test / fork := false)

lazy val extrasTestingTools = crossSubProject("testing-tools", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(extrasCore)

lazy val extrasTestingToolsJvm = extrasTestingTools.jvm
lazy val extrasTestingToolsJs  = extrasTestingTools.js.settings(Test / fork := false)

lazy val extrasTestingToolsCats = crossSubProject("testing-tools-cats", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats,
      libs.catsEffect % Test,
    ),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = (baseDirectory.value / ".." / "shared").getCanonicalFile / "src" / "main"
      if (isScala3(scalaVersion.value))
        Seq(
          sharedSourceDir / "scala-2.13_3"
        )
      else if (scalaVersion.value.startsWith("2.13"))
        Seq(
          sharedSourceDir / "scala-2.13_3"
        )
      else if (scalaVersion.value.startsWith("2.12"))
        Seq(
          sharedSourceDir / "scala-2.12"
        )
      else
        Seq.empty
    },
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(
    extrasCore,
    extrasTestingTools,
  )

lazy val extrasTestingToolsCatsJvm = extrasTestingToolsCats.jvm
lazy val extrasTestingToolsCatsJs  = extrasTestingToolsCats.js.settings(Test / fork := false)

lazy val extrasTestingToolsEffectie = crossSubProject("testing-tools-effectie", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats,
      libs.effectieCore,
      libs.effectieSyntax % Test,
      libs.effectieCe2    % Test,
      libs.catsEffect     % Test,
    ),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = (baseDirectory.value / ".." / "shared").getCanonicalFile / "src" / "main"
      if (isScala3(scalaVersion.value))
        Seq(
          sharedSourceDir / "scala-2.13_3"
        )
      else if (scalaVersion.value.startsWith("2.13"))
        Seq(
          sharedSourceDir / "scala-2.13_3"
        )
      else if (scalaVersion.value.startsWith("2.12"))
        Seq(
          sharedSourceDir / "scala-2.12"
        )
      else
        Seq.empty
    },
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(
    extrasCore,
    extrasTestingTools,
    extrasConcurrent        % Test,
    extrasConcurrentTesting % Test,
  )

lazy val extrasTestingToolsEffectieJvm = extrasTestingToolsEffectie.jvm
lazy val extrasTestingToolsEffectieJs  = extrasTestingToolsEffectie.js.settings(Test / fork := false)

lazy val docs = (project in file("docs-gen-tmp/docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs"),
    name := "docs",
    mdocIn := file("docs/common"),
    mdocOut := file("generated-docs/docs"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables := createMdocVariables(),
    docusaurDir := (ThisBuild / baseDirectory).value / "website",
    docusaurBuildDir := docusaurDir.value / "build",
  )
  .settings(noPublish)

lazy val docsExtrasRender = docsProject("docs-extras-render", file("docs-gen-tmp/extras-render"))
  .settings(
    mdocIn := file("docs/extras-render"),
    mdocOut := file("generated-docs/docs/extras-render"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-render"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-render" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasCats = docsProject("docs-extras-cats", file("docs-gen-tmp/extras-cats"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-cats"),
    mdocOut := file("generated-docs/docs/extras-cats"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-cats"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-cats" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasConcurrent = docsProject("docs-extras-concurrent", file("docs-gen-tmp/extras-concurrent"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-concurrent"),
    mdocOut := file("generated-docs/docs/extras-concurrent"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-concurrent"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-concurrent" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasDoobieTools = docsProject("docs-extras-doobie-tools", file("docs-gen-tmp/extras-doobie-tools"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-doobie-tools/common"),
    mdocOut := file("generated-docs/docs/extras-doobie-tools"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-doobie-tools"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasDoobieToolsCe2 =
  docsProject("docs-extras-doobie-tools-ce2", file("docs-gen-tmp/extras-doobie-tools-ce2"))
    .enablePlugins(MdocPlugin)
    .settings(
      mdocIn := file("docs/extras-doobie-tools/ce2"),
      mdocOut := file("generated-docs/docs/extras-doobie-tools/ce2"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-doobie-tools" / "ce2"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-doobie-tools-ce2" % latestVersion,
          libs.doobieCe2Core,
          libs.embeddedPostgres,
          libs.effectieCe2,
        )
      } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasDoobieToolsCe3 =
  docsProject("docs-extras-doobie-tools-ce3", file("docs-gen-tmp/extras-doobie-tools-ce3"))
    .enablePlugins(MdocPlugin)
    .settings(
      mdocIn := file("docs/extras-doobie-tools/ce3"),
      mdocOut := file("generated-docs/docs/extras-doobie-tools/ce3"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-doobie-tools" / "ce3"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-doobie-tools-ce3" % latestVersion,
          libs.doobieCe3Core,
          libs.embeddedPostgres,
          libs.effectieCe3,
        )
      } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasHedgehog = docsProject("docs-extras-hedgehog", file("docs-gen-tmp/extras-hedgehog"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-hedgehog"),
    mdocOut := file("generated-docs/docs/extras-hedgehog"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-hedgehog"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-hedgehog-ce3"   % latestVersion,
        "io.kevinlee" %% "extras-hedgehog-circe" % latestVersion,
      )
    } ++ List(
      libs.hedgehogCore,
      libs.hedgehogRunner,
      libs.circeGeneric,
    ),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasRefinement = docsProject("docs-extras-refinement", file("docs-gen-tmp/extras-refinement"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-refinement"),
    mdocOut := file("generated-docs/docs/extras-refinement"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-refinement"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-refinement" % latestVersion,
        libs.newtype,
      ) ++ List(libs.cats, libs.refined)
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasReflects = docsProject("docs-extras-reflects", file("docs-gen-tmp/extras-reflects"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-reflects"),
    mdocOut := file("generated-docs/docs/extras-reflects"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-reflects"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        libs.newtype,
        "io.kevinlee" %% "extras-reflects" % latestVersion,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    libraryDependencies := (if (isScala3(scalaVersion.value)) List.empty[ModuleID] else libraryDependencies.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasScalaIo = docsProject("docs-extras-scala-io", file("docs-gen-tmp/extras-scala-io"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-scala-io"),
    mdocOut := file("generated-docs/docs/extras-scala-io"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-scala-io"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-scala-io" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasString = docsProject("docs-extras-string", file("docs-gen-tmp/extras-string"))
  .enablePlugins(MdocPlugin)
  .settings(
    mdocIn := file("docs/extras-string"),
    mdocOut := file("generated-docs/docs/extras-string"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-string"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-string" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTypeInfo = docsProject("docs-extras-type-info", file("docs-gen-tmp/extras-type-info"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-type-info"),
    mdocOut := file("generated-docs/docs/extras-type-info"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-type-info"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-type-info" % latestVersion
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTypeInfoScala2 =
  docsProject("docs-extras-type-info-scala2", file("docs-gen-tmp/extras-type-info-scala2"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.Scala2Version,
      mdocIn := file("docs/extras-type-info-scala2"),
      mdocOut := file("generated-docs/docs/extras-type-info/scala2"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-type-info/scala2"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-type-info" % latestVersion
        )
      } ++ List(
        libs.newtype,
        libs.refined,
        libs.hedgehogCore,
        libs.hedgehogRunner,
      ),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasTypeInfoScala3 =
  docsProject("docs-extras-type-info-scala3", file("docs-gen-tmp/extras-type-info-scala3"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := "3.1.3",
      mdocIn := file("docs/extras-type-info-scala3"),
      mdocOut := file("generated-docs/docs/extras-type-info/scala3"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-type-info/scala3"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-type-info" % latestVersion
        )
      } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasCirce = docsProject("docs-extras-circe", file("docs-gen-tmp/extras-circe"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-circe"),
    mdocOut := file("generated-docs/docs/extras-circe"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-circe"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-circe" % latestVersion,
        libs.circeCore,
        libs.circeParser,
        libs.circeGeneric,
        libs.circeLiteral,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasFs2 = docsProject("docs-extras-fs2", file("docs-gen-tmp/extras-fs2"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-fs2/common"),
    mdocOut := file("generated-docs/docs/extras-fs2"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-fs2"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasFs2V2 = docsProject("docs-extras-fs2-v2", file("docs-gen-tmp/extras-fs2/v2"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-fs2/v2"),
    mdocOut := file("generated-docs/docs/extras-fs2/v2"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-fs2" / "v2"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-fs2-v2-text" % latestVersion,
        libs.http4sServerDsl_0_22,
      )
    },
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasFs2V3 = docsProject("docs-extras-fs2-v3", file("docs-gen-tmp/extras-fs2/v3"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-fs2/v3"),
    mdocOut := file("generated-docs/docs/extras-fs2/v3"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-fs2" / "v3"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-fs2-v3-text" % latestVersion,
        libs.http4sServerDsl_0_23,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTestingTools = docsProject("docs-extras-testing-tools", file("docs-gen-tmp/extras-testing-tools"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-testing-tools/common"),
    mdocOut := file("generated-docs/docs/extras-testing-tools"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-testing-tools"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-testing-tools" % latestVersion,
        libs.newtype,
        libs.cats,
        libs.refined,
        libs.refinedCats,
      )
    } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTestingToolsCats =
  docsProject("docs-extras-testing-tools-cats", file("docs-gen-tmp/extras-testing-tools-cats"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.Scala2Version,
      mdocIn := file("docs/extras-testing-tools/cats"),
      mdocOut := file("generated-docs/docs/extras-testing-tools/cats"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-testing-tools" / "cats"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-testing-tools-cats" % latestVersion,
          libs.newtype,
          libs.cats,
          libs.catsEffect,
          libs.refined,
          libs.refinedCats,
        )
      } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasTestingToolsEffectie =
  docsProject("docs-extras-testing-tools-effectie", file("docs-gen-tmp/extras-testing-tools-effectie"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.Scala2Version,
      mdocIn := file("docs/extras-testing-tools/effectie"),
      mdocOut := file("generated-docs/docs/extras-testing-tools/effectie"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-testing-tools" / "effectie"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-testing-tools-effectie" % latestVersion,
          libs.newtype,
          libs.cats,
          libs.catsEffect,
          libs.refined,
          libs.refinedCats,
          libs.effectieCe2,
        )
      } ++ List(libs.hedgehogCore, libs.hedgehogRunner),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

// scalafmt: off

def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

lazy val mavenCentralPublishSettings: SettingsDefinition = List(
  /* Publish to Maven Central { */
  sonatypeCredentialHost := props.SonatypeCredentialHost,
  sonatypeRepository := props.SonatypeRepository,
  /* } Publish to Maven Central */
)

def subProject(projectName: String): Project = {
  val prefixedName = prefixedProjectName(projectName)
  Project(projectName, file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      Test / fork := true,
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
      name := prefixedName,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      Test / fork := true,
      libraryDependencies ++= libs.hedgehog,
      testFrameworks ~=
        (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
      wartremoverErrors ++= Warts.allBut(Wart.Any, Wart.Nothing, Wart.ImplicitConversion, Wart.ImplicitParameter),
      Compile / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      //      Test / console / wartremoverErrors      := List.empty,
      //      Test / console / wartremoverWarnings    := List.empty,
      Test / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      scalacOptions := {
        val options = scalacOptions.value
        if (isScala3(scalaVersion.value)) {
          options.filterNot(_.startsWith("-P:wartremover"))
        } else {
          options
        }
      },
      scalacOptions := scalacOptionsPostProcess(scalaVersion.value, scalacOptions.value),
      scalacOptions := {
        if (scalaVersion.value.startsWith("2.13"))
          "-Ymacro-annotations" +: scalacOptions.value
        else
          scalacOptions.value
      },
    )
    .settings(
      mavenCentralPublishSettings
    )
}

def removeScala3Incompatible(scalaVersion: String, libraryDependencies: Seq[ModuleID]): Seq[ModuleID] =
  if (isScala3(scalaVersion)) {
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
  "docs/clean",
)
addCommandAlias(
  "docsMdocAll",
  "; docsExtrasRender/mdoc; docsExtrasCats/mdoc; docsExtrasCirce/mdoc; docsExtrasHedgehog/mdoc; docsExtrasDoobieTools/mdoc; docsExtrasDoobieToolsCe2/mdoc; docsExtrasDoobieToolsCe3/mdoc; docsExtrasRefinement/mdoc; docsExtrasTypeInfo/mdoc; docsExtrasTypeInfoScala2/mdoc; docsExtrasTypeInfoScala3/mdoc; docsExtrasScalaIo/mdoc; docsExtrasString/mdoc; docsExtrasFs2/mdoc; docsExtrasFs2V2/mdoc; docsExtrasFs2V3/mdoc; docsExtrasTestingTools/mdoc; docsExtrasTestingToolsCats/mdoc; docsExtrasTestingToolsEffectie/mdoc; docsExtrasConcurrent/mdoc; docsExtrasReflects/mdoc; docs/mdoc",
)

def easeScalacOptionsForDocs(scalacOptions: Seq[String]): Seq[String] =
  scalacOptions.filterNot(_ == "-Xfatal-warnings")

def kebabCaseToCamelCase(s: String): String               = s.split("-+").toList match {
  case head :: tail => (head :: tail.map(_.capitalize)).mkString
  case Nil => ""
}
def docsProject(projectName: String, path: File): Project =
  Project(kebabCaseToCamelCase(projectName), path)
    .enablePlugins(MdocPlugin)
    .settings(
      name := projectName,
      scalacOptions ~= (ops => easeScalacOptionsForDocs(ops)),
    )

lazy val props = new {

  private val GitHubRepo = findRepoOrgAndName

  val Org        = "io.kevinlee"
  val GitHubUser = GitHubRepo.fold("kevin-lee")(_.orgToString)
  val RepoName   = GitHubRepo.fold("extras")(_.nameToString)

  val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

  val Scala2Versions = List(
    "2.13.10",
    "2.12.17",
  )
  val Scala2Version  = Scala2Versions.head

  val Scala3Versions = List("3.1.3")
  val Scala3Version  = Scala3Versions.head

  val ProjectScalaVersion = Scala2Version
//  val ProjectScalaVersion = Scala3Version

  val CrossScalaVersions =
    (Scala3Versions ++ Scala2Versions).distinct

  val SonatypeCredentialHost = "s01.oss.sonatype.org"
  val SonatypeRepository     = s"https://$SonatypeCredentialHost/service/local"

  val CatsVersion      = "2.8.0"
  val Cats2_0_0Version = "2.0.0"

  val CatsEffect3Version = "3.3.14"
  val CatsEffectVersion  = "2.5.5"

  val DoobieCe2Version = "0.13.4"
  val DoobieCe3Version = "1.0.0-RC2"

  val KittensVersion = "3.0.0"

  val CirceVersion         = "0.14.1"
  val Circe_0_14_3_Version = "0.14.3"

  val Fs2V2Version = "2.5.11"
  val Fs2V3Version = "3.3.0"

  val Http4s_0_22_Version = "0.22.15"
  val Http4s_0_23_Version = "0.23.16"

  val HedgehogVersion = "0.9.0"

  val HedgehogExtraVersion = "0.3.0"

  val NewtypeVersion = "0.4.4"

  val RefinedVersion       = "0.9.27"
  val RefinedLatestVersion = "0.10.1"

  val EmbeddedPostgresVersion = "2.0.1"

  val EffectieVersion = "2.0.0-beta14"

  val isScala3Incompatible: ModuleID => Boolean =
    m =>
      m.name == "wartremover" ||
        m.name == "ammonite" ||
        m.name == "kind-projector" ||
        m.name == "better-monadic-for"

  val IncludeTest = "compile->compile;test->test"
}

lazy val libs = new {
  lazy val cats    = "org.typelevel" %% "cats-core" % props.CatsVersion
  lazy val catsOld = "org.typelevel" %% "cats-core" % props.Cats2_0_0Version

  lazy val catsEffect3 = "org.typelevel" %% "cats-effect" % props.CatsEffect3Version
  lazy val catsEffect  = "org.typelevel" %% "cats-effect" % props.CatsEffectVersion

  lazy val doobieCe2Core    = "org.tpolecat" %% "doobie-core"    % props.DoobieCe2Version
  lazy val doobieCe2Refined = "org.tpolecat" %% "doobie-refined" % props.DoobieCe2Version
  lazy val doobieCe3Core    = "org.tpolecat" %% "doobie-core"    % props.DoobieCe3Version

  lazy val libCatsEffectTestKit = "org.typelevel" %% "cats-effect-testkit" % props.CatsEffect3Version

  lazy val kittens = "org.typelevel" %% "kittens" % props.KittensVersion

  lazy val circeCore        = "io.circe" %% "circe-core" % props.CirceVersion
  lazy val circeCore_0_14_3 = "io.circe" %% "circe-core" % props.Circe_0_14_3_Version

  lazy val circeParser        = "io.circe" %% "circe-parser" % props.CirceVersion
  lazy val circeParser_0_14_3 = "io.circe" %% "circe-parser" % props.Circe_0_14_3_Version

  lazy val circeGeneric        = "io.circe" %% "circe-generic" % props.CirceVersion
  lazy val circeGeneric_0_14_3 = "io.circe" %% "circe-generic" % props.Circe_0_14_3_Version

  lazy val circeLiteral        = "io.circe" %% "circe-literal" % props.CirceVersion
  lazy val circeLiteral_0_14_3 = "io.circe" %% "circe-literal" % props.Circe_0_14_3_Version

  lazy val fs2V2 = "co.fs2" %% "fs2-core" % props.Fs2V2Version
  lazy val fs2V3 = "co.fs2" %% "fs2-core" % props.Fs2V3Version

  lazy val http4sServer_0_22    = "org.http4s" %% "http4s-server" % props.Http4s_0_22_Version
  lazy val http4sServerDsl_0_22 = "org.http4s" %% "http4s-dsl"    % props.Http4s_0_22_Version

  lazy val http4sServer_0_23    = "org.http4s" %% "http4s-server" % props.Http4s_0_23_Version
  lazy val http4sServerDsl_0_23 = "org.http4s" %% "http4s-dsl"    % props.Http4s_0_23_Version

  lazy val hedgehogCore   = "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion
  lazy val hedgehogRunner = "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion
  lazy val hedgehogSbt    = "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion

  lazy val hedgehogExtraCore    = "io.kevinlee" %% "hedgehog-extra-core"    % props.HedgehogExtraVersion
  lazy val hedgehogExtraRefined = "io.kevinlee" %% "hedgehog-extra-refined" % props.HedgehogExtraVersion

  lazy val hedgehog = List(
    hedgehogCore,
    hedgehogRunner,
    hedgehogSbt,
  ).map(_ % Test)

  def scalaReflect(scalaVersion: String): ModuleID = "org.scala-lang" % "scala-reflect" % scalaVersion

  lazy val newtype = "io.estatico" %% "newtype" % props.NewtypeVersion
  lazy val refined =
    ("eu.timepit" %% "refined" % props.RefinedVersion).excludeAll("org.scala-lang.modules" %% "scala-xml")
  lazy val refinedLatest =
    ("eu.timepit" %% "refined" % props.RefinedLatestVersion).excludeAll("org.scala-lang.modules" %% "scala-xml")

  lazy val refinedCats       = "eu.timepit" %% "refined-cats" % props.RefinedVersion
  lazy val refinedCatsLatest = "eu.timepit" %% "refined-cats" % props.RefinedLatestVersion

  lazy val embeddedPostgres = "io.zonky.test" % "embedded-postgres" % props.EmbeddedPostgresVersion

  lazy val effectieCore   = "io.kevinlee" %% "effectie-core"         % props.EffectieVersion
  lazy val effectieSyntax = "io.kevinlee" %% "effectie-syntax"       % props.EffectieVersion
  lazy val effectieCe2    = "io.kevinlee" %% "effectie-cats-effect2" % props.EffectieVersion
  lazy val effectieCe3    = "io.kevinlee" %% "effectie-cats-effect3" % props.EffectieVersion
}

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")

lazy val scala3cLanguageOptions =
  List(
    "dynamics",
    "existentials",
    "higherKinds",
    "reflectiveCalls",
    "experimental.macros",
    "implicitConversions",
  ).map("-language:" + _)

def scalacOptionsPostProcess(scalaVersion: String, options: Seq[String]): Seq[String] =
  if (scalaVersion.startsWith("3.")) {
    scala3cLanguageOptions ++
      options.filterNot(o =>
        o == "-language:dynamics,existentials,higherKinds,reflectiveCalls,experimental.macros,implicitConversions" || o == "UTF-8",
      )
  } else {
    options.filterNot(_ == "UTF-8")
  }
