package extras.hedgehog.circe

import cats.Show
import cats.syntax.all.*

import hedgehog.*
import io.circe
import io.circe.syntax.*
import io.circe.{Decoder, Encoder, Json, Printer}

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2022-10-23
  */
object RoundTripTester {

  def roundTripTest[A: Encoder: Decoder: Show: ClassTag](a: A, indent: Int): Result = indent match {
    case 0 =>
      roundTripTest0(a, Printer.noSpaces)
    case 2 =>
      roundTripTest0(a, Printer.spaces2)
    case 4 =>
      roundTripTest0(a, Printer.spaces4)
    case _ =>
      roundTripTest0(a, Printer.indented(" " * indent))
  }

  private def roundTripTest0[A: Encoder: Decoder: Show: ClassTag](a: A, printer: Printer): Result = {
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

  private[circe] def failureAfterParseSuccessMessage[A: Show: ClassTag](
    actual: A,
    input: A,
    json: Json,
    printer: Printer,
  ): String = {
    val runtimeClass = summon[ClassTag[A]].runtimeClass
    s"""Round-trip test for ${runtimeClass.getName} failed:
       |  The input ${runtimeClass.getName} object does not equal to
       |  the one that was encoded from the input to JSON then decoded to have
       |  the ${runtimeClass.getName} type object back.
       |> ---
       |> Input: ${input.show}
       |> ---
       |> Actual: ${actual.show}
       |> ---
       |> JSON: ${json.printWith(printer)}
       |""".stripMargin
  }

  private[circe] def decodeFailureMessage[A: Show: ClassTag](
    a: A,
    json: Json,
    err: circe.Error,
    printer: Printer,
  ): String = {
    val runtimeClass = summon[ClassTag[A]].runtimeClass
    s"""Round-trip test for ${runtimeClass.getName} failed with error:
       |> Error: ${err.show}
       |> ---
       |> Input: ${a.show}
       |> ---
       |> JSON: ${json.printWith(printer)}
       |>""".stripMargin
  }

  trait Builder[A] {
    def indent(indent: Int): Builder[A]

    def test(): Result
  }
  final private case class BuilderA[A: Encoder: Decoder: Show: ClassTag](a: A, _indent: Int) extends Builder[A] {
    def indent(indent: Int): Builder[A] = copy(_indent = indent)

    def test(): Result = roundTripTest(a, _indent)
  }

  def apply[A: Encoder: Decoder: Show: ClassTag](a: A): Builder[A] = BuilderA(a, 2)

}
