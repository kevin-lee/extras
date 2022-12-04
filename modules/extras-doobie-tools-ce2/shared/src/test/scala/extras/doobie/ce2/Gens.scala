package extras.doobie.ce2

import hedgehog.*
import extras.doobie.ce2.data.Example

/** @author Kevin Lee
  * @since 2022-11-27
  */
object Gens {
  def genExample: Gen[Example] = for {
    id   <- Gen.int(Range.linear(1, Int.MaxValue)).map(Example.Id(_))
    name <- Gen.string(Gen.unicode, Range.linear(5, 20)).map(Example.Name(_))
    note <- Gen.string(Gen.unicode, Range.linear(5, 20)).map(Example.Note(_))
  } yield Example(id, name, note)
}
