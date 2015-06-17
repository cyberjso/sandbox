name := """hello-esper"""

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies += "com.espertech" % "esper" % "5.2.0"

resolvers += "Secured Central Repository" at "https://repo1.maven.org/maven2"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)