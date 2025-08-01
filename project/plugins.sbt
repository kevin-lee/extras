import sbt.Defaults.sbtPluginExtra

logLevel := sbt.Level.Warn
scalaVersion := "2.12.18"

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.1")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.1.7")

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix"  % "0.11.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt"  % "2.5.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.10")
addSbtPlugin("org.scalameta" % "sbt-mdoc"      % "2.5.2")
addSbtPlugin("io.kevinlee"   % "sbt-docusaur"  % "0.17.0")

addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.13.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.2.0")

val sbtDevOopsVersion = "3.2.1"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)

addSbtPlugin("io.kevinlee" % "sbt-devoops-starter" % sbtDevOopsVersion)

addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.2")
