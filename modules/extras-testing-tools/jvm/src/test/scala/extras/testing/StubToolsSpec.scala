package extras.testing

import hedgehog._
import hedgehog.runner._

import scala.util.Try

/** @author Kevin Lee
  * @since 2023-02-08
  */
object StubToolsSpec extends Properties {
  override def tests: List[Test] = List(
    property("test StubTools.missing - success case", testMissing),
    example("test StubTools.missing - missing case", testMissingMissingCase),
    property("test StubTools.missing - success case (2)", testMissing2),
    example("test StubTools.missing - missing case (2)", testMissingMissingCase2),
  )

  def testMissing: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      val testType = TestTypeStub(Option(f))
      val actual   = testType.foo(n)
      actual ==== expected
    }

  def testMissingMissingCase: Result = {

    val testType = TestTypeStub(None)
    Try(testType.foo(1))
      .fold(
        {
          case err: StubTools.MissingStubException[_] =>
            val message = err.getMessage
            message.split("\n").take(3).toList match {
              case first :: second :: third :: Nil =>
                val thirdRegex1  = "^>>   extras.testing.TestTypeStub\\$"
                val thirdRegex2  = "TestTypeStub\\$\\$anon\\$1"
                val thirdRegex3a = "\\$foo\\$"
                val thirdRegex3b = "\\.foo\\("
                val thirdRegex4  = "\\(TestType\\.scala:11\\)$"

                Result
                  .all(
                    List(
                      first ==== "",
                      second ==== ">> Missing Stub implementation at",
                      Result
                        .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex1]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                      Result
                        .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex2]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                      (Result
                        .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex3a]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        )
                        .or(
                          Result
                            .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                            .log(
                              s"""[thirdRegex3b]
                                 |ThirdLine mismatch:
                                 |  regex: $thirdRegex3b
                                 |  third: $third""".stripMargin
                            )
                        )),
                      Result
                        .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex4]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                    )
                  )
                  .log(s"Error message mismatch. message: $message")
              case _ =>
                Result
                  .failure
                  .log(s"Error message mismatch. message: $message")
            }

          case err =>
            import extras.tools._
            Result
              .failure
              .log(
                s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                 |${err.stackTraceString}
                 |""".stripMargin
              )
        },
        result =>
          Result
            .failure
            .log(
              s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
            ),
      )

  }

  def testMissing2: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      val testType = TestTypeStub2(Option(f))
      val actual   = testType.bar(n)
      actual ==== expected
    }

  def testMissingMissingCase2: Result = {

    val testType = TestTypeStub2(None)
    Try(testType.bar(1))
      .fold(
        {
          case err: StubTools.MissingStubException[_] =>
            val message = err.getMessage
            message.split("\n").take(3).toList match {
              case first :: second :: third :: Nil =>
                val thirdRegex1  = "^>>   extras.testing.TestTypeStub2\\$"
                val thirdRegex2  = "TestTypeStub2\\$\\$anon\\$1"
                val thirdRegex3a = "\\$bar\\$"
                val thirdRegex3b = "\\.bar\\("
                val thirdRegex4  = "\\(TestType2\\.scala:11\\)$"

                Result
                  .all(
                    List(
                      first ==== "",
                      second ==== ">> Missing Stub implementation at",
                      Result
                        .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex1]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                      Result
                        .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex2]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                      (Result
                        .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex3a]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        )
                        .or(
                          Result
                            .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                            .log(
                              s"""[thirdRegex3b]
                                 |ThirdLine mismatch:
                                 |  regex: $thirdRegex3b
                                 |  third: $third""".stripMargin
                            )
                        )),
                      Result
                        .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                        .log(
                          s"""[thirdRegex4]
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third
                             |---
                             |The whole message:
                             |$message
                             |""".stripMargin
                        ),
                    )
                  )
                  .log(s"Error message mismatch. message: $message")
              case _ =>
                Result
                  .failure
                  .log(s"Error message mismatch. message: $message")
            }

          case err =>
            import extras.tools._
            Result
              .failure
              .log(
                s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                 |${err.stackTraceString}
                 |""".stripMargin
              )
        },
        result =>
          Result
            .failure
            .log(
              s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
            ),
      )

  }

}
