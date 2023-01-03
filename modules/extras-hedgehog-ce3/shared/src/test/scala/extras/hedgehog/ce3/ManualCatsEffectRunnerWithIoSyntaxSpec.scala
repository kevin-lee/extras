package extras.hedgehog.ce3

import cats.effect.IO
import cats.{Eq, Show}
import extras.hedgehog.ce3.syntax.runner._
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-12-12
  */
object ManualCatsEffectRunnerWithIoSyntaxSpec extends Properties {

  override def tests: List[Test] = List(
    property("test syntax.runner and IO.completeAs", testCatsEffectRunnerWithCompleteAs),
    property("test syntax.runner and two IO.completeAs", testCatsEffectRunnerWithCompleteAsPair),
    property("test syntax.runner and multiple IO.completeAs", testCatsEffectRunnerWithCompleteAsMultiple),
    property("test syntax.runner and IO.completeThen", testCatsEffectRunnerWithCompleteThen),
    property("test syntax.runner and two IO.completeThen", testCatsEffectRunnerWithCompleteThenPair),
    property("test syntax.runner and multiple IO.completeThen", testCatsEffectRunnerWithCompleteThenMultiple),
    property("test syntax.runner and IO.expectError", testCatsEffectRunnerWithExpectError),
    property("test syntax.runner and two IO.expectError", testCatsEffectRunnerWithExpectErrorPair),
    property("test syntax.runner and multiple IO.expectError", testCatsEffectRunnerWithExpectErrorMultiple),
    property("test syntax.runner and IO.errorThen", testCatsEffectRunnerWithErrorThen),
    property("test syntax.runner and two IO.errorThen", testCatsEffectRunnerWithErrorThenPair),
    property("test syntax.runner and multiple IO.errorThen", testCatsEffectRunnerWithErrorThenMultiple),
  )

  def testCatsEffectRunnerWithCompleteAs: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield withIO { implicit ticker =>

      val expected = n
      val actual   = IO(n)

      actual.completeAs(expected)
    }

  def testCatsEffectRunnerWithCompleteAsPair: Property =
    for {
      n  <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
    } yield withIO { implicit ticker =>

      val expected  = n
      val expected2 = n2
      val actual    = IO(n)
      val actual2   = IO(n2)

      actual.completeAs(expected).and(actual2.completeAs(expected2))
    }

  def testCatsEffectRunnerWithCompleteAsMultiple: Property =
    for {
      ns <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).list(Range.linear(3, 10)).log("ns")
    } yield withIO { implicit ticker =>

      val expected = ns
      val actual   = ns.map(IO(_))

      Result.all(
        actual.zipWithIndex.map {
          case (ioN, index) =>
            ioN.completeAs(expected(index))
        }
      )
    }

  def testCatsEffectRunnerWithCompleteThen: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield withIO { implicit ticker =>

      val expected = n
      val actual   = IO(n)

      actual.completeThen { actual =>
        actual ==== expected
      }
    }

  def testCatsEffectRunnerWithCompleteThenPair: Property =
    for {
      n  <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
    } yield withIO { implicit ticker =>

      val expected  = n
      val expected2 = n2

      val actual  = IO(n)
      val actual2 = IO(n2)

      actual
        .completeThen { actual =>
          actual ==== expected
        }
        .and(actual2.completeThen { actual =>
          actual ==== expected2
        })
    }

  def testCatsEffectRunnerWithCompleteThenMultiple: Property =
    for {
      ns <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).list(Range.linear(3, 10)).log("ns")
    } yield withIO { implicit ticker =>

      val expected = ns
      val actual   = ns.map(IO(_))

      Result.all(
        actual.zipWithIndex.map {
          case (ioN, index) =>
            ioN.completeThen { actual =>
              actual ==== expected(index)
            }
        }
      )
    }

  def testCatsEffectRunnerWithExpectError: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      error   <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .log("error")
    } yield withIO { implicit ticker =>

      val expected = error
      val actual   = IO.raiseError[Int](error)

      actual.expectError(expected)
    }

  def testCatsEffectRunnerWithExpectErrorPair: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      genError = Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
      error  <- genError.log("error")
      error2 <- genError.log("error2")
    } yield withIO { implicit ticker =>

      val expected  = error
      val expected2 = error2

      val actual  = IO.raiseError[Int](error)
      val actual2 = IO.raiseError[String](error2)

      actual.expectError(expected).and(actual2.expectError(expected2))
    }

  def testCatsEffectRunnerWithExpectErrorMultiple: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      errors  <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .list(Range.linear(3, 10))
                   .log("errors")
    } yield withIO { implicit ticker =>

      val expected = errors
      val actual   = errors.map(IO.raiseError[Int](_))

      Result.all(
        actual.zipWithIndex.map {
          case (ioN, index) =>
            ioN.expectError(expected(index))
        }
      )
    }

  def testCatsEffectRunnerWithErrorThen: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      error   <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .log("error")
    } yield withIO { implicit ticker =>

      val expected = error
      val actual   = IO.raiseError[Int](error)

      actual.errorThen { actual =>
        actual ==== expected
      }
    }

  def testCatsEffectRunnerWithErrorThenPair: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      genError = Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
      error  <- genError.log("error")
      error2 <- genError.log("error2")
    } yield withIO { implicit ticker =>

      val expected  = error
      val expected2 = error2
      val actual    = IO.raiseError[Int](error)
      val actual2   = IO.raiseError[Int](error2)

      actual
        .errorThen { actual =>
          actual ==== expected
        }
        .and(actual2.errorThen { actual =>
          actual ==== expected2
        })
    }

  def testCatsEffectRunnerWithErrorThenMultiple: Property =
    for {
      message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
      errors  <- Gen
                   .element1(
                     TestError.someTestError(s"Don't worry it's only a test error. $message"),
                     TestError.anotherTestError(s"Don't worry it's only a test error. $message"),
                   )
                   .list(Range.linear(3, 10))
                   .log("errors")
    } yield withIO { implicit ticker =>

      val expected = errors
      val actual   = errors.map(IO.raiseError[Int](_))

      Result.all(
        actual.zipWithIndex.map {
          case (ioN, index) =>
            ioN.errorThen { actual =>
              actual ==== expected(index)
            }
        }
      )
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
