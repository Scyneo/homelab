name := "laby9"
version := "0.0.1"

scalaVersion := "3.5.2"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-explain",
  "-explain-types",
  "-source:3.3",
  "-encoding", "utf8"
)

libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.5.8"
)

