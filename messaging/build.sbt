name := "messaging Application"

version := "1.0"

scalaVersion := "2.11.7"


val akkaVersion  = "2.4.2"

val sprayVersion = "1.3.3"

val springVersion = "4.2.1.RELEASE"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "io.spray" %% "spray-can" % sprayVersion,
  "io.spray" %%  "spray-routing" % sprayVersion,
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.springframework" % "spring-context" % springVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion
)