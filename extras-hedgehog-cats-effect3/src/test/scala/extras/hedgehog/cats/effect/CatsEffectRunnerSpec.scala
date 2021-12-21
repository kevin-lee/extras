package extras.hedgehog.cats.effect

import cats.Eq
import cats.effect.IO
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-12-12
  */
object CatsEffectRunnerSpec extends Properties {

  override def tests: List[Test] = List(
    property("test CatsEffectRunner and IO.completeAs", testCatsEffectRunnerWithCompleteAs),
    property("test CatsEffectRunner and IO.completeThen", testCatsEffectRunnerWithCompleteThen),
    property("test CatsEffectRunner and IO.expectError", testCatsEffectRunnerWithExpectError),
    property("test CatsEffectRunner and IO.errorThen", testCatsEffectRunnerWithErrorThen)
  )

  def testCatsEffectRunnerWithCompleteAs: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield {
    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = n
    val actual   = IO(n)

    actual.completeAs(expected)
  }

  def testCatsEffectRunnerWithCompleteThen: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield {
    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = n
    val actual   = IO(n)

    actual.completeThen { actual =>
      actual ==== expected
    }
  }

  def testCatsEffectRunnerWithExpectError: Property = for {
    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
    error   <- Gen
                 .element1(
                   TestError.someTestError(s"Don't worry it's only a test error. $message"),
                   TestError.anotherTestError(s"Don't worry it's only a test error. $message")
                 )
                 .log("error")
  } yield {
    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = error
    val actual   = IO.raiseError[Int](error)

    actual.expectError(expected)
  }

  def testCatsEffectRunnerWithErrorThen: Property = for {
    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
    error   <- Gen
                 .element1(
                   TestError.someTestError(s"Don't worry it's only a test error. $message"),
                   TestError.anotherTestError(s"Don't worry it's only a test error. $message")
                 )
                 .log("error")
  } yield {
    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = error
    val actual   = IO.raiseError[Int](error)

    actual.errorThen { actual =>
      actual ==== expected
    }
  }

  sealed abstract class TestError(val message: String) extends RuntimeException(message)
  object TestError {
    final case class SomeTestError(override val message: String) extends TestError(message)
    final case class AnotherTestError(override val message: String) extends TestError(message)

    def anotherTestError(message: String): TestError = AnotherTestError(message)
    def someTestError(message: String): TestError    = SomeTestError(message)

    implicit val testErrorEq: Eq[TestError] = Eq.fromUniversalEquals
  }
}
