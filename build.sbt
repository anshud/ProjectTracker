import play.Project._

name := "My first application"

version := "1.0"

name := "ProjectTracker"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     


//Just a comment
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.27",
  "com.typesafe.slick" %% "slick" % "2.0.1",
  "net.liftweb" %% "lift-json" % "2.5-RC1",
  "org.scalatest" % "scalatest_2.9.2" % "2.0.M5" % "test",
  "com.typesafe.akka" % "akka-testkit_2.10" % "2.2.0" % "test",
  "org.specs2" %% "specs2" % "1.13" % "test",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

play.Project.playScalaSettings





