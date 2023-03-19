package extras.doobie

import cats.Eq
import doobie.{Put, Read}

import scala.annotation.nowarn

/** The technique to support newtype in doobie
  * from https://gist.github.com/pierangeloc/ec408ca3a864f5d72dbc83a0e793e59a#file-doobie_newtype-scala
  * @author Kevin Lee
  * @since 2023-03-19
  */
package object newtype {

  import io.estatico.newtype.Coercible
  import io.estatico.newtype.ops._

  @nowarn(
    "msg=evidence parameter evidence\\$[\\d]+ (?:of type io\\.estatico\\.newtype\\.Coercible\\[R,N\\])? in method newTypeRead is never used"
  )
  implicit def newTypeRead[N: Coercible[R, *], R: Read]: Read[N] =
    Read[R].map(_.asInstanceOf[N]) // scalafix:ok DisableSyntax.asInstanceOf

  implicit def newTypePut[N: Coercible[*, R], R: Put]: Put[N] = Put[R].contramap[N](_.coerce[R])

  /** If there is an Eq instance for Repr type R, derives an Eq instance for NewType N. */
  implicit def coercibleEq[R, N](implicit ev: Coercible[Eq[R], Eq[N]], R: Eq[R]): Eq[N] = ev(R)

}
