package extras.hedgehog.circe

import cats.Show
import cats.syntax.all.*
import extras.typeinfo.syntax.types.*
import hedgehog.*
import io.circe
import io.circe.syntax.*
import io.circe.{Decoder, Encoder, Json, Printer}

/** @author Kevin Lee
  * @since 2022-10-23
  */
object RoundTripTester {

  def roundTripTest[A: Encoder: Decoder: Show: TypeName](a: A, indent: Int): Result = indent match {
    case 0 =>
      roundTripTest0(a, Printer.noSpaces)
    case 2 =>
      roundTripTest0(a, Printer.spaces2)
    case 4 =>
      roundTripTest0(a, Printer.spaces4)
    case _ =>
      roundTripTest0(a, Printer.indented(" " * indent))
  }

  private def roundTripTest0[A: Encoder: Decoder: Show: TypeName](a: A, printer: Printer): Result = {
    val expected = a
    val json     = a.asJson

    import io.circe.parser.*
    decode[A](json.noSpaces) match {
      case Right(actual) =>
        (actual ==== expected)
          .log(failureAfterParseSuccessMessage[A](actual, a, json, printer))
      case Left(err) =>
        Result
          .failure
          .log(decodeFailureMessage[A](a, json, err, printer))
    }
  }

  private[circe] def failureAfterParseSuccessMessage[A: Show: TypeName](
    actual: A,
    input: A,
    json: Json,
    printer: Printer,
  ): String =
    s"""Round-trip test for ${input.nestedTypeName} failed:
       |  The input ${input.nestedTypeName} object does not equal to
       |  the one that was encoded from the input to JSON then decoded to have
       |  the ${input.nestedTypeName} type object back.
       |> ---
       |> Input: ${input.show}
       |> ---
       |> Actual: ${actual.show}
       |> ---
       |> JSON: ${json.printWith(printer)}
       |""".stripMargin

  private[circe] def decodeFailureMessage[A: Show: TypeName](
    a: A,
    json: Json,
    err: circe.Error,
    printer: Printer,
  ): String =
    s"""Round-trip test for ${a.nestedTypeName} failed with error:
       |> Error: ${err.show}
       |> ---
       |> Input: ${a.show}
       |> ---
       |> JSON: ${json.printWith(printer)}
       |>""".stripMargin

  trait Builder[A] {
    def indent(indent: Int): Builder[A]

    def test(): Result
  }
  final private case class BuilderA[A: Encoder: Decoder: Show: TypeName](a: A, _indent: Int) extends Builder[A] {
    def indent(indent: Int): Builder[A] = copy(_indent = indent)

    def test(): Result = roundTripTest(a, _indent)
  }

  def apply[A: Encoder: Decoder: Show: TypeName](a: A): Builder[A] = BuilderA(a, 2)

}
