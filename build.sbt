
name := "lf-lms"
organization in ThisBuild := "com.lf"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"

lazy val `lms` = (project in file(".")).aggregate(`lms-api`, `lms-impl`)

lazy val `lms-api` = (project in file("lms-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
lazy val `lms-impl` = (project in file("lms-impl")).enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceJdbc,
      lagomScaladslTestKit,
      macwire,
      scalaTest, slick,
      "com.loanframe" % "lf-data-models_2.12" % "1.0.0-SNAPSHOT"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .settings(
    resolvers += "Nexus" at s"$nexus_url/repository/lf-repo/",
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )
  .dependsOn(`lms-api`)

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
val slick = "com.typesafe.slick" %% "slick" % "3.2.1"
val nexus_url = "https://staging-repo.loanframe.com/"

//Define the external service’s host and port name.
lagomUnmanagedServices in ThisBuild := Map("lms-service" -> "http://13.126.58.168/api")
lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false
