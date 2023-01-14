package extras.circe.codecs

import extras.circe.codecs.encoder._
import hedgehog._
import hedgehog.runner._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder, Json}

/** @author Kevin Lee
  * @since 2023-01-14
  */
object encoderSpec extends Properties {
  override def tests: List[Test] = List(
    property("test EncoderExtras.addFields", testEncoderExtrasAddFields),
    property("extra round-trip test for EncoderExtras.addFields", roundTripTestEncoderExtrasAddFields),
  )

  def testEncoderExtrasAddFields: Property =
    for {
      n                    <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      additionalFieldName  <- Gen
                                .string(Gen.alpha, Range.linear(2, 5))
                                .list(Range.linear(1, 5))
                                .map(_.mkString("-"))
                                .log("additionalFieldName")
      additionalFieldValue <- Gen.string(Gen.unicode, Range.linear(1, 5)).log("additionalFieldValue")
    } yield {
      import io.circe.syntax._
      final case class Something(n: Int) {
        def name: String = "Blah"
      }

      object Something {
        implicit val somethingEncoder: Encoder[Something] =
          io.circe
            .generic
            .semiauto
            .deriveEncoder[Something]
            .withFields { a =>
              List(
                "name"              -> Json.fromString(a.name),
                additionalFieldName -> additionalFieldValue.asJson,
              )
            }
      }

      val something = Something(n)
      import io.circe.literal._

      val expected =
        json"""{
                 "n":${something.n},
                 "name": "Blah",
                 $additionalFieldName: $additionalFieldValue
               }"""
      val actual   = something.asJson

      actual ==== expected
    }

  def roundTripTestEncoderExtrasAddFields: Property =
    for {
      n                    <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      additionalFieldName  <- Gen
                                .string(Gen.alpha, Range.linear(2, 5))
                                .list(Range.linear(1, 5))
                                .map(_.mkString("-"))
                                .log("additionalFieldName")
      additionalFieldValue <- Gen.string(Gen.unicode, Range.linear(1, 5)).log("additionalFieldValue")
    } yield {
      import io.circe.syntax._
      final case class Something(n: Int) {
        def name: String = "Blah"
      }

      object Something {
        implicit val somethingEncoder: Encoder[Something] = deriveEncoder[Something]
          .withFields { a =>
            List(
              "name"              -> Json.fromString(a.name),
              additionalFieldName -> additionalFieldValue.asJson,
            )
          }

        implicit val somethingDecoder: Decoder[Something] = deriveDecoder
      }

      val something = Something(n)
      import io.circe.literal._

      val expected     = something
      val expectedJson =
        json"""{
                 "n":${something.n},
                 "name": "Blah",
                 $additionalFieldName: $additionalFieldValue
               }"""

      val json = something.asJson

      import io.circe.parser._

      decode[Something](json.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              (actual ==== expected)
                .log("The decoded Something doesn't equal to the expected Something"),
              (actual.asJson ==== expectedJson)
                .log("Encoded JSON from the decoded Something doesn't equal to the expected JSON"),
            )
          )

        case Left(err) =>
          Result.failure.log(s"Failed to decode Something with error: ${err.getMessage}")
      }

    }

}
