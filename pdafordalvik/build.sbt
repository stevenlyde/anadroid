import com.github.retronym.SbtOneJar._

oneJarSettings

name := "anadroid"

version := "1.0"

scalaVersion := "2.11.7"

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("org.ucombinator.dalvik.cfa.cesk.RunAnalysis")

mainClass in (oneJar) := Some("org.ucombinator.dalvik.cfa.cesk.RunAnalysis")

// fork a new JVM for 'run' and 'test:run'
fork := true

// add a JVM option to use when forking a JVM for 'run'
javaOptions += "-XX:MaxPermSize=512m"

javaOptions += "-Xms1024m"

javaOptions += "-Xmx2048m"

javaOptions += "-Xss1536m"

// only use a single thread for building
parallelExecution := false

exportJars := true

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % "1.3.2",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "com.typesafe.akka" %% "akka-actor" % "2.3.12"
)

