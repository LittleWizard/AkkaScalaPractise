name := "Akka Application"

version := "1.0"

scalaVersion := "2.11.7"


val akkaVersion  = "2.4.2"

val sprayVersion = "1.3.3"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "io.spray" %% "spray-can" % sprayVersion,
  "io.spray" %%  "spray-routing" % sprayVersion,
  "io.spray" %%  "spray-json" % "1.3.2"
)
