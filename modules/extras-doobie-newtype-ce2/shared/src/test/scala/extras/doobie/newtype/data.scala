package extras.doobie.newtype

import cats.Eq
import io.estatico.newtype.macros.newtype

/** @author Kevin Lee
  * @since 2023-03-19
  */
object data {
  final case class Example(id: Example.Id, name: Example.Name, note: Example.Note)

  object Example {

    @newtype case class Id(value: Int)
    object Id {
      implicit val idEq: Eq[Id] = deriving
    }

    @newtype case class Name(value: String)
    object Name {
      implicit val nameEq: Eq[Name] = deriving
    }

    @newtype case class Note(value: String)
    object Note {
      implicit val noteEq: Eq[Note] = deriving
    }

  }
}
