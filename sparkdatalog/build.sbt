name := "sparkDatalog"

version := "1.2.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq()

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.2.1"


resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.1.4" % "test"

libraryDependencies += "org.scalamock" %% "scalamock-core" % "3.1.4"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "net.sf.squirrel-sql.thirdparty-non-maven" % "java-cup" % "0.11a"

libraryDependencies += "org.jgrapht" % "jgrapht-core" % "0.9.1"

libraryDependencies += "org.deri.iris" % "iris-parser" % "0.61" from "http://www.iris-reasoner.org/snapshot/iris-parser-0.61.jar"

mainClass in (Compile, run) := Some("pl.appsilon.marek.sparkdatalogexample.PathsOrdinaryPerfTest")
