package extras.testing

import cats.effect.IO
import cats.syntax.all._
import hedgehog._
import hedgehog.runner._

import scala.util.Try

/** @author Kevin Lee
  * @since 2023-02-09
  */
object StubToolsCatsSpec extends Properties {
  override def tests: List[Test] = List(
    property("test StubToolsCats.stub(Int => Int) with IO - success case", testStubIntToIntWithIOSuccessCase),
    property("test StubToolsCats.stub(Int => F[Int]) with IO - success case", testStubIntToFIntWithIOSuccessCase),
    property("test StubToolsCats.stub(Int => Int) with IO - failure case", testStubIntToIntWithIOFailureCase),
    property("test StubToolsCats.stub(Int => F[Int]) with IO - failure case", testStubIntToFIntWithIOFailureCase),
    property("test StubToolsCats.stub(Int => Int) with Try - success case", testStubIntToIntWithTrySuccessCase),
    property("test StubToolsCats.stub(Int => F[Int]) with Try - success case", testStubIntToFIntWithTrySuccessCase),
    property("test StubToolsCats.stub(Int => Int) with Try - failure case", testStubIntToIntWithTryFailureCase),
    property("test StubToolsCats.stub(Int => F[Int]) with Try - failure case", testStubIntToFIntWithTryFailureCase),
  )

  def testStubIntToIntWithIOSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      val testType = TestTypeStub[IO](f.some, none)
      testType
        .foo(n)
        .attempt
        .map {
          case Right(actual) =>
            actual ==== expected
          case Left(err) =>
            Result.failure.log(s"Failed with error: ${err.toString}")
        }
        .unsafeRunSync()
    }

  def testStubIntToFIntWithIOSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (n: Int) => IO.pure(n * 2)

      val expected = f(n)

      val testType = TestTypeStub[IO](none, f.some)
      testType
        .bar(n)
        .attempt
        .flatMap {
          case Right(actual) =>
            expected.map { expected =>
              actual ==== expected
            }
          case Left(err) =>
            IO.pure(Result.failure.log(s"Failed with error: ${err.toString}"))
        }
        .unsafeRunSync()
    }

  def testStubIntToIntWithIOFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (n: Int) => IO.pure(n * 2)

      val testType = TestTypeStub[IO](none, f.some)
      testType
        .foo(n)
        .attempt
        .map {
          case Right(result) =>
            Result
              .failure
              .log(
                s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
              )
          case Left(err: StubTools.MissingStubException[_]) =>
            val message = err.getMessage
            message.split("\n").take(3).toList match {
              case first :: second :: third :: Nil =>
                val thirdRegex1  = "^>>   extras.testing.TestTypeStub\\$"
                val thirdRegex2  = "TestTypeStub\\$\\$anon\\$1"
                val thirdRegex3a = "\\$foo\\$"
                val thirdRegex3b = "\\.foo\\("
                val thirdRegex4  = "\\(TestType\\.scala:16\\)$"
                Result
                  .all(
                    List(
                      first ==== "",
                      second ==== ">> Missing Stub implementation at",
                      Result
                        .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third""".stripMargin
                        ),
                      Result
                        .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third""".stripMargin
                        ),
                      (Result
                        .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third""".stripMargin
                        )
                        .or(
                          Result
                            .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                            .log(
                              s"""
                               |ThirdLine mismatch:
                               |  regex: $thirdRegex3b
                               |  third: $third""".stripMargin
                            )
                        )),
                      Result
                        .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third""".stripMargin
                        ),
                    )
                  )
                  .log(s"Error message mismatch. message: $message")
              case _ =>
                Result
                  .failure
                  .log(s"Error message mismatch. message: $message")
            }
          case Left(err) =>
            import extras.tools._
            Result
              .failure
              .log(
                s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                   |${err.stackTraceString}
                   |""".stripMargin
              )
        }
        .unsafeRunSync()
    }

  def testStubIntToFIntWithIOFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val testType = TestTypeStub[IO](f.some, none)
      testType
        .bar(n)
        .attempt
        .map {
          case Right(result) =>
            Result
              .failure
              .log(
                s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
              )
          case Left(err: StubTools.MissingStubException[_]) =>
            val message = err.getMessage
            message.split("\n").take(3).toList match {
              case first :: second :: third :: Nil =>
                val thirdRegex1  = "^>>   extras.testing.TestTypeStub\\$"
                val thirdRegex2  = "TestTypeStub\\$\\$anon\\$1"
                val thirdRegex3a = "\\$bar\\$"
                val thirdRegex3b = "\\.bar\\("
                val thirdRegex4  = "\\(TestType\\.scala:19\\)$"
                Result
                  .all(
                    List(
                      first ==== "",
                      second ==== ">> Missing Stub implementation at",
                      Result
                        .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third""".stripMargin
                        ),
                      Result
                        .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third""".stripMargin
                        ),
                      (Result
                        .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third""".stripMargin
                        )
                        .or(
                          Result
                            .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                            .log(
                              s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3b
                             |  third: $third""".stripMargin
                            )
                        )),
                      Result
                        .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                        .log(
                          s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third""".stripMargin
                        ),
                    )
                  )
                  .log(s"Error message mismatch. message: $message")
              case _ =>
                Result
                  .failure
                  .log(s"Error message mismatch. message: $message")
            }
          case Left(err) =>
            import extras.tools._
            Result
              .failure
              .log(
                s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                   |${err.stackTraceString}
                   |""".stripMargin
              )
        }
        .unsafeRunSync()

    }

  def testStubIntToIntWithTrySuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      val testType = TestTypeStub[Try](f.some, none)
      testType
        .foo(n)
        .toEither match {
        case Right(actual) =>
          actual ==== expected
        case Left(err) =>
          Result.failure.log(s"Failed with error: ${err.toString}")
      }
    }

  def testStubIntToFIntWithTrySuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (n: Int) => Try(n * 2)

      val expected = f(n)

      val testType = TestTypeStub[Try](none, f.some)
      testType
        .bar(n)
        .toEither
        .flatMap { actual =>
          expected.map { expected =>
            actual ==== expected
          }.toEither

        } match {
        case Right(result) =>
          result
        case Left(err) =>
          Result.failure.log(s"Failed with error: ${err.toString}")
      }
    }

  def testStubIntToIntWithTryFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (n: Int) => Try(n * 2)

      val testType = TestTypeStub[Try](none, f.some)
      testType
        .foo(n)
        .toEither match {
        case Right(result) =>
          Result
            .failure
            .log(
              s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
            )
        case Left(err: StubTools.MissingStubException[_]) =>
          val message = err.getMessage
          message.split("\n").take(3).toList match {
            case first :: second :: third :: Nil =>
              val thirdRegex1  = "^>>   extras.testing.TestTypeStub\\$"
              val thirdRegex2  = "TestTypeStub\\$\\$anon\\$1"
              val thirdRegex3a = "\\$foo\\$"
              val thirdRegex3b = "\\.foo\\("
              val thirdRegex4  = "\\(TestType\\.scala:16\\)$"
              Result
                .all(
                  List(
                    first ==== "",
                    second ==== ">> Missing Stub implementation at",
                    Result
                      .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third""".stripMargin
                      ),
                    Result
                      .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third""".stripMargin
                      ),
                    (Result
                      .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third""".stripMargin
                      )
                      .or(
                        Result
                          .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                          .log(
                            s"""
                               |ThirdLine mismatch:
                               |  regex: $thirdRegex3b
                               |  third: $third""".stripMargin
                          )
                      )),
                    Result
                      .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third""".stripMargin
                      ),
                  )
                )
                .log(s"Error message mismatch. message: $message")
            case _ =>
              Result
                .failure
                .log(s"Error message mismatch. message: $message")
          }
        case Left(err) =>
          import extras.tools._
          Result
            .failure
            .log(
              s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                   |${err.stackTraceString}
                   |""".stripMargin
            )
      }
    }

  def testStubIntToFIntWithTryFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val testType = TestTypeStub[Try](f.some, none)
      testType
        .bar(n)
        .toEither match {
        case Right(result) =>
          Result
            .failure
            .log(
              s"Expected StubTools.MissingStubException but got success with ${result.toString} instead"
            )
        case Left(err: StubTools.MissingStubException[_]) =>
          val message = err.getMessage
          message.split("\n").take(3).toList match {
            case first :: second :: third :: Nil =>
              val thirdRegex1  = "^>>   extras.testing.TestTypeStub\\$"
              val thirdRegex2  = "TestTypeStub\\$\\$anon\\$1"
              val thirdRegex3a = "\\$bar\\$"
              val thirdRegex3b = "\\.bar\\("
              val thirdRegex4  = "\\(TestType\\.scala:19\\)$"
              Result
                .all(
                  List(
                    first ==== "",
                    second ==== ">> Missing Stub implementation at",
                    Result
                      .assert(thirdRegex1.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex1
                             |  third: $third""".stripMargin
                      ),
                    Result
                      .assert(thirdRegex2.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex2
                             |  third: $third""".stripMargin
                      ),
                    (Result
                      .assert(thirdRegex3a.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3a
                             |  third: $third""".stripMargin
                      )
                      .or(
                        Result
                          .assert(thirdRegex3b.r.findFirstIn(third).isDefined)
                          .log(
                            s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex3b
                             |  third: $third""".stripMargin
                          )
                      )),
                    Result
                      .assert(thirdRegex4.r.findFirstIn(third).isDefined)
                      .log(
                        s"""
                             |ThirdLine mismatch:
                             |  regex: $thirdRegex4
                             |  third: $third""".stripMargin
                      ),
                  )
                )
                .log(s"Error message mismatch. message: $message")
            case _ =>
              Result
                .failure
                .log(s"Error message mismatch. message: $message")
          }
        case Left(err) =>
          import extras.tools._
          Result
            .failure
            .log(
              s"""Expected StubTools.MissingStubException but got ${err.getClass.getName} instead
                   |${err.stackTraceString}
                   |""".stripMargin
            )
      }

    }

}
