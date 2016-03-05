name := "AkkaScala"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT",
  "com.typesafe.akka" %% "akka-cluster" % "2.4-SNAPSHOT"
)

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"



lazy val application = (project in file("application"))

lazy val cluster = (project in file("cluster"))



lazy val root = (project in file("."))
               .aggregate(application, cluster)