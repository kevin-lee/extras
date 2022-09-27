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
      "test CatsEffectRunner syntax: IO.map(_ ==== expected) - success case",
      testCatsEffectRunnerSuccessCase
    ),
    property(
      "test CatsEffectRunner syntax: IO.attempt.map(_ ==== Left(expected)) - error case",
      testCatsEffectRunnerErrorCase
    ),
  )

  def testCatsEffectRunnerSuccessCase: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield withIO {

      val expected = n
      val actual   = IO(n)

      actual.map(_ ==== expected)
    }

  def testCatsEffectRunnerErrorCase: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      error   <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message")
                   )
                   .log("error")
    } yield withIO {

      val expected = error
      val actual   = IO.raiseError[Int](error)

      actual.attempt.map(_ ==== expected.asLeft)
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
