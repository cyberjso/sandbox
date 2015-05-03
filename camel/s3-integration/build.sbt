name := """s3-integration"""

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.apache.camel" % "camel-core" % "2.15.0",
  "org.apache.camel" % "camel-aws" % "2.15.0",
  "org.apache.camel" % "camel-spring-javaconfig" % "2.15.0",
  "org.apache.camel" % "camel-test-spring" % "2.15.0",
  "commons-io" % "commons-io" % "2.4"
)

mainClass := Some("com.example.Main")