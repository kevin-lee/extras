package extras.hedgehog.circe

import cats.syntax.all._
import cats.{Show, derived}
import hedgehog._
import hedgehog.runner._
import io.circe.{Codec, Decoder, Encoder}

/** @author Kevin Lee
  * @since 2022-10-23
  */
object RoundTripTesterSpec extends Properties {
  override def tests: List[Test] = List(
    property("test roundTripTest", roundTripTest),
    property("test RoundTripTester.Builder", roundTripTestBuilder),
    property("test roundTripTest with different decoding", roundTripTestDifferentDecoding),
    property("test roundTripTest with decoding failure", roundTripTestWithDecodeFailure),
  )

  def roundTripTest: Property = {
    for {
      something <- genSomething.log("something")
    } yield {
      RoundTripTester.roundTripTest(something, 2)
    }
  }

  def roundTripTestBuilder: Property = {
    for {
      something <- genSomething.log("something")
    } yield {
      RoundTripTester(something)
        .indent(4)
        .test()
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def roundTripTestDifferentDecoding: Property = {
    for {
      something <-
        genSomething
          .map(something => SomethingWithCustomDecoding(something.id, something.name))
          .log("something")
    } yield {
      RoundTripTester.roundTripTest(something, 2) match {
        case Result.Failure(actual) =>
          import io.circe.syntax._
          val json = something.asJson
          Decoder[SomethingWithCustomDecoding].decodeJson(json) match {
            case Right(decoded) =>
              actual ==== List(
                core.Info("=== Not Equal ==="),
                core.Info("--- lhs ---"),
                core.Info(
                  something
                    .copy(id = something.id + 100)
                    .copy(name = "Name=" + something.name)
                    .toString
                ),
                core.Info("--- rhs ---"),
                core.Info(something.toString),
                core.Info(
                  RoundTripTester.failureAfterParseSuccessMessage(
                    decoded,
                    something,
                    json,
                    io.circe.Printer.spaces2,
                  )
                ),
              )
            case Left(err) =>
              Result.failure.log(s"Decoding ${json.spaces2} for testing failed with error: ${err.show}.")
          }

        case Result.Success =>
          Result.failure.log("Test failure was expected but it succeeded.")
      }
    }
  }

  def roundTripTestWithDecodeFailure: Property = {
    for {
      something <- genSomething
                     .map(something => SomethingWithDecodeFailure(something.id, something.name))
                     .log("something")
    } yield {
      RoundTripTester.roundTripTest(something, 2) match {
        case Result.Failure(actual) =>
          import io.circe.syntax._

          val err = io
            .circe
            .DecodingFailure("Attempt to decode value on failed cursor", List(io.circe.CursorOp.DownField("blah")))

          val json = something.asJson

          actual ==== List(
            core.Info(RoundTripTester.decodeFailureMessage(something, json, err, io.circe.Printer.spaces2))
          )
        case Result.Success =>
          Result.failure.log("Test failure was expected but it succeeded.")
      }
    }
  }

  def genSomething: Gen[Something] =
    for {
      id   <- Gen.int(Range.linear(1, 100))
      name <- Gen.string(Gen.unicode, Range.linear(3, 20))
    } yield Something(id, name)

  final case class Something(id: Int, name: String)
  object Something {
    implicit val somethingShow: Show[Something] = derived.semiauto.show

    implicit val somethingCodec: Codec[Something] = io.circe.generic.semiauto.deriveCodec
  }

  final case class SomethingWithCustomDecoding(id: Int, name: String)
  object SomethingWithCustomDecoding {
    implicit val somethingWithDifferentDecoderShow: Show[SomethingWithCustomDecoding] = derived.semiauto.show

    implicit val somethingWithDifferentDecoderEncoder: Encoder[SomethingWithCustomDecoding] =
      io.circe.generic.semiauto.deriveEncoder

    implicit val somethingWithDifferentDecoderDecoder: Decoder[SomethingWithCustomDecoding] = Decoder.instance(c =>
      for {
        id   <- c.downField("id").as[Int].map(_ + 100)
        name <- c.downField("name").as[String].map("Name=" + _)
      } yield SomethingWithCustomDecoding(id, name)
    )
  }

  final case class SomethingWithDecodeFailure(id: Int, name: String)

  object SomethingWithDecodeFailure {
    implicit val somethingWithDecodeFailureShow: Show[SomethingWithDecodeFailure] = derived.semiauto.show

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
