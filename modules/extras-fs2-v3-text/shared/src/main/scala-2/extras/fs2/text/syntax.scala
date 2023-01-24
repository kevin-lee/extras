package extras.fs2.text

import fs2.{text, Compiler, Stream}

/** @author Kevin Lee
  * @since 2023-01-15
  */
object syntax {

  implicit class byteStreamOps[F[*]](private val byteStream: Stream[F, Byte]) {
    def utf8String(implicit compiler: Compiler[F, F]): F[String] =
      byteStream
        .through(text.utf8.decode)
        .through(text.lines)
        .compile[F, F, String]
        .string
  }

}
