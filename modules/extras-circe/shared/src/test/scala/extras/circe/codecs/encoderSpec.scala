package extras.circe.codecs

import cats.syntax.all._
import extras.circe.codecs.encoder.NamingConflictError
import hedgehog._
import hedgehog.runner._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder, Json}

import scala.reflect.ClassTag
import scala.util.Try

/** @author Kevin Lee
  * @since 2023-01-14
  */
object encoderSpec extends Properties {
  override def tests: List[Test] = List(
    property("test EncoderExtras.addFields", testEncoderExtrasAddFields),
    property("extra round-trip test for EncoderExtras.addFields", roundTripTestEncoderExtrasAddFields),
    property("test rename all fields", testRenameAllFields),
    property("test rename some fields", testRenameSomeFields),
    property("test rename with no matching field", testRenameNoMatchingField),
    property("test rename with missing field", testRenameMissingField),
    property("test rename None fields", testRenameNoneFields),
    property("test rename fields with existing new names", testRenameWithExistingNewNameFields),
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
        import extras.circe.codecs.encoder._
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
        import extras.circe.codecs.encoder._
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

  def testRenameAllFields: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {
        import extras.circe.codecs.encoder._
        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      import io.circe.literal._

      val expected =
        json"""{
                 "productNumber":$n,
                 "name": $s,
                 "price": $bd
               }"""

      val something = Something(n, s, bd)

      import io.circe.syntax._

      val actual = something.asJson
      actual ==== expected
    }

  def testRenameSomeFields: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {
        import extras.circe.codecs.encoder._
        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "s" -> "name"
          )
      }

      import io.circe.literal._

      val expected =
        json"""{
                 "n":$n,
                 "name": $s,
                 "bd": $bd
               }"""

      val something = Something(n, s, bd)
      import io.circe.syntax._
      val actual    = something.asJson

      actual ==== expected
    }

  def testRenameNoMatchingField: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {
        import extras.circe.codecs.encoder._
        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "a" -> "productNumber",
            "b" -> "name",
            "c" -> "price",
          )
      }

      import io.circe.literal._

      val expected =
        json"""{
                 "n":$n,
                 "s": $s,
                 "bd": $bd
               }"""

      val something = Something(n, s, bd)
      import io.circe.syntax._
      val actual    = something.asJson

      actual ==== expected
    }

  def testRenameMissingField: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, bd: BigDecimal, code: String)
      object Something {
        import extras.circe.codecs.encoder._
        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      import io.circe.literal._

      val expected =
        json"""{
                 "productNumber":$n,
                 "price": $bd,
                 "code": $s
               }"""

      val something = Something(n, bd, s)
      import io.circe.syntax._
      val actual    = something.asJson

      actual ==== expected
    }

  def testRenameNoneFields: Property =
    for {
      n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
    } yield {
      final case class Something(n: Int, s: Option[String], s2: Option[String])
      object Something {

        import extras.circe.codecs.encoder._

        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "s2" -> "code",
          )
      }

      import io.circe.literal._

      val expected =
        json"""{
               "productNumber":$n,
               "name": null,
               "code": null
             }"""

      val something = Something(n, none, none)
      import io.circe.syntax._
      val actual    = something.asJson

      actual ==== expected
    }

  def testRenameWithExistingNewNameFields: Property =
    for {
      n    <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      n2   <- Gen.int(Range.linear(1, Int.MaxValue)).log("n2")
      s    <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      name <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_ + s).log("name")
      bd   <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, name: String, productNumber: Int, bd: BigDecimal)
      object Something {

        import extras.circe.codecs.encoder._

        implicit val somethingEncoder: Encoder[Something] =
          deriveEncoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      val something = Something(n, s, name, n2, bd)

      import io.circe.syntax._
      val expected =
        NamingConflictError(
          List("s" -> "name", "n" -> "productNumber").sorted,
          implicitly[ClassTag[Something]].runtimeClass.getName,
        )

      Try(something.asJson) match {
        case scala.util.Failure(err) =>
          Result.all(
            List(
              err ==== expected,
              err.getMessage ==== (
                s"There are newName values conflict with the existing filed names for ${expected.typeName}. " +
                  "conflicted newNames (oldName -> newName): " +
                  s"${expected.names.map { case (oldName, newName) => s"$oldName -> $newName" }.mkString("[", ", ", "]")}"
              ),
            )
          )

        case scala.util.Success(json) =>
          Result
            .failure
            .log(
              s"""Expected EncoderExtras.NamingConflictError but got the following JSON
                 |${json.spaces2}
                 |>""".stripMargin
            )
      }

    }

}
