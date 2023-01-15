package extras.fs2.text

import cats.effect.Sync
import fs2.{text, Stream}

/** @author Kevin Lee
  * @since 2023-01-15
  */
object syntax {

  implicit class byteStreamOps[F[*]](private val byteStream: Stream[F, Byte]) {
    def utf8String(implicit sync: Sync[F]): F[String] =
      byteStream
        .through(text.utf8.decode)
        .through(text.lines)
        .compile[F, F, String]
        .string
  }

}
