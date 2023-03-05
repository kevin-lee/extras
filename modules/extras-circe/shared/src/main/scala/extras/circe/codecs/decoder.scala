package extras.circe.codecs

import io.circe.Decoder

/** @author Kevin Lee
  * @since 2023-03-05
  */
trait decoder {
  import extras.circe.codecs.decoder.DecoderExtras

  implicit def decoderExtras[A](decoder: Decoder[A]): DecoderExtras[A] = new DecoderExtras(decoder)
}
object decoder extends decoder {
  final class DecoderExtras[A](private val decoder: Decoder[A]) extends AnyVal {
    def renameFields(newName: (String, String), rest: (String, String)*): Decoder[A] =
      DecoderExtras.renameFields(decoder)(newName, rest: _*)
  }
  object DecoderExtras {
    def renameFields[A](decoder: Decoder[A])(newName: (String, String), rest: (String, String)*): Decoder[A] =
      Decoder.instance { hCursor =>
        val newJson = hCursor.value.mapObject { jsObj =>
          val namePairs = newName :: rest.toList
          namePairs.foldLeft(jsObj) {
            case (jsObj, (oldName, newName)) =>
              jsObj(newName)
                .map { valueFound =>
                  jsObj.add(oldName, valueFound).remove(newName)
                }
                .getOrElse(jsObj)
          }
        }
        @SuppressWarnings(Array("org.wartremover.warts.Null"))
        val decoded = decoder(
          hCursor.replace(
            newJson,
            hCursor,
            null, // scalafix:ok DisableSyntax.null
          )
        )
        decoded
      }
  }
}
