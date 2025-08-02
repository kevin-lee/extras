---
sidebar_position: 2
id: 'round-trip-tester'
title: 'RoundTripTester'
---

## Module

```scala
"io.kevinlee" %% "extras-hedgehog-circe" % "@VERSION@" % Test
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-hedgehog-circe" % "@VERSION@" % Test
```

## Test JSON Encoder and Decoder

`RoundTripTester` tests the available Circe JSON `Encoder` and `Decoder` by:
* Encoding the given type `A` into a JSON `String` using `Encoder[A]`.
* Decoding the JSON `String` back into the original type `A` using `Decoder[A]`.

```scala
RoundTripTester(sometypeA).test()
// hedgehog.Result
```

## Examples:

```scala mdoc:reset-object
import extras.hedgehog.circe.RoundTripTester

import cats._
import io.circe._

import hedgehog._
import hedgehog.runner._

object SomethingSpec extends Properties {
  override def tests: List[Test] = List(
    property("round-trip test Something JSON Codec", roundTripTest),
    property("round-trip test Something JSON Codec - failure case", roundTripTestWithDecodeFailure),
    property("round-trip test Something JSON Codec - failure case (indent 8)", roundTripTestWithDecodeFailureIndent8),
  )
  
  def roundTripTest: Property =
    for {
      something <- genSomething.log("something")
    } yield {
      RoundTripTester(something).test()
    }

  def roundTripTestWithDecodeFailure: Property =
    for {
      something <- genSomething
        .map(something => SomethingWithDecodeFailure(something.id, something.name))
        .log("something")
    } yield {
      RoundTripTester(something).test()
    }

  def roundTripTestWithDecodeFailureIndent8: Property =
    for {
      something <- genSomething
        .map(something => SomethingWithDecodeFailure(something.id, something.name))
        .log("something")
    } yield {
      RoundTripTester(something)
        .indent(8)
        .test()
    }

  def genSomething: Gen[Something] =
    for {
      id <- Gen.int(Range.linear(1, 100))
      name <- Gen.string(Gen.unicode, Range.linear(3, 20))
    } yield Something(id, name)


  final case class Something(id: Int, name: String)
  object Something {
    implicit val somethingShow: Show[Something] =
      something => s"Something(id = ${something.id.toString}, name = ${something.name})"

    implicit val somethingCodec: Codec[Something] = io.circe.generic.semiauto.deriveCodec
  }


  final case class SomethingWithDecodeFailure(id: Int, name: String)

  object SomethingWithDecodeFailure {
    implicit val somethingWithDecodeFailureShow: Show[SomethingWithDecodeFailure] =
      somethingWithDecodeFailure =>
        List(
          s"id = ${somethingWithDecodeFailure.id.toString}",
          s"name = ${somethingWithDecodeFailure.name}",
        ).mkString(
          "SomethingWithDecodeFailure(",
          ", ",
          ")",
        )

    implicit val somethingWithDecodeFailureEncoder: Encoder[SomethingWithDecodeFailure] =
      io.circe.generic.semiauto.deriveEncoder

    implicit val somethingWithDecodeFailureDecoder: Decoder[SomethingWithDecodeFailure] =
      Decoder.instance(c =>
        for {
          id   <- c.downField("blah").as[Int]
          name <- c.downField("name").as[String]
        } yield SomethingWithDecodeFailure(id, name)
      )
  }

}


// This is only for this document so you don't need this.
SomethingSpec.main(Array.empty)
```