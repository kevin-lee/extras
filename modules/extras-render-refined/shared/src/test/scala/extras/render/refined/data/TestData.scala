package extras.render.refined.data

import eu.timepit.refined.types.string.NonEmptyString
import extras.render.Render
import io.estatico.newtype.macros.newtype

/** @author Kevin Lee
  * @since 2022-11-23
  */
object TestData {
  import extras.render.refined._

  @newtype case class Name(value: NonEmptyString)
  object Name {
    implicit val nameRender: Render[Name] = deriving
  }
}
