---
sidebar_position: 2
id: 'test-with-io'
title: 'Test with cats-effect 3 IO'
---

# Test with cats-effect 3 `IO`

If you just run `IO.unsafeRunSync()` in a test, it may not end and just hang. extras for hedgehog and cats-effect 3 (`extras-hedgehog-ce3`) can solve it. 

## completeThen
Use `CatsEffectRunner` and `completeThen` instead of `unsafeRunSync()`.

```scala
import cats.effect._

import extras.hedgehog.ce3.CatsEffectRunner

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties with CatsEffectRunner {
  
  override def tests: List[Test] = List(
    property("test with CatsEffectRunner and completeThen", testCatsEffectRunnerWithCompleteThen)
  )
  
  def testCatsEffectRunnerWithCompleteThen: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield {
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = n
    val actual = IO(n)

    actual.completeThen { actual =>
      actual ==== expected
    }
  }
}
```

## errorThen
If you want to test with `IO` which may result in some `Exception` thrown, you can use `errorThen` instead of `unsafeRunSync()` and `Try`.

```scala
import cats.effect._

import extras.hedgehog.ce3.CatsEffectRunner

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties with CatsEffectRunner {

  override def tests: List[Test] = List(
    property("test with CatsEffectRunner and errorThen", testCatsEffectRunnerWithErrorThen)
  )

  def testCatsEffectRunnerWithErrorThen: Property = for {
    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
    error <- Gen
      .element1(
        TestError.someTestError(s"Don't worry it's only a test error. $message"),
        TestError.anotherTestError(s"Don't worry it's only a test error. $message")
      )
      .log("error")
  } yield {
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = error
    val actual = IO.raiseError[Int](error)

    actual.errorThen { actual =>
      actual ==== expected
    }
  }

  sealed abstract class TestError(val message: String) extends RuntimeException(message)

  object TestError {
    final case class SomeTestError(override val message: String) extends TestError(message)
    final case class AnotherTestError(override val message: String) extends TestError(message)

    def anotherTestError(message: String): TestError = AnotherTestError(message)
    def someTestError(message: String): TestError = SomeTestError(message)

  }
}
```
