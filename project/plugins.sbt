import sbt.Defaults.sbtPluginExtra

logLevel := sbt.Level.Warn
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

libraryDependencies ++= {
  val sbtV   = (pluginCrossBuild / sbtBinaryVersion).value
  val scalaV = (update / scalaBinaryVersion).value
  if (scalaV.startsWith("3")) {
    List.empty
  } else {
    List(sbtPluginExtra("org.wartremover" % "sbt-wartremover" % "2.4.13", sbtV, scalaV))
  }
}

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix"  % "0.10.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt"  % "2.4.6")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.2")
addSbtPlugin("org.scalameta" % "sbt-mdoc"      % "2.3.2")
addSbtPlugin("io.kevinlee"   % "sbt-docusaur"  % "0.11.0")

addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.11.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.2.0")

val sbtDevOopsVersion = "2.22.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)

addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % sbtDevOopsVersion)
