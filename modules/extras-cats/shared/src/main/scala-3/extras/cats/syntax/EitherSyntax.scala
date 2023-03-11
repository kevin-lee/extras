package extras.cats.syntax

import cats.{Applicative, FlatMap, Functor, Monad}
import cats.data.EitherT
import cats.syntax.either.*

/** @author Kevin Lee
  * @since 2021-07-28
  */
trait EitherSyntax {

  extension [F[_], A, B](fOfEither: F[Either[A, B]]) {
    inline def eitherT: EitherT[F, A, B] = EitherT[F, A, B](fOfEither)
    inline def t: EitherT[F, A, B]       = eitherT
  }

  extension [A, B](either: Either[A, B]) {
    inline def eitherT[F[_]: Applicative]: EitherT[F, A, B] = EitherT.fromEither[F](either)
    inline def t[F[_]: Applicative]: EitherT[F, A, B]       = eitherT[F]
  }

  extension [F[_], A](fa: F[A]) {
    inline def rightT[B](using F: Functor[F]): EitherT[F, B, A] = EitherT.right[B](fa)
    inline def leftT[B](using F: Functor[F]): EitherT[F, A, B]  = EitherT.left[B](fa)
  }

  extension [A](a: A) {
    inline def rightTF[F[_]: Applicative, B]: EitherT[F, B, A] = EitherT.rightT[F, B](a)
    inline def leftTF[F[_]: Applicative, B]: EitherT[F, A, B]  = EitherT.leftT[F, B](a)
  }

  extension [F[_], A, B](fOfEither: F[Either[A, B]]) {
    inline def innerMap[D](f: B => D)(using F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.map(f))

    inline def innerFlatMap[D](f: B => Either[A, D])(using F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.flatMap(f))

    inline def innerFlatMapF[D](f: B => F[Either[A, D]])(using F: Monad[F]): F[Either[A, D]] =
      F.flatMap(fOfEither) {
        case Left(a) => F.pure(a.asLeft[D])
        case Right(b) => f(b)
      }

    inline def innerGetOrElse[D >: B](ifLeft: => D)(using F: Functor[F]): F[D] =
      F.map(fOfEither)(_.getOrElse(ifLeft))

    inline def innerGetOrElseF[D >: B](ifLeft: => F[D])(using F: Monad[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(_ => ifLeft, F.pure))

    inline def innerFold[D](ifLeft: => D)(f: B => D)(using F: Functor[F]): F[D] =
      F.map(fOfEither)(_.fold(_ => ifLeft, f))

    inline def innerFoldF[D](ifLeft: => F[D])(f: B => F[D])(using F: FlatMap[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(_ => ifLeft, f))
  }

}

object EitherSyntax extends EitherSyntax
