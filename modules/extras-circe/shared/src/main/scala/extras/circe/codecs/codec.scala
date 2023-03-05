package extras.circe.codecs

import extras.circe.codecs.decoder.DecoderExtras
import extras.circe.codecs.encoder.EncoderExtras
import io.circe.Codec

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2023-03-06
  */
trait codec {
  import extras.circe.codecs.codec.CodecExtras

  implicit def codecExtras[A](codec: Codec[A]): CodecExtras[A] = new CodecExtras[A](codec)
}
object codec extends codec {
  final class CodecExtras[A](private val codec: Codec[A]) extends AnyVal {
    def renameFields(newName: (String, String), rest: (String, String)*)(implicit classTag: ClassTag[A]): Codec[A] =
      CodecExtras.renameFields[A](codec)(newName, rest: _*)

  }
  object CodecExtras {
    def renameFields[A: ClassTag](codec: Codec[A])(newName: (String, String), rest: (String, String)*): Codec[A] =
      Codec.from[A](
        DecoderExtras.renameFields[A](codec)(newName, rest: _*),
        EncoderExtras.renameFields[A](codec)(newName, rest: _*),
      )
  }
}
