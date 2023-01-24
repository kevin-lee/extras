package extras.fs2.text

import fs2.Stream.Compiler
import fs2.{text, Stream}

/** @author Kevin Lee
  * @since 2023-01-15
  */
object syntax {
  extension [F[*]](byteStream: Stream[F, Byte]) {
    def utf8String(using Compiler[F, F]): F[String] =
      byteStream
        .through(text.utf8Decode)
        .through(text.lines)
        .compile[F, F, String]
        .string
  }

}
