package extras.circe.codecs

import cats.Show
import cats.syntax.all._
import hedgehog._
import hedgehog.runner._
import io.circe.Decoder
import io.circe.generic.semiauto._

/** @author Kevin Lee
  * @since 2023-03-05
  */
object decoderSpec extends Properties {
  override def tests: List[Test] = List(
    property("test rename all fields", testRenameAllFields),
    property("test rename some fields", testRenameSomeFields),
    property("test rename with no matching field", testRenameNoMatchingField),
    property("test rename with missing field", testRenameMissingField),
    property("test rename None fields", testRenameNoneFields),
    property("test rename fields with existing new names", testRenameWithExistingNewNameFields),
  )

  def testRenameAllFields: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      val expected = Something(n, s, bd)

      val input = {
        import io.circe.literal._
        json"""{
                 "productNumber":$n,
                 "name": $s,
                 "price": $bd
               }"""
      }
      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) => actual ==== expected
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }

    }

  def testRenameSomeFields: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "s" -> "name"
          )
      }

      val expected = Something(n, s, bd)

      val input = {
        import io.circe.literal._
        json"""{
                 "n":$n,
                 "name": $s,
                 "bd": $bd
               }"""
      }
      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) => actual ==== expected
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
    }

  def testRenameNoMatchingField: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, s: String, bd: BigDecimal)
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "a" -> "productNumber",
            "b" -> "name",
            "c" -> "price",
          )
      }

      val expected = Something(n, s, bd)

      val input = {
        import io.circe.literal._
        json"""{
                 "n":$n,
                 "s": $s,
                 "bd": $bd
               }"""
      }

      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) => actual ==== expected
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
    }

  def testRenameMissingField: Property =
    for {
      n  <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s  <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
      bd <- Gen.double(Range.linearFrac(0.10d, Double.MaxValue)).map(BigDecimal(_)).log("bd")
    } yield {
      final case class Something(n: Int, bd: BigDecimal, code: String)
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      val expected = Something(n, bd, s)

      val input = {
        import io.circe.literal._
        json"""{
                 "productNumber":$n,
                 "price": $bd,
                 "code": $s
               }"""
      }

      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) => actual ==== expected
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
    }

  def testRenameNoneFields: Property =
    for {
      n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
    } yield {
      final case class Something(n: Int, s: Option[String], s2: Option[String])
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "s2" -> "code",
          )
      }

      val expected = Something(n, none, none)

      val input = {
        import io.circe.literal._
        json"""{
               "productNumber":$n,
               "name": null,
               "code": null
             }"""
      }

      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) => actual ==== expected
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
    }

  def testRenameWithExistingNewNameFields: Property =
    for {
      n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
      s <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
    } yield {
      final case class Something(n: Int, s: String, name: String)
      object Something {

        import extras.circe.codecs.decoder._

        implicit val somethingDecoder: Decoder[Something] =
          deriveDecoder[Something].renameFields(
            "n" -> "productNumber",
            "s" -> "name",
          )

        implicit val somethingShow: Show[Something] = Show.fromToString
      }

      val input = {
        import io.circe.literal._
        json"""{
                 "productNumber":$n,
                 "name": $s
               }"""
      }

      import io.circe.parser._
      decode[Something](input.noSpaces) match {
        case Right(actual) =>
          Result
            .failure
            .log(
              s"""Expected decode failure due to field name conflict but got the following one instead.
                 |> ${actual.show}
                 |> """.stripMargin
            )

        case Left(err) =>
          Result.any(
            List(
              err.getMessage ==== "Attempt to decode value on failed cursor: DownField(name)", // for Scala 2
              err.getMessage ==== "Missing required field: DownField(name)", // for Scala 3
            )
          )

      }
    }

}
