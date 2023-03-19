package extras.doobie.newtype

import cats.effect.ContextShift
import cats.syntax.all._
import doobie.syntax.all._
import extras.doobie.RunWithDb
import extras.doobie.ce2.DbTools
import extras.doobie.newtype.data.Example
import extras.runner.ce2.RunSyncCe2
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-03-19
  */
object newtypeSpec extends Properties with RunSyncCe2 with RunWithDb {

  override def tests: List[Test] = List(
    propertyWithDb("test doobie.newtype by fetching and updating data", testFetchUpdateFetch)
  )

  import effectie.instances.ce2.fx._

  @SuppressWarnings(Array("org.wartremover.warts.GlobalExecutionContext"))
  implicit val cs: ContextShift[F] = F.contextShift(scala.concurrent.ExecutionContext.global)

  def testFetchUpdateFetch(testName: String): Property =
    for {
      example <- genExample.log("example")
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

  def genExample: Gen[Example] = for {
    id   <- Gen.int(Range.linear(1, Int.MaxValue)).map(Example.Id(_))
    name <- Gen.string(Gen.alphaNum, Range.linear(5, 20)).map(Example.Name(_))
    note <- Gen.string(Gen.alphaNum, Range.linear(5, 20)).map(Example.Note(_))
  } yield Example(id, name, note)
}
