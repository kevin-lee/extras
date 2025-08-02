---
sidebar_position: 1
id: 'run-io'
title: 'runIO'
---

## Module

```scala
"io.kevinlee" %% "extras-hedgehog-ce3" % "@VERSION@" % Test
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-hedgehog-ce3" % "@VERSION@" % Test
```

## `runIO`

If you just run `IO.unsafeRunSync()` in a test, it may not end and just hang. extras for hedgehog and cats-effect 3 (`extras-hedgehog-ce3`) can solve it with `runIO`.

```scala
def runIO(test: => IO[Result]): Result
```

To use `runIO`, your test needs to import `extras.hedgehog.ce3.syntax.runner`
```scala
import hedgehog.runner._
import extras.hedgehog.ce3.syntax.runner._

object SomeSpec extends Properties {
```
then in your test, use `runIO` like
```scala {2}
def test: Result = 
  runIO {
    // Your test
    val exected = ??? // A
    val actual = ??? // IO[A]
    actual.map(_ ==== expected) // IO[Result]
  }
```
or
```scala {4}
def test: Property =
  for {
    n <- Gen.int(Range.linear(1, 100)).log("n")
  } yield runIO {
    // Your test
    val exected = ??? // A
    val actual = ??? // IO[A]
    actual.map(_ ==== expected) // IO[Result]
  }
```


### completeThen
Use `extras.hedgehog.ce3.syntax.runner` and `completeThen` instead of `unsafeRunSync()`.

```scala {8,16,21-23} mdoc:reset-object
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {
  
  override def tests: List[Test] = List(
    property("test with syntax and success case", testCatsEffectRunnerWithSuccessCase)
  )
  
  def testCatsEffectRunnerWithSuccessCase: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield runIO {

    val expected = n
    val actual = IO(n)

    actual.map { actual =>
      actual ==== expected
    } // IO[Result]
  }
}

```

### errorThen
If you want to test with `IO` which may result in some `Exception` thrown, you can use `errorThen` instead of `unsafeRunSync()` and `Try`.

```scala {8,22,27-32} mdoc:reset-object
import cats.effect._

import extras.hedgehog.ce3.syntax.runner._

import hedgehog._
import hedgehog.runner._

object SomeSpec extends Properties {

  override def tests: List[Test] = List(
    property("test with syntax and error case", testCatsEffectRunnerWithErrorCase)
  )

  def testCatsEffectRunnerWithErrorCase: Property = for {
    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")
    error <- Gen
      .element1(
        TestError.someTestError(s"Don't worry it's only a test error. $message"),
        TestError.anotherTestError(s"Don't worry it's only a test error. $message")
      )
      .log("error")
  } yield runIO {

    val expected = error
    val actual = IO.raiseError[Int](error)

    actual.attempt.map {
      case Left(actual) =>
        actual ==== expected
      case Right(r) =>
        Result.failure.log(s"Expected error $expected but got $r instead.")
    } // IO[Result]
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
