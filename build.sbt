name := "akka-streams"

version := "0.1"

scalaVersion := "2.13.3"

val akkaVersion = "2.6.6"
val scalaTestVersion = "3.2.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion
)
