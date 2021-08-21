package extras.cats.syntax

import cats.{Applicative, Functor}
import cats.data.EitherT

/** @author Kevin Lee
  * @since 2021-07-28
  */
trait EitherSyntax {

  extension [F[_], A, B](fOfEither: F[Either[A, B]]) {
    def eitherT: EitherT[F, A, B] = EitherT[F, A, B](fOfEither)
  }

  extension [A, B](either: Either[A, B]) {
    def eitherT[F[_]: Applicative]: EitherT[F, A, B] = EitherT.fromEither[F](either)
  }

  extension [F[_], A](fa: F[A]) {
    def rightT[B](using F: Functor[F]): EitherT[F, B, A] = EitherT.right[B](fa)
    def leftT[B](using F: Functor[F]): EitherT[F, A, B]  = EitherT.left[B](fa)
  }

  extension [A](a: A) {
    def rightTF[F[_]: Applicative, B]: EitherT[F, B, A] = EitherT.rightT[F, B](a)
    def leftTF[F[_]: Applicative, B]: EitherT[F, A, B]  = EitherT.leftT[F, B](a)
  }

}

object EitherSyntax extends EitherSyntax
