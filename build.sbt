
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "hyps-lang"

publishTo in ThisBuild := Some(Resolver.file("Unused transient repository"))

scalacOptions in ThisBuild ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", // Provide explicit encoding (the next line)
  "utf-8", // Specify character encoding used by Scala source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xmacro-settings:-logging@org.enso", // Disable the debug logging globally.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Xfatal-warnings" // Make warnings fatal so they don't make it onto main (use @nowarn for local suppression)
)

// ============================================================================
// === Global Project =========================================================
// ============================================================================


// === Circe ==================================================================

val circeVersion = "0.14.0-M7"

val circe = Seq("circe-core", "circe-generic", "circe-parser", "circe-literal")
  .map("io.circe" %% _ % circeVersion)

// === Azure ==================================================================

// === Other ==================================================================

val logbackVersion = "1.2.3"
val scalaLoggingVersion = "3.9.2"
val zioVersion = "1.0.3"
val scalaGuiceVersion = "4.2.11"
val scalaTestVersion = "3.2.2"
val commonsIoVersion = "2.6"
val commonsLangVersion = "3.5"
val enumeratumVersion = "1.5.15"
val enumeratumCirceVersion = "1.5.22"
val pureconfigVersion = "0.12.2"
val typesafeConfigVersion = "1.4.0"
val shapelessVersion = "2.3.3"
val kindProjectorVersion = "0.11.0"
val mockitoVersion = "1.10.19"
val auth0JwksVersion = "0.14.1"
val auth0JwtVersion = "3.11.0"
val wiremockVersion = "2.27.2"
val circeYamlVersion = "0.13.1"
val bumpVersion = "0.1.3"
val curatorVersion = "5.1.0"
val bouncycastleVersion = "1.68"
val commonsLang3Version = "3.12.0"
val wireMockVersion = "2.27.2"
val stripeVersion = "20.62.0"
val engineScalatestVersion = "3.3.0-SNAP2"
val parserCombinatorsVersion = "2.1.0"

lazy val `hyps-lang` = (project in file("."))
  .settings(
    version := "0.1.0",
    libraryDependencies ++= circe,
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.typelevel" %% "cats-effect" % "3.2.9",
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % kindProjectorVersion cross CrossVersion.full
    )
  )
  .settings(
    publishArtifact in packageDoc := false,
    publish / skip := true,
    sources in(Docker, doc) := Seq.empty
  )
  .enablePlugins(DockerPlugin, JavaAppPackaging)
