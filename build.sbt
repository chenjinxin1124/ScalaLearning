name in Global := "simple"
organization in Global := "simple"
scalaVersion in Global := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.1",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1",
  "org.postgresql" % "postgresql" % "42.2.5", //org.postgresql.ds.PGSimpleDataSource dependency
  "com.typesafe.slick" %% "slick-codegen" % "3.3.1"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.9"
  , "com.typesafe.akka" %% "akka-stream" % "2.5.23" // or whatever the latest version is
  , "com.typesafe.akka" %% "akka-actor" % "2.5.23"
  , "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9"
)

scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.7.0"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion
)
libraryDependencies += "io.monix" %% "monix" % "3.0.0-RC3"

val circeV = "0.11.1"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeV
  , "io.circe" %% "circe-generic" % circeV
  , "io.circe" %% "circe-parser" % circeV
  , "io.circe" %% "circe-generic-extras" % circeV
)
