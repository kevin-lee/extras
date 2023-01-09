package extras.doobie

import cats.effect.*
import cats.syntax.all.*
import doobie.implicits.*
import doobie.util.transactor.Transactor
import effectie.core.*
import effectie.resource.Ce3Resource
import effectie.syntax.all.*
import extras.tools.*
import hedgehog.*
import hedgehog.runner.*
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres

import java.nio.file.Files

/** @author Kevin Lee
  * @since 2022-11-27
  */
trait RunWithDb {

  def propertyWithDb[F[*]](
    name: String,
    stringToProperty: String => Property,
  ): Test =
    property(name, stringToProperty(name)).withTests(count = 1).noShrinking

  def withDb[F[*]: Fx: Async](
    testName: String
  )(test: Transactor[F] => F[Result]): F[Result] = {
    Ce3Resource
      .fromAutoCloseable(effectOf(AutoDeletingFile(Files.createTempDirectory("pg-test").toFile)))
      .use { autoDeletingWorkingDir =>
        val workingDir = autoDeletingWorkingDir.file
        Ce3Resource
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
