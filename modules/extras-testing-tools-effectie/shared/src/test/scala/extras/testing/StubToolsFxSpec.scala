package extras.testing

import cats.effect.IO
import cats.syntax.all._
import hedgehog._
import hedgehog.runner._
import effectie.syntax.all._
import extras.concurrent.testing.ConcurrentSupport
import extras.concurrent.testing.types.{ErrorLogger, WaitFor}

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent._
import scala.concurrent.duration._

/** @author Kevin Lee
  * @since 2023-02-09
  */
object StubToolsFxSpec extends Properties {
  override def tests: List[Test] = List(
    property("test StubToolsFx.stub(Int => Int) with IO - success case", testStubIntToIntWithIOSuccessCase),
    property("test StubToolsFx.stub(Int => F[Int]) with IO - success case", testStubIntToFIntWithIOSuccessCase),
    property("test StubToolsFx.stub(Int => Int) with IO - failure case", testStubIntToIntWithIOFailureCase),
    property("test StubToolsFx.stub(Int => F[Int]) with IO - failure case", testStubIntToFIntWithIOFailureCase),
    property("test StubToolsFx.stub(Int => Int) with Future - success case", testStubIntToIntWithFutureSuccessCase),
    property("test StubToolsFx.stub(Int => F[Int]) with Future - success case", testStubIntToFIntWithFutureSuccessCase),
    property("test StubToolsFx.stub(Int => Int) with Future - failure case", testStubIntToIntWithFutureFailureCase),
    property("test StubToolsFx.stub(Int => F[Int]) with Future - failure case", testStubIntToFIntWithFutureFailureCase),
  )

  def testStubIntToIntWithIOSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      import effectie.instances.ce2.fx.ioFx
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

      import effectie.instances.ce2.fx.ioFx
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

      import effectie.instances.ce2.fx.ioFx
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
                val thirdRegex4  = "\\(TestType\\.scala:17\\)$"
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

      import effectie.instances.ce2.fx.ioFx
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
                val thirdRegex4  = "\\(TestType\\.scala:20\\)$"
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

  val waitFor: WaitFor                                     = WaitFor(500.milliseconds)
  implicit private val errorLogger: ErrorLogger[Throwable] = ErrorLogger.printlnDefaultErrorLogger

  def testStubIntToIntWithFutureSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      val expected = f(n)

      import effectie.instances.future.fx.futureFx

      implicit val executorService: ExecutorService = Executors.newFixedThreadPool(1)
      implicit val ec: ExecutionContext             =
        ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

      val testType = TestTypeStub[Future](f.some, none)
      ConcurrentSupport.futureToValueAndTerminate(
        executorService,
        waitFor,
      )(
        testType
          .foo(n)
          .catchNonFatalThrowable
      ) match {
        case Right(actual) =>
          actual ==== expected
        case Left(err) =>
          Result.failure.log(s"Failed with error: ${err.toString}")
      }
    }

  def testStubIntToFIntWithFutureSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      import effectie.instances.future.fx._

      implicit val executorService: ExecutorService = Executors.newFixedThreadPool(1)
      implicit val ec: ExecutionContext             =
        ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

      val innerF = (n: Int) => n * 2
      val f      = (n: Int) => Future(innerF(n))

      val expected = innerF(n)

      val testType = TestTypeStub[Future](none, f.some)
      ConcurrentSupport.futureToValueAndTerminate(
        executorService,
        waitFor,
      )(
        testType
          .bar(n)
          .catchNonFatalThrowable
          .map {
            case Right(actual) =>
              actual ==== expected
            case Left(err) =>
              Result.failure.log(s"Failed with error: ${err.toString}")
          }
      )
    }

  def testStubIntToIntWithFutureFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {

      implicit val executorService: ExecutorService = Executors.newFixedThreadPool(1)
      implicit val ec: ExecutionContext             =
        ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

      val f = (n: Int) => Future(n * 2)

      import effectie.instances.future.fx._

      val testType = TestTypeStub[Future](none, f.some)
      ConcurrentSupport.futureToValueAndTerminate(
        executorService,
        waitFor,
      )(
        testType
          .foo(n)
          .catchNonFatalThrowable
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
                  val thirdRegex4  = "\\(TestType\\.scala:17\\)$"
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
      )
    }

  def testStubIntToFIntWithFutureFailureCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val f = (_: Int) * 2

      import effectie.instances.future.fx.futureFx

      implicit val executorService: ExecutorService = Executors.newFixedThreadPool(1)
      implicit val ec: ExecutionContext             =
        ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

      val testType = TestTypeStub[Future](f.some, none)

      ConcurrentSupport.futureToValueAndTerminate(
        executorService,
        waitFor,
      )(
        testType
          .bar(n)
          .catchNonFatalThrowable
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
                  val thirdRegex4  = "\\(TestType\\.scala:20\\)$"
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
      )

    }

}
