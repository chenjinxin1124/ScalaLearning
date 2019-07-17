package slick.code

//#imports
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import slick.jdbc.JdbcBackend.Database
//#imports

object CodeGenerator extends App {
  val profile = "slick.jdbc.PostgresProfile"
  val jdbcDriver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost:5432/exampledb"
  val outputFolder = "./src/main/scala/slick/"
  val pkg = "tables"
  val user = "dbuser"
  val password = "cjx123"
  val outputToMultipleFiles = true

  val db = Database.forConfig("mydb")
  slick.codegen.SourceCodeGenerator.main(
    Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
  )
}
