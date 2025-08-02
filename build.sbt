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

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3")) file(".scalafix-scala3.conf").some
  else file(".scalafix-scala2.conf").some
)

inThisBuild(
  List(
    scalaVersion := scalaVersion.value,
    semanticdbEnabled := true,
    semanticdbVersion := "4.8.15",
  )
)

ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % "0.3.0"

lazy val extras = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name := props.RepoName,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
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
    extrasRefinementJvm,
    extrasScalaIoJvm,
    extrasScalaIoJs,
    extrasStringJvm,
    extrasStringJs,
    extrasTestingToolsJvm,
    extrasTestingToolsCatsJvm,
    extrasTestingToolsEffectieJvm,
    extrasTypeInfoJvm,
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
    libraryDependencies += libs.cats.value % Test,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasStringJvm = extrasString.jvm
lazy val extrasStringJs  = extrasString.js.settings(Test / fork := false)

lazy val extrasCirce    = crossSubProject("circe", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (if (isScala3(scalaVersion.value))
                               List(
                                 libs.circeCore_0_14_3.value,
                                 libs.circeJawn_0_14_3.value    % Test,
                                 libs.circeParser_0_14_3.value  % Test,
                                 libs.circeGeneric_0_14_3.value % Test,
                                 libs.circeLiteral_0_14_3.value % Test,
                               )
                             else
                               List(
                                 libs.circeCore.value,
                                 libs.circeJawn.value           % Test,
                                 libs.circeParser.value         % Test,
                                 libs.circeGeneric.value        % Test,
                                 libs.circeLiteral.value        % Test,
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
        libs.embeddedPostgres  % Test,
        libs.effectieCe2.value % Test,
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
        libs.embeddedPostgres  % Test,
        libs.effectieCe3.value % Test,
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
      libs.fs2V2.value
    ),
  )
lazy val extrasFs2V2TextJvm = extrasFs2V2Text
  .jvm
  .settings(
    libraryDependencies ++= List(libs.http4sServerDsl_0_22 % Test)
  )
lazy val extrasFs2V2TextJs  = extrasFs2V2Text
  .js
  .settings(Test / fork := false)
//  .settings(libraryDependencies ++= List(libs.munit.value))

lazy val extrasFs2V3Text    = crossSubProject("fs2-v3-text", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.fs2V3.value,
      libs.http4sServer_0_23.value    % Test,
      libs.http4sServerDsl_0_23.value % Test,
    ),
  )
  .dependsOn(extrasHedgehogCe3 % Test)
lazy val extrasFs2V3TextJvm = extrasFs2V3Text.jvm
lazy val extrasFs2V3TextJs  = extrasFs2V3Text.js.settings(Test / fork := false)

lazy val extrasRender    = crossSubProject("render", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats.value % Optional
    ) ++ (
      if (isScala3(scalaVersion.value)) List.empty else List(libs.scalacCompatAnnotation)
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasRenderJvm = extrasRender.jvm
lazy val extrasRenderJs  = extrasRender
  .js
  .settings(Test / fork := false)
  .settings(
    libraryDependencies ++= List(
      libs.scalajsJavaSecurerandom.value % Test
    )
  )

lazy val extrasRenderRefined    = crossSubProject("render-refined", crossProject(JVMPlatform, JSPlatform))
  .settings(
    scalaVersion := props.Scala2Version,
    crossScalaVersions := props.Scala2Versions,
    libraryDependencies ++= List(
      libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"),
      libs.newtype           % Test,
      libs.cats.value        % Test,
      libs.refinedCats.value % Test,
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasRender)
lazy val extrasRenderRefinedJvm = extrasRenderRefined.jvm
lazy val extrasRenderRefinedJs  = extrasRenderRefined.js.settings(Test / fork := false)

lazy val extrasReflects    = crossSubProject("reflects", crossProject(JVMPlatform))
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

lazy val extrasTypeInfo    = crossSubProject("type-info", crossProject(JVMPlatform))
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

lazy val extrasRefinement = crossSubProject("refinement", crossProject(JVMPlatform))
  .settings(
    crossScalaVersions := props.Scala2Versions,
    libraryDependencies ++= List(
      libs.newtype.cross(CrossVersion.for3Use2_13)
    ) ++ List(
      libs.cats.value,
      libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml").cross(CrossVersion.for3Use2_13),
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore % props.IncludeTest, extrasReflects)

lazy val extrasRefinementJvm = extrasRefinement.jvm

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

lazy val extrasConcurrentTesting = crossSubProject("concurrent-testing", crossProject(JVMPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
  .dependsOn(extrasCore, extrasConcurrent)

lazy val extrasConcurrentTestingJvm = extrasConcurrentTesting.jvm

lazy val extrasCats = crossSubProject("cats", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= (if (scalaVersion.value.startsWith("3")) {
                               List(libs.cats.value, libs.catsEffect3.value % Test)
                             } else {
                               List(libs.cats.value, libs.catsEffect.value % Test)
                             }) ++ List("org.slf4j" % "slf4j-api" % "1.7.32" % Test),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )

lazy val extrasCatsJvm = extrasCats
  .jvm
  .dependsOn(extrasConcurrentTestingJvm % Test)
lazy val extrasCatsJs  = extrasCats
  .js
  .settings(Test / fork := false)
  .settings(
    libraryDependencies ++= List(
      libs.scalaJsMacrotaskExecutor.value,
      libs.munit.value,
    )
  )

lazy val extrasHedgehogCirce    = crossSubProject("hedgehog-circe", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats.value,
      libs.hedgehogCore.value,
      libs.circeCore.value,
      libs.circeParser.value,
      libs.circeJawn.value,
//      libs.kittens      % Test,
      libs.circeGeneric.value % Test,
    ),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
  )
lazy val extrasHedgehogCirceJvm = extrasHedgehogCirce
  .jvm
  .dependsOn(
    extrasTypeInfoJvm
  )
lazy val extrasHedgehogCirceJs  = extrasHedgehogCirce.js.settings(Test / fork := false)

lazy val extrasHedgehogCe3 = crossSubProject("hedgehog-ce3", crossProject(JVMPlatform, JSPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats.value,
      libs.catsEffect3.value,
      libs.libCatsEffectTestKit.value.excludeAll("org.scalacheck"),
      libs.hedgehogCore.value,
      libs.hedgehogRunner.value,
    ),
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(extrasCore, extrasCats)

lazy val extrasHedgehogCatsEffect3Jvm = extrasHedgehogCe3.jvm
lazy val extrasHedgehogCatsEffect3Js  = extrasHedgehogCe3.js.settings(Test / fork := false)

lazy val extrasTestingTools = crossSubProject("testing-tools", crossProject(JVMPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies :=
      removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    Test / console / scalacOptions := List.empty,
  )
  .dependsOn(extrasCore)

lazy val extrasTestingToolsJvm = extrasTestingTools.jvm

lazy val extrasTestingToolsCats = crossSubProject("testing-tools-cats", crossProject(JVMPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats.value,
      libs.catsEffect.value % Test,
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

lazy val extrasTestingToolsEffectie = crossSubProject("testing-tools-effectie", crossProject(JVMPlatform))
  .settings(
    crossScalaVersions := props.CrossScalaVersions,
    libraryDependencies ++= List(
      libs.cats.value,
      libs.effectieCore.value,
      libs.effectieSyntax.value % Test,
      libs.effectieCe2.value    % Test,
      libs.catsEffect.value     % Test,
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

lazy val docs = (project in file("docs-gen-tmp/docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-render"),
    mdocOut := file("generated-docs/docs/extras-render"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-render"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect.value
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-render" % latestVersion
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasCats = docsProject("docs-extras-cats", file("docs-gen-tmp/extras-cats"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-cats"),
    mdocOut := file("generated-docs/docs/extras-cats"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-cats"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(
      libs.catsEffect.value
    ) ++ {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-cats" % latestVersion
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasConcurrent = docsProject("docs-extras-concurrent", file("docs-gen-tmp/extras-concurrent"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-concurrent"),
    mdocOut := file("generated-docs/docs/extras-concurrent"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-concurrent"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-concurrent" % latestVersion
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasDoobieTools = docsProject("docs-extras-doobie-tools", file("docs-gen-tmp/extras-doobie-tools"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
      scalaVersion := props.DocsScalaVersion,
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
          libs.effectieCe2.value,
        )
      } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasDoobieToolsCe3 =
  docsProject("docs-extras-doobie-tools-ce3", file("docs-gen-tmp/extras-doobie-tools-ce3"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.DocsScalaVersion,
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
          libs.effectieCe3.value,
        )
      } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasHedgehog = docsProject("docs-extras-hedgehog", file("docs-gen-tmp/extras-hedgehog"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-hedgehog"),
    mdocOut := file("generated-docs/docs/extras-hedgehog"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-hedgehog"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion   = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-hedgehog-ce3"   % latestVersion,
        "io.kevinlee" %% "extras-hedgehog-circe" % latestVersion,
      )
    } ++ List(
      libs.hedgehogCore.value,
      libs.hedgehogRunner.value,
      libs.circeGeneric.value,
    ),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasRefinement = docsProject("docs-extras-refinement", file("docs-gen-tmp/extras-refinement"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-refinement"),
    mdocOut := file("generated-docs/docs/extras-refinement"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-refinement"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-refinement" % latestVersion,
        libs.newtype,
      ) ++ List(libs.cats.value, libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"))
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasReflects = docsProject("docs-extras-reflects", file("docs-gen-tmp/extras-reflects"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    libraryDependencies := (if (isScala3(scalaVersion.value)) List.empty[ModuleID] else libraryDependencies.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasScalaIo = docsProject("docs-extras-scala-io", file("docs-gen-tmp/extras-scala-io"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-scala-io"),
    mdocOut := file("generated-docs/docs/extras-scala-io"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-scala-io"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-scala-io" % latestVersion
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasString = docsProject("docs-extras-string", file("docs-gen-tmp/extras-string"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    mdocIn := file("docs/extras-string"),
    mdocOut := file("generated-docs/docs/extras-string"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-string"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-string" % latestVersion
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTypeInfo = docsProject("docs-extras-type-info", file("docs-gen-tmp/extras-type-info"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTypeInfoScala2 =
  docsProject("docs-extras-type-info-scala2", file("docs-gen-tmp/extras-type-info-scala2"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.DocsScalaVersion,
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
        libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"),
        libs.hedgehogCore.value,
        libs.hedgehogRunner.value,
      ),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasTypeInfoScala3 =
  docsProject("docs-extras-type-info-scala3", file("docs-gen-tmp/extras-type-info-scala3"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.DocsScalaVersion,
      scalaVersion := "3.3.6",
      mdocIn := file("docs/extras-type-info-scala3"),
      mdocOut := file("generated-docs/docs/extras-type-info/scala3"),
      cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-type-info/scala3"),
      libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
      libraryDependencies ++= {
        val latestVersion = getLatestExtrasVersion()
        List(
          "io.kevinlee" %% "extras-type-info" % latestVersion
        )
      } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasCirce = docsProject("docs-extras-circe", file("docs-gen-tmp/extras-circe"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-circe"),
    mdocOut := file("generated-docs/docs/extras-circe"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-circe"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-circe" % latestVersion,
        libs.circeCore.value,
        libs.circeParser.value,
        libs.circeGeneric.value,
        libs.circeLiteral.value,
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasFs2 = docsProject("docs-extras-fs2", file("docs-gen-tmp/extras-fs2"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-fs2/common"),
    mdocOut := file("generated-docs/docs/extras-fs2"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-fs2"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasFs2V2 = docsProject("docs-extras-fs2-v2", file("docs-gen-tmp/extras-fs2/v2"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
    scalaVersion := props.DocsScalaVersion,
    scalaVersion := props.Scala2Version,
    mdocIn := file("docs/extras-fs2/v3"),
    mdocOut := file("generated-docs/docs/extras-fs2/v3"),
    cleanFiles += ((ThisBuild / baseDirectory).value / "generated-docs" / "docs" / "extras-fs2" / "v3"),
    libraryDependencies := removeScala3Incompatible(scalaVersion.value, libraryDependencies.value),
    libraryDependencies ++= {
      val latestVersion = getLatestExtrasVersion()
      List(
        "io.kevinlee" %% "extras-fs2-v3-text" % latestVersion,
        libs.http4sServerDsl_0_23.value,
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTestingTools = docsProject("docs-extras-testing-tools", file("docs-gen-tmp/extras-testing-tools"))
  .enablePlugins(MdocPlugin)
  .settings(
    scalaVersion := props.DocsScalaVersion,
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
        libs.cats.value,
        libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"),
        libs.refinedCats.value,
      )
    } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
    mdocVariables := createMdocVariables(),
  )
  .settings(noPublish)

lazy val docsExtrasTestingToolsCats =
  docsProject("docs-extras-testing-tools-cats", file("docs-gen-tmp/extras-testing-tools-cats"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.DocsScalaVersion,
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
          libs.cats.value,
          libs.catsEffect.value,
          libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"),
          libs.refinedCats.value,
        )
      } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

lazy val docsExtrasTestingToolsEffectie =
  docsProject("docs-extras-testing-tools-effectie", file("docs-gen-tmp/extras-testing-tools-effectie"))
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := props.DocsScalaVersion,
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
          libs.cats.value,
          libs.catsEffect.value,
          libs.refined.value.excludeAll("org.scala-lang.modules" %% "scala-xml"),
          libs.refinedCats.value,
          libs.effectieCe2.value,
        )
      } ++ List(libs.hedgehogCore.value, libs.hedgehogRunner.value),
      mdocVariables := createMdocVariables(),
    )
    .settings(noPublish)

// scalafmt: off

def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

def subProject(projectName: String): Project = {
  val prefixedName = prefixedProjectName(projectName)
  Project(projectName, file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      Test / fork := true,
      scalacOptions += "-explain",
      libraryDependencies ++= libs.hedgehog.value,
      testFrameworks ~=
        (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
    )
}

def crossSubProject(projectName: String, crossProject: CrossProject.Builder): CrossProject = {
  val prefixedName = prefixedProjectName(projectName)
  crossProject
    .in(file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      Test / fork := true,
      libraryDependencies ++= libs.hedgehog.value,
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
      scalacOptions ~= (ops => "-Ymacro-annotations" +: easeScalacOptionsForDocs(ops)),
    )

lazy val props = new {

  private val GitHubRepo = findRepoOrgAndName

  val Org        = "io.kevinlee"
  val GitHubUser = GitHubRepo.fold("kevin-lee")(_.orgToString)
  val RepoName   = GitHubRepo.fold("extras")(_.nameToString)

  val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

  val Scala2Versions = List(
    "2.13.12",
    "2.12.18",
  )
  val Scala2Version  = Scala2Versions.head

  val Scala3Versions = List("3.1.3")
//  val Scala3Versions = List("3.3.6")
  val Scala3Version  = Scala3Versions.head

  val ProjectScalaVersion = Scala2Version
//  val ProjectScalaVersion = Scala3Version

  val DocsScalaVersion = Scala2Version

  val CrossScalaVersions =
    (Scala3Versions ++ Scala2Versions).distinct

  val CatsVersion      = "2.8.0"
  val Cats2_0_0Version = "2.0.0"

  val CatsEffect3Version = "3.3.14"
  val CatsEffectVersion  = "2.5.5"

  val DoobieCe2Version = "0.13.4"
  val DoobieCe3Version = "1.0.0-RC2"

  val KittensVersion = "3.0.0"

  val CirceVersion         = "0.14.2"
  val Circe_0_14_3_Version = "0.14.3"

  val Fs2V2Version = "2.5.11"
  val Fs2V3Version = "3.3.0"

  val Http4s_0_22_Version = "0.22.15"
  val Http4s_0_23_Version = "0.23.16"

  val HedgehogVersion = "0.10.1"

  val HedgehogExtraVersion = "0.11.0"

  val NewtypeVersion = "0.4.4"

  val RefinedVersion       = "0.9.27"
  val RefinedLatestVersion = "0.10.1"

  val EmbeddedPostgresVersion = "2.0.1"

  val EffectieVersion = "2.0.0"

  val ScalajsJavaSecurerandomVersion = "1.0.0"

  val ScalaJsMacrotaskExecutorVersion = "1.1.1"

  val ScalacCompatAnnotationVersion = "0.1.4"

  val MunitVersion = "0.7.29"

  val isScala3Incompatible: ModuleID => Boolean =
    m =>
      m.name == "wartremover" ||
        m.name == "ammonite" ||
        m.name == "kind-projector" ||
        m.name == "better-monadic-for"

  val IncludeTest = "compile->compile;test->test"
}

lazy val libs = new {
  lazy val cats    = Def.setting("org.typelevel" %%% "cats-core" % props.CatsVersion)
  lazy val catsOld = Def.setting("org.typelevel" %%% "cats-core" % props.Cats2_0_0Version)

  lazy val catsEffect3 = Def.setting("org.typelevel" %%% "cats-effect" % props.CatsEffect3Version)
  lazy val catsEffect  = Def.setting("org.typelevel" %%% "cats-effect" % props.CatsEffectVersion)

  lazy val doobieCe2Core    = "org.tpolecat" %% "doobie-core"    % props.DoobieCe2Version
  lazy val doobieCe2Refined = "org.tpolecat" %% "doobie-refined" % props.DoobieCe2Version
  lazy val doobieCe3Core    = "org.tpolecat" %% "doobie-core"    % props.DoobieCe3Version

  lazy val libCatsEffectTestKit = Def.setting("org.typelevel" %%% "cats-effect-testkit" % props.CatsEffect3Version)

  lazy val kittens = Def.setting("org.typelevel" %%% "kittens" % props.KittensVersion)

  lazy val circeCore        = Def.setting("io.circe" %%% "circe-core" % props.CirceVersion)
  lazy val circeCore_0_14_3 = Def.setting("io.circe" %%% "circe-core" % props.Circe_0_14_3_Version)

  lazy val circeJawn        = Def.setting("io.circe" %%% "circe-jawn" % props.CirceVersion)
  lazy val circeJawn_0_14_3 = Def.setting("io.circe" %%% "circe-jawn" % props.Circe_0_14_3_Version)

  lazy val circeParser        = Def.setting("io.circe" %%% "circe-parser" % props.CirceVersion)
  lazy val circeParser_0_14_3 = Def.setting("io.circe" %%% "circe-parser" % props.Circe_0_14_3_Version)

  lazy val circeGeneric        = Def.setting("io.circe" %%% "circe-generic" % props.CirceVersion)
  lazy val circeGeneric_0_14_3 = Def.setting("io.circe" %%% "circe-generic" % props.Circe_0_14_3_Version)

  lazy val circeLiteral        = Def.setting("io.circe" %%% "circe-literal" % props.CirceVersion)
  lazy val circeLiteral_0_14_3 = Def.setting("io.circe" %%% "circe-literal" % props.Circe_0_14_3_Version)

  lazy val fs2V2 = Def.setting("co.fs2" %%% "fs2-core" % props.Fs2V2Version)
  lazy val fs2V3 = Def.setting("co.fs2" %%% "fs2-core" % props.Fs2V3Version)

  lazy val http4sServer_0_22    = "org.http4s" %% "http4s-server" % props.Http4s_0_22_Version
  lazy val http4sServerDsl_0_22 = "org.http4s" %% "http4s-dsl"    % props.Http4s_0_22_Version

  lazy val http4sServer_0_23    = Def.setting("org.http4s" %%% "http4s-server" % props.Http4s_0_23_Version)
  lazy val http4sServerDsl_0_23 = Def.setting("org.http4s" %%% "http4s-dsl" % props.Http4s_0_23_Version)

  lazy val hedgehogCore   = Def.setting("qa.hedgehog" %%% "hedgehog-core" % props.HedgehogVersion)
  lazy val hedgehogRunner = Def.setting("qa.hedgehog" %%% "hedgehog-runner" % props.HedgehogVersion)
  lazy val hedgehogSbt    = Def.setting("qa.hedgehog" %%% "hedgehog-sbt" % props.HedgehogVersion)

  lazy val scalacCompatAnnotation = "org.typelevel" %% "scalac-compat-annotation" % props.ScalacCompatAnnotationVersion

  lazy val hedgehogExtraCore    = Def.setting("io.kevinlee" %%% "hedgehog-extra-core" % props.HedgehogExtraVersion)
  lazy val hedgehogExtraRefined = Def.setting("io.kevinlee" %%% "hedgehog-extra-refined" % props.HedgehogExtraVersion)

  lazy val hedgehog = Def.setting(
    List(
      hedgehogCore.value,
      hedgehogRunner.value,
      hedgehogSbt.value,
    ).map(_ % Test)
  )

  def scalaReflect(scalaVersion: String): ModuleID = "org.scala-lang" % "scala-reflect" % scalaVersion

  lazy val newtype = "io.estatico" %% "newtype" % props.NewtypeVersion
  lazy val refined = Def.setting(("eu.timepit" %%% "refined" % props.RefinedVersion))
//  lazy val refinedLatest = Def.setting("eu.timepit" %%% "refined" % props.RefinedLatestVersion)

  lazy val refinedCats       = Def.setting("eu.timepit" %%% "refined-cats" % props.RefinedVersion)
  lazy val refinedCatsLatest = Def.setting("eu.timepit" %%% "refined-cats" % props.RefinedLatestVersion)

  lazy val embeddedPostgres = "io.zonky.test" % "embedded-postgres" % props.EmbeddedPostgresVersion

  lazy val effectieCore   = Def.setting("io.kevinlee" %% "effectie-core" % props.EffectieVersion)
  lazy val effectieSyntax = Def.setting("io.kevinlee" %% "effectie-syntax" % props.EffectieVersion)
  lazy val effectieCe2    = Def.setting("io.kevinlee" %% "effectie-cats-effect2" % props.EffectieVersion)
  lazy val effectieCe3    = Def.setting("io.kevinlee" %% "effectie-cats-effect3" % props.EffectieVersion)

  lazy val scalajsJavaSecurerandom =
    Def.setting(
      ("org.scala-js" %%% "scalajs-java-securerandom" % props.ScalajsJavaSecurerandomVersion).cross(
        CrossVersion.for3Use2_13
      )
    )

  lazy val scalaJsMacrotaskExecutor =
    Def.setting("org.scala-js" %%% "scala-js-macrotask-executor" % props.ScalaJsMacrotaskExecutorVersion % Test)

  lazy val munit = Def.setting("org.scalameta" %%% "munit" % props.MunitVersion % Test)

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
        o == "-language:dynamics,existentials,higherKinds,reflectiveCalls,experimental.macros,implicitConversions" || o == "UTF-8"
      )
  } else {
    options.filterNot(_ == "UTF-8")
  }
