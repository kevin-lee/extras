package extras.circe.codecs

import cats.syntax.all._
import extras.circe.codecs.encoder.NamingConflictError
import hedgehog._
import hedgehog.runner._
import io.circe._
import io.circe.generic.semiauto._

import scala.reflect.ClassTag
import scala.util.Try

/** @author Kevin Lee
  * @since 2023-03-06
  */
object codecSpec extends Properties {
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

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      val expected     = Something(n, s, bd)
      val expectedJson = {
        import io.circe.literal._
        json"""{
                 "productNumber":$n,
                 "name": $s,
                 "price": $bd
               }"""
      }

      import io.circe.syntax._
      val encoded = expected.asJson

      val encodingResult = encoded ==== expectedJson

      import io.circe.parser._
      decode[Something](encoded.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              encodingResult,
              actual ==== expected,
            )
          )
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

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "s" -> "name"
          )
      }

      val expected = Something(n, s, bd)

      val expectedJson = {
        import io.circe.literal._
        json"""{
                 "n":$n,
                 "name": $s,
                 "bd": $bd
               }"""
      }

      import io.circe.syntax._
      val encoded = expected.asJson

      val encodingResult = encoded ==== expectedJson

      import io.circe.parser._
      decode[Something](encoded.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              encodingResult,
              actual ==== expected,
            )
          )
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

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "a" -> "productNumber",
            "b" -> "name",
            "c" -> "price",
          )
      }

      val expected = Something(n, s, bd)

      val expectedJson = {
        import io.circe.literal._
        json"""{
                 "n":$n,
                 "s": $s,
                 "bd": $bd
               }"""
      }

      import io.circe.syntax._
      val encoded = expected.asJson

      val encodingResult = encoded ==== expectedJson

      import io.circe.parser._
      decode[Something](encoded.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              encodingResult,
              actual ==== expected,
            )
          )
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

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )
      }

      val expected = Something(n, bd, s)

      val expectedJson = {
        import io.circe.literal._
        json"""{
                 "productNumber":$n,
                 "price": $bd,
                 "code": $s
               }"""
      }

      import io.circe.syntax._
      val encoded = expected.asJson

      val encodingResult = encoded ==== expectedJson

      import io.circe.parser._
      decode[Something](encoded.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              encodingResult,
              actual ==== expected,
            )
          )
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
    }

  def testRenameNoneFields: Property =
    for {
      n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
    } yield {
      final case class Something(n: Int, s: Option[String], s2: Option[String])
      object Something {

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "s2" -> "code",
          )
      }

      val expected = Something(n, none, none)

      val expectedJson = {
        import io.circe.literal._
        json"""{
               "productNumber":$n,
               "name": null,
               "code": null
             }"""
      }

      import io.circe.syntax._
      val encoded = expected.asJson

      val encodingResult = encoded ==== expectedJson

      import io.circe.parser._
      decode[Something](encoded.noSpaces) match {
        case Right(actual) =>
          Result.all(
            List(
              encodingResult,
              actual ==== expected,
            )
          )
        case Left(err) => Result.failure.log(s"Decoding JSON to Something failed with error: ${err.getMessage}")
      }
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

        import extras.circe.codecs.codec._

        implicit val somethingCodec: Codec[Something] =
          deriveCodec[Something].renameFields(
            "n"  -> "productNumber",
            "s"  -> "name",
            "bd" -> "price",
          )

      }

      val something = Something(n, s, name, n2, bd)

      val expected =
        NamingConflictError(
          List("s" -> "name", "n" -> "productNumber").sorted,
          implicitly[ClassTag[Something]].runtimeClass.getName,
        )

      import io.circe.syntax._

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
