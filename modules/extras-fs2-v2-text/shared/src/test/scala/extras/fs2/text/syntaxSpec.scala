package extras.fs2.text

import cats.effect.IO
import fs2.Stream
import hedgehog._
import hedgehog.runner._

import java.nio.charset.StandardCharsets

/** @author Kevin Lee
  * @since 2023-01-15
  */
object syntaxSpec extends Properties {
  type F[A] = IO[A]

  override def tests: List[Test] = List(
    property("test ByteStream to utf8String.string", testByteStreamToUtf8String),
    example("test empty ByteStream to utf8String.string", testEmptyByteStreamToUtf8String),
    property("test Response.body to utf8String.string", testResponseBodyToUtf8String),
    example("test empty Response.body to utf8String.string", testEmptyResponseBodyToUtf8String),
  )

  def testByteStreamToUtf8String: Property =
    for {
      s <- Gen.string(Gen.alphaNum, Range.linear(1, 1000)).log("s")
    } yield {
      val stream = Stream[F, Byte](s.getBytes(StandardCharsets.UTF_8).toList: _*)

      import extras.fs2.text.syntax._
      val expected = s
      stream
        .utf8String
        .map { actual =>
          actual ==== expected
        }
        .unsafeRunSync()
    }

  def testEmptyByteStreamToUtf8String: Result = {
    val stream: Stream[F, Byte] = Stream.empty

    import extras.fs2.text.syntax._
    stream
      .utf8String
      .map { actual =>
        actual ==== ""
      }
      .unsafeRunSync()
  }

  def testResponseBodyToUtf8String: Property =
    for {
      s <- Gen.string(Gen.alphaNum, Range.linear(1, 1000)).log("s")
    } yield {
      import org.http4s.dsl.io._
      Ok(s)
        .flatMap { response =>
          import extras.fs2.text.syntax._
          val expected = s
          response
            .body
            .utf8String
            .map { actual =>
              Result.all(
                List(
                  (Result.assert(response.status.isEntityAllowed)).log("entity should be allowed"),
                  (actual ==== expected).log("actual should be equal to expected"),
                )
              )
            }

        }
        .unsafeRunSync()
    }

  def testEmptyResponseBodyToUtf8String: Result = {
    import extras.fs2.text.syntax._
    import org.http4s.dsl.io._
    Continue()
      .flatMap { response =>
        response
          .body
          .utf8String
          .map { actual =>
            Result.all(
              List(
                Result.assert(!response.status.isEntityAllowed).log("entity should not be allowed"),
                (actual ==== "").log("actual should be empty"),
              )
            )
          }
      }
      .unsafeRunSync()
  }

}
