name in Global := "Interview"
organization in Global := "Interview"
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
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.7"
//testOptions in Test += Tests.Argument("-oI") 只显示失败
libraryDependencies += "org.tpolecat" % "doobie-scalatest_2.11" % "0.4.0"
libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "0.7.0"
libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "0.7.0"
libraryDependencies += "net.postgis" % "postgis-jdbc" % "2.3.0"

libraryDependencies += "org.tpolecat" %% "doobie-quill" % "0.7.0"
libraryDependencies ++= Seq("org.tpolecat" %% "doobie-quill" % "0.7.0", "io.getquill" %% "quill-sql" % "3.1.0", "io.getquill" %% "quill-jdbc" % "3.1.0")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-laws" % "2.0.0" % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3" % Test
)

libraryDependencies += "joda-time" % "joda-time" % "2.1"

libraryDependencies += "org.joda" % "joda-convert" % "1.3"

scalacOptions += "-Ypartial-unification"