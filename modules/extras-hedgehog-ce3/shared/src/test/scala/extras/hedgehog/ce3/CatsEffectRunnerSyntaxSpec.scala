package extras.hedgehog.ce3

import cats.effect.IO
import cats.syntax.all._
import cats.{Eq, Show}
import extras.hedgehog.ce3.syntax.runner._
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-12-12
  */
object CatsEffectRunnerSyntaxSpec extends Properties {

  override def tests: List[Test] = List(
    property(
      "test syntax.runner: IO.map(_ ==== expected) - success case",
      testCatsEffectRunnerSuccessCase,
    ),
    property(
      "test syntax.runner and two IO.map(_ ==== expected) - success case",
      testCatsEffectRunnerSuccessCasePair,
    ),
    property(
      "test syntax.runner and multiple IO.map(_ ==== expected) - success case",
      testCatsEffectRunnerSuccessCaseMultiple,
    ),
    property(
      "test syntax.runner and IO.attempt.map(_ ==== Left(expected)) - error case",
      testCatsEffectRunnerErrorCase,
    ),
    property(
      "test syntax.runner and two IO.attempt.map(_ ==== Left(expected)) - error case",
      testCatsEffectRunnerErrorCasePair,
    ),
    property(
      "test syntax.runner and multiple IO.attempt.map(_ ==== Left(expected)) - error case",
      testCatsEffectRunnerErrorCaseMultiple,
    ),
  )

  def testCatsEffectRunnerSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield runIO {

      val expected = n
      val actual   = IO(n)

      actual.map(_ ==== expected)
    }

  def testCatsEffectRunnerSuccessCasePair: Property =
    for {
      n  <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
    } yield runIO {

      val expected  = n
      val expected2 = n2
      val actual    = IO(n)
      val actual2   = IO(n2)

      (
        actual.map(_ ==== expected),
        actual2.map(_ ==== n2),
      ).mapN(_ and _)
    }

  def testCatsEffectRunnerSuccessCaseMultiple: Property =
    for {
      n  <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
      n3 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n3")
      n4 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n4")
      n5 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n5")
    } yield runIO {

      val expected  = n
      val expected2 = n2
      val expected3 = n3
      val expected4 = n4
      val expected5 = n5
      val actual    = IO(n)
      val actual2   = IO(n2)
      val actual3   = IO(n3)
      val actual4   = IO(n4)
      val actual5   = IO(n5)

      List(
        actual.map(_ ==== expected),
        actual2.map(_ ==== expected2),
        actual3.map(_ ==== expected3),
        actual4.map(_ ==== expected4),
        actual5.map(_ ==== expected5),
      ).sequence.map(Result.all)
    }

  def testCatsEffectRunnerErrorCase: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      error   <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .log("error")
    } yield runIO {

      val expected = error
      val actual   = IO.raiseError[Int](error)

      actual.attempt.map(_ ==== expected.asLeft)
    }

  def testCatsEffectRunnerErrorCasePair: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      error   <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .log("error")
      error2  <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .log("error2")
    } yield runIO {

      val expected = error.asLeft[Int].leftWiden[Throwable]
      val actual   = IO.raiseError[Int](error)

      val expected2 = error2.asLeft[Int].leftWiden[Throwable]
      val actual2   = IO.raiseError[Int](error2)

      (
        actual.attempt.map(_ ==== expected),
        actual2.attempt.map(_ ==== expected2),
      ).mapN(_ and _)
    }

  def testCatsEffectRunnerErrorCaseMultiple: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      errors  <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .list(Range.linear(3, 10))
                   .log("errors")
    } yield runIO {

      val expected = errors.map(_.asLeft[Int].leftWiden[Throwable])
      val actual   = errors.map(IO.raiseError[Int](_))

      actual.traverse(_.attempt).map(_ ==== expected)
    }

  sealed abstract class TestError(val message: String) extends RuntimeException(message)
  object TestError {
    final case class SomeTestError(override val message: String) extends TestError(message)
    final case class AnotherTestError(override val message: String) extends TestError(message)

    def anotherTestError(message: String): TestError = AnotherTestError(message)
    def someTestError(message: String): TestError    = SomeTestError(message)

    implicit val testErrorEq: Eq[TestError]     = Eq.fromUniversalEquals
    implicit val testErrorShow: Show[TestError] = Show.fromToString
  }
}
