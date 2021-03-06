name := "Messaging Application"

version := "1.0"

scalaVersion := "2.11.7"


val akkaVersion  = "2.4.2"

val sprayVersion = "1.3.3"

val springVersion = "4.2.1.RELEASE"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "org.iq80.leveldb"            % "leveldb"          % "0.7",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",
  "io.spray" %% "spray-can" % sprayVersion,
  "io.spray" %%  "spray-routing" % sprayVersion,
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.springframework" % "spring-context" % springVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "org.springframework.data" % "spring-data-cassandra" % "1.3.4.RELEASE",
  "javax.inject" % "javax.inject" % "1"
)
