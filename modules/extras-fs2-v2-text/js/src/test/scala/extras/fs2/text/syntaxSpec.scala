package extras.fs2.text

import cats.effect.{IO, Sync}
import cats.syntax.all._
import fs2.Stream
import hedgehog._
import hedgehog.runner._

import java.nio.charset.StandardCharsets

/** @author Kevin Lee
  * @since 2023-01-15
  */
object syntaxSpec extends Properties {

  override def tests: List[Test] = List(
    property("test ByteStream to utf8String.string", testByteStreamToUtf8String),
    example("test empty ByteStream to utf8String.string", testEmptyByteStreamToUtf8String),
  )

  def testByteStreamToUtf8String: Property =
    for {
      s <- Gen.string(Gen.alphaNum, Range.linear(1, 1000)).log("s")
    } yield {

      def test[F[*]: Sync]: F[Result] = {

        val stream = Stream[F, Byte](s.getBytes(StandardCharsets.UTF_8).toList: _*)

        import extras.fs2.text.syntax._
        val expected = s
        stream
          .utf8String
          .map { actual =>
            actual ==== expected
          }
      }

      test[IO]
        .unsafeRunSync()
    }

  def testEmptyByteStreamToUtf8String: Result = {

    def test[F[*]: Sync]: F[Result] = {
      val stream: Stream[F, Byte] = Stream.empty
      import extras.fs2.text.syntax._
      stream
        .utf8String
        .map { actual =>
          actual ==== ""
        }
    }

    test[IO]
      .unsafeRunSync()
  }

}
