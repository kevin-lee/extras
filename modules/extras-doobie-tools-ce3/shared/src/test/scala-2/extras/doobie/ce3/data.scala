package extras.doobie.ce3

import cats.Eq
import doobie.Read
import doobie.util.{Put, Write}
import io.estatico.newtype.macros.newtype

import scala.annotation.nowarn

/** @author Kevin Lee
  * @since 2022-11-27
  */
object data {
  final case class Example(id: Example.Id, name: Example.Name, note: Example.Note)

  object Example {

    @newtype case class Id(value: Int)

    @newtype case class Name(value: String)

    @newtype case class Note(value: String)

    implicit val writeExample: Write[Example] =
      Write[(Int, String, String)].contramap(example => (example.id.value, example.name.value, example.note.value))

    implicit val readExample: Read[Example] =
      Read[(Int, String, String)].map {
        case (id, name, note) =>
          Example(Example.Id(id), Example.Name(name), Example.Note(note))
      }

  }

  trait doobieNewtype {
    /*
     * Support newtype in doobie is from
     * from https://gist.github.com/pierangeloc/ec408ca3a864f5d72dbc83a0e793e59a#file-doobie_newtype-scala
     */
    import io.estatico.newtype.Coercible
    import io.estatico.newtype.ops.*

    implicit def newTypePut[N: Coercible[*, R], R: Put]: Put[N] = Put[R].contramap[N](_.coerce[R])

    @nowarn(
      "msg=evidence parameter evidence\\$[\\d]+ (?:of type io\\.estatico\\.newtype\\.Coercible\\[R,N\\])? in method newTypeRead is never used"
    )
    implicit def newTypeRead[N: Coercible[R, *], R: Read]: Read[N] =
      Read[R].map(_.asInstanceOf[N]) // scalafix:ok DisableSyntax.asInstanceOf

    /** If there is an Eq instance for Repr type R, derives an Eq instance for NewType N. */
    implicit def coercibleEq[R, N](implicit ev: Coercible[Eq[R], Eq[N]], R: Eq[R]): Eq[N] = ev(R)

  }

  object doobieNewtype extends doobieNewtype

  trait Compat extends doobieNewtype

}
