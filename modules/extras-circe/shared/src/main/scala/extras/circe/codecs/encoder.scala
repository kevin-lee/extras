package extras.circe.codecs

import io.circe.{Encoder, Json}

/** @author Kevin Lee
  * @since 2023-01-14
  */
trait encoder {
  import extras.circe.codecs.encoder.EncoderExtras

  implicit def encoderExtras[A](encoder: Encoder[A]): EncoderExtras[A] = new EncoderExtras[A](encoder)
}
object encoder extends encoder {

  class EncoderExtras[A](private val encoder: Encoder[A]) extends AnyVal {
    def withFields(newFields: A => List[(String, Json)]): Encoder[A] = EncoderExtras.withFields(encoder)(newFields)

    def renameFields(newName: (String, String), rest: (String, String)*): Encoder[A] =
      EncoderExtras.renameFields(encoder)(newName, rest: _*)
  }

  object EncoderExtras {

    def withFields[A](encoder: Encoder[A])(newFields: A => List[(String, Json)]): Encoder[A] =
      Encoder.instance[A] { a =>
        Json.obj(newFields(a): _*).deepMerge(encoder(a))
      }

    def renameFields[A](encoder: Encoder[A])(newName: (String, String), rest: (String, String)*): Encoder[A] =
      Encoder.instance[A] { a =>
        val originalJson = encoder(a)
        originalJson.mapObject { jsObj =>
          val namePairs = newName :: rest.toList
          namePairs.foldLeft(jsObj) {
            case (jsObj, (oldName, newName)) =>
              jsObj(oldName)
                .map { valueFound =>
                  jsObj.add(newName, valueFound).remove(oldName)
                }
                .getOrElse(jsObj)
          }
        }
      }
  }
}
