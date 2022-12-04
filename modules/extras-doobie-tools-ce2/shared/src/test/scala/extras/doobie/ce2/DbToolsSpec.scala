package extras.doobie.ce2

import cats.effect.*
import cats.syntax.all.*
import doobie.syntax.all.toSqlInterpolator
import extras.doobie.RunWithDb
import extras.doobie.ce2.data.{Compat, Example}
import extras.runner.ce2.RunSyncCe2
import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2022-11-27
  */
object DbToolsSpec extends Properties with RunSyncCe2 with RunWithDb with Compat {

  import effectie.instances.ce2.fx.ioFx

  @SuppressWarnings(Array("org.wartremover.warts.GlobalExecutionContext"))
  implicit val cs: ContextShift[F] = IO.contextShift(scala.concurrent.ExecutionContext.global)

  override def tests: List[Test] = List(
    propertyWithDb("test fetchSingleRow - updateSingle - fetchSingleRow", testFetchUpdateFetch),
    propertyWithDb("test fetchMultipleRows - updateMultiple - fetchMultipleRows", testFetchUpdateFetchMultiple),
  )

  def testFetchUpdateFetch(testName: String): Property =
    for {
      example <- Gens.genExample.log("example")
    } yield withDb[F](testName) { transactor =>
      val expectedFetchBefore = none[Example]
      val expectedInsert      = 1
      val expectedFetchAfter  = example.some

      val fetch = DbTools.fetchSingleRow[F][Example](
        sql"""
            SELECT id, name, note
              FROM db_tools_test.example
         """
      )(transactor)

      val insert = DbTools.updateSingle[F](
        sql"""
             INSERT INTO db_tools_test.example (id, name, note) VALUES (${example.id}, ${example.name}, ${example.note})
           """
      )(transactor)

      for {
        fetchResultBefore <- fetch.map(_ ==== expectedFetchBefore)
        insertResult      <- insert.map(_ ==== expectedInsert)
        fetchResultAfter  <- fetch.map(_ ==== expectedFetchAfter)
      } yield Result.all(
        List(
          fetchResultBefore.log("Failed: fetch before"),
          insertResult.log("Failed: insert"),
          fetchResultAfter.log("Failed: fetch after"),
        )
      )
    }

  def testFetchUpdateFetchMultiple(testName: String): Property =
    for {
      examples <- Gens.genExample.list(Range.linear(10, 50)).log("examples")
    } yield withDb[F](testName) { transactor =>
      val expectedFetchBefore = List.empty[Example]
      val expectedInsert      = examples.length
      val expectedFetchAfter  = examples

      val fetch = DbTools.fetchMultipleRows[F][Example](
        sql"""
            SELECT id, name, note
              FROM db_tools_test.example
         """
      )(transactor)

      val insert = DbTools.updateMultiple[F][Example](
        "INSERT INTO db_tools_test.example (id, name, note) VALUES (?, ?, ?)"
      )(examples)(transactor)

      for {
        fetchResultBefore <- fetch.map(_ ==== expectedFetchBefore)
        insertResult      <- insert.map(_ ==== expectedInsert)
        fetchResultAfter  <- fetch.map(_ ==== expectedFetchAfter)
      } yield Result.all(
        List(
          fetchResultBefore.log("Failed: fetch before"),
          insertResult.log("Failed: insert"),
          fetchResultAfter.log("Failed: fetch after"),
        )
      )
    }

}
