package extras.circe.codecs

import cats.syntax.all._
import io.circe.{Encoder, Json}

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2023-01-14
  */
trait encoder {
  import extras.circe.codecs.encoder.EncoderExtras

  implicit def encoderExtras[A](encoder: Encoder[A]): EncoderExtras[A] = new EncoderExtras[A](encoder)
}
object encoder extends encoder {

  final class EncoderExtras[A](private val encoder: Encoder[A]) extends AnyVal {
    def withFields(newFields: A => List[(String, Json)]): Encoder[A] = EncoderExtras.withFields(encoder)(newFields)

    def renameFields(newName: (String, String), rest: (String, String)*)(implicit classTag: ClassTag[A]): Encoder[A] =
      EncoderExtras.renameFields[A](encoder)(newName, rest: _*)
  }

  object EncoderExtras {

    def withFields[A](encoder: Encoder[A])(newFields: A => List[(String, Json)]): Encoder[A] =
      Encoder.instance[A] { a =>
        Json.obj(newFields(a): _*).deepMerge(encoder(a))
      }

    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def renameFields[A: ClassTag](encoder: Encoder[A])(newName: (String, String), rest: (String, String)*): Encoder[A] =
      Encoder.instance[A] { a =>
        val originalJson = encoder(a)
        originalJson.mapObject { jsObj =>
          val namePairs = newName :: rest.toList
          val names     = jsObj.keys.toList
          val conflicts = namePairs.filter {
            case (_, newName) =>
              names.exists(_ === newName)
          }
          if (conflicts.nonEmpty) {
            throw NamingConflictError(
              conflicts.sorted,
              implicitly[ClassTag[A]].runtimeClass.getName,
            ) // scalafix:ok DisableSyntax.throw
          } else {
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

  final case class NamingConflictError(names: List[(String, String)], typeName: String)
      extends RuntimeException(
        s"There are newName values conflict with the existing filed names for $typeName. " +
          "conflicted newNames (oldName -> newName): " +
          s"${names.map { case (oldName, newName) => s"$oldName -> $newName" }.mkString("[", ", ", "]")}"
      )

}
