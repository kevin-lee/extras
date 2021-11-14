package extras.cats.syntax

import cats.{Applicative, Functor}
import cats.data.EitherT

/** @author Kevin Lee
  * @since 2021-07-28
  */
trait EitherSyntax {

  extension [F[_], A, B](fOfEither: F[Either[A, B]]) {
    inline def eitherT: EitherT[F, A, B] = EitherT[F, A, B](fOfEither)
    inline def t: EitherT[F, A, B] = eitherT
  }

  extension [A, B](either: Either[A, B]) {
    inline def eitherT[F[_]: Applicative]: EitherT[F, A, B] = EitherT.fromEither[F](either)
    inline def t[F[_]: Applicative]: EitherT[F, A, B] = eitherT[F]
  }

  extension [F[_], A](fa: F[A]) {
    inline def rightT[B](using F: Functor[F]): EitherT[F, B, A] = EitherT.right[B](fa)
    inline def leftT[B](using F: Functor[F]): EitherT[F, A, B]  = EitherT.left[B](fa)
  }

  extension [A](a: A) {
    inline def rightTF[F[_]: Applicative, B]: EitherT[F, B, A] = EitherT.rightT[F, B](a)
    inline def leftTF[F[_]: Applicative, B]: EitherT[F, A, B]  = EitherT.leftT[F, B](a)
  }

}

object EitherSyntax extends EitherSyntax
