package extras.doobie

import cats.effect.*
import cats.syntax.all.*
import effectie.core.*
import effectie.resource.{Ce3UseResource, ReleasableResource, UseResource}
import effectie.syntax.all.*
import extras.tools.*
import hedgehog.*
import hedgehog.runner.*
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import org.typelevel.doobie.implicits.*
import org.typelevel.doobie.util.log.LogHandler
import org.typelevel.doobie.util.transactor.Transactor

import java.nio.file.Files

/** @author Kevin Lee
  * @since 2022-11-27
  */
trait RunWithDb {

  def propertyWithDb(
    name: String,
    stringToProperty: String => Property,
  ): Test =
    property(name, stringToProperty(name)).withTests(count = 1).noShrinking

  def withDb[F[*]: Fx: Async](
    testName: String
  )(test: Transactor[F] => F[Result]): F[Result] = {
    implicit val useResource: UseResource[F] = Ce3UseResource.useResource[F]

    ReleasableResource
      .fromAutoCloseable(effectOf(AutoDeletingFile(Files.createTempDirectory("pg-test").toFile)))
      .use { autoDeletingWorkingDir =>
        val workingDir = autoDeletingWorkingDir.file
        ReleasableResource
          .fromAutoCloseable(
            effectOf(
              EmbeddedPostgres
                .builder()
                .setOverrideWorkingDirectory(workingDir)
                .setCleanDataDirectory(true)
                .start()
            )
          )
          .use { postgres =>
            val user     = "postgres"
            val password = "postgres"
            val url      = postgres.getJdbcUrl(user, password)
            for {
              transactor <- effectOf(
                              Transactor.fromDriverManager[F](
                                "org.postgresql.Driver",
                                url,
                                user,
                                password,
                                LogHandler.jdkLogHandler[F].some,
                              )
                            )

              _ <- sql"""CREATE SCHEMA IF NOT EXISTS db_tools_test""".update.run.transact(transactor)
              _ <- sql"""
                       CREATE TABLE IF NOT EXISTS db_tools_test.example
                       (
                           id SERIAL PRIMARY KEY,
                           name TEXT NOT NULL,
                           note TEXT NULL
                       )
                     """.update.run.transact(transactor)

              result <- test(transactor)
                          .handleNonFatal { err =>
                            Result
                              .failure
                              .log(
                                s""""$testName": Test failed due to error
                                      |> ERROR: ${err.getMessage.split("\n").mkString("\n>      ")}
                                      |> Type: ${err.getClass.getName}
                                      |> StackTrace: ${err.stackTraceString}
                                      |""".stripMargin
                              )
                          }
            } yield result
          }
      }
  }

}
