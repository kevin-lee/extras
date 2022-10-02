---
sidebar_position: 1
id: 'with-io'
title: 'withIO'
---

## `withIO`

If you just run `IO.unsafeRunSync()` in a test, it may not end and just hang. extras for hedgehog and cats-effect 3 (`extras-hedgehog-ce3`) can solve it with `withIO`.

```scala
def withIO(test: => Ticker => Result): Result
```

To use `withIO`, your test needs to import `extras.hedgehog.ce3.syntax.runner`
```scala
import hedgehog.runner._
import extras.hedgehog.ce3.syntax.runner._

object SomeSpec extends Properties {
```
then in your test, use `withIO` like
```scala {2}
def test: Result = 
  withIO { implicit ticker =>
    // Your test
    val exected = ??? // A
    val actual = ??? // IO[A]
    actual.completeThen(_ ==== expected)
  }
```
or
```scala {4}
def test: Property =
  for {
    n <- Gen.int(Range.linear(1, 100)).log("n")
  } yield withIO { implicit ticker =>
    // Your test
    val exected = ??? // A
    val actual = ??? // IO[A]
    actual.completeThen(_ ==== expected)
  }
```


### completeThen
Use `extras.hedgehog.ce3.syntax.runner` and `completeThen` instead of `unsafeRunSync()`.

```scala {8,16,21-23}
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {
  
  override def tests: List[Test] = List(
    property("test with CatsEffectRunner and completeThen", testCatsEffectRunnerWithCompleteThen)
  )
  
  def testCatsEffectRunnerWithCompleteThen: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield withIO { implicit ticker =>

    val expected = n
    val actual = IO(n)

    actual.completeThen { actual =>
      actual ==== expected
    }
  }
}

```

### errorThen
If you want to test with `IO` which may result in some `Exception` thrown, you can use `errorThen` instead of `unsafeRunSync()` and `Try`.

```scala {8,22,27-29}
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {

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
  } yield withIO { implicit ticker =>

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
