---
sidebar_position: 3
id: 'with-manual-test-context-creation'
title: 'With Manual TestContext Creation'
sidebar_label: 'With TestContext'
---

## Module

```scala
"io.kevinlee" %% "extras-hedgehog-ce3" % "@VERSION@" % Test
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-hedgehog-ce3" % "@VERSION@" % Test
```

## `TestContext`

`cats.effect.kernel.testkit.TestContext` is from `cats-effect-kernel-testkit` and it is
> A `scala.concurrent.ExecutionContext` implementation that can simulate async boundaries and time passage

`extras-hedgehog-ce3` uses it and if you need to manually create it you can do so if you do not use `withIO` or `runIO`.
```scala
implicit val ticker: Ticker = Ticker.withNewTestContext()
```
`Ticker` is a value class for `TestContext` so you can also do
```scala
import cats.effect.kernel.testkit.TestContext
import extras.hedgehog.ce3.Ticker // You don't need to import it if you're using extras.hedgehog.ce3.syntax.runner

implicit val ticker: Ticker = Ticker(TestContext())
```

## completeThen
Use `CatsEffectRunner` and `completeThen` instead of `unsafeRunSync()`.

```scala {17} mdoc:reset-object
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {
  
  override def tests: List[Test] = List(
    property("test and completeThen", testCatsEffectRunnerWithCompleteThen)
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

```scala {23} mdoc:reset-object
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {

  override def tests: List[Test] = List(
    property("test and errorThen", testCatsEffectRunnerWithErrorThen)
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
