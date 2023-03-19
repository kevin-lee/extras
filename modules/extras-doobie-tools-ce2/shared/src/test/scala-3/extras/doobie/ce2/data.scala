package extras.doobie.ce2

import doobie.{Get, Put}
import doobie.util.{Read, Write}

import doobie.Meta

/** @author Kevin Lee
  * @since 2022-11-27
  */
object data {
  final private[ce2] case class Example(id: Example.Id, name: Example.Name, note: Example.Note)

  private[ce2] object Example {

    type Id = Id.Id
    object Id {
      opaque type Id = Int
      def apply(id: Int): Id = id

      given idCanEqual: CanEqual[Id, Id] = CanEqual.derived

      extension (id: Id) {
        def value: Int = id
      }

      given idMeta: Meta[Id] = Meta.IntMeta.imap[Id](Id(_))(_.value)
    }

    type Name = Name.Name
    object Name {
      opaque type Name = String
      def apply(name: String): Name = name

      given nameCanEqual: CanEqual[Name, Name] = CanEqual.derived

      extension (name: Name) {
        def value: String = name
      }

      given nameMeta: Meta[Name] = Meta.StringMeta.imap(Name(_))(_.value)
    }

    type Note = Note.Note
    object Note {
      opaque type Note = String
      def apply(note: String): Note = note

      given noteCanEqual: CanEqual[Note, Note] = CanEqual.derived

      extension (note: Note) {
        def value: String = note
      }

      given noteMeta: Meta[Note] = Meta.StringMeta.imap(Note(_))(_.value)
    }

    given readExample: Read[Example] =
      Read[(Int, String, String)].map {
        case (id, name, note) => Example(Example.Id(id), Example.Name(name), Example.Note(note))
      }

    given writeExample: Write[Example] =
      Write[(Int, String, String)].contramap(example => (example.id.value, example.name.value, example.note.value))

  }

  trait Compat
}
