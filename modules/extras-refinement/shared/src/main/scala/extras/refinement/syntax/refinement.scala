package extras.refinement.syntax

import cats.data.EitherNec
import cats.syntax.all._
import eu.timepit.refined._
import eu.timepit.refined.api._
import extras.reflects.syntax.reflects._
import io.estatico.newtype.Coercible
import io.estatico.newtype.ops._

trait refinement {
  import refinement.{PartiallyAppliedRefinement, RefinementSyntax}

  def validateAs[A]: PartiallyAppliedRefinement[A] = new PartiallyAppliedRefinement[A]()

  implicit def refinementSyntax[T, P](value: T): RefinementSyntax[T, P] = new RefinementSyntax(value)
}

object refinement extends refinement { self =>
  import scala.reflect.runtime.universe._

  private[refinement] final class PartiallyAppliedRefinement[A](private val dummy: Boolean = false) extends AnyVal {
    def apply[T, P](value: T)(
      implicit coercible: Coercible[Refined[T, P], A],
      validate: Validate[T, P],
      tt: WeakTypeTag[A]
    ): EitherNec[String, A] =
      refineV[P](value)
        .leftMap(err => s"Failed to create ${weakTypeTag[A].nestedTypeName}: $err")
        .toEitherNec
        .map(_.coerce[A])
  }

  private[refinement] final class RefinementSyntax[T, P](private val value: T) extends AnyVal {
    def validateAs[A](
      implicit coercible: Coercible[Refined[T, P], A],
      validate: Validate[T, P],
      tt: WeakTypeTag[A]
    ): EitherNec[String, A] = self.validateAs[A](value)
  }

}
