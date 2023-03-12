package extras.cats.syntax

import cats.{Applicative, FlatMap, Functor, Monad}
import cats.data.EitherT
import cats.syntax.either.*
import cats.syntax.foldable.*

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

    inline def innerFind(f: B => Boolean)(using F: Functor[F]): F[Option[B]] =
      F.map(fOfEither)(_.find(f))

    inline def innerFilterOrElse[C >: A](f: B => Boolean, leftIfFalse: => C)(using F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.filterOrElse(f, leftIfFalse))

    inline def innerExists(f: B => Boolean)(using F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.exists(f))

    inline def innerForall(f: B => Boolean)(using F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.forall(f))

    inline def innerContains(b: B)(using F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.contains(b))

    inline def innerCollectFirst[D](pf: PartialFunction[B, D])(using F: Functor[F]): F[Option[D]] =
      F.map(fOfEither)(_.collectFirst(pf))

    inline def innerMap[D](f: B => D)(using F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.map(f))

    inline def innerFlatMap[D](f: B => Either[A, D])(using F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.flatMap(f))

    inline def innerFlatMapF[D](f: B => F[Either[A, D]])(using F: Monad[F]): F[Either[A, D]] =
      F.flatMap(fOfEither) {
        case Left(a) => F.pure(a.asLeft[D])
        case Right(b) => f(b)
      }

    inline def innerLeftMap[C](f: A => C)(using F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.leftMap(f))

    inline def innerLeftFlatMap[C](f: A => Either[C, B])(using F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.leftFlatMap(f))

    inline def innerLeftFlatMapF[C](f: A => F[Either[C, B]])(using F: Monad[F]): F[Either[C, B]] =
      F.flatMap(fOfEither) {
        case Left(a) => f(a)
        case Right(b) => F.pure(b.asRight[C])
      }

    inline def innerGetOrElse[D >: B](ifLeft: => D)(using F: Functor[F]): F[D] =
      F.map(fOfEither)(_.getOrElse(ifLeft))

    inline def innerGetOrElseF[D >: B](ifLeft: => F[D])(using F: Monad[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(_ => ifLeft, F.pure))

    inline def innerOrElse[C >: A, D >: B](ifLeft: => Either[C, D])(using F: Functor[F]): F[Either[C, D]] =
      F.map(fOfEither)(_.orElse(ifLeft))

    inline def innerOrElseF[C >: A, D >: B](ifLeft: => F[Either[C, D]])(using F: Monad[F]): F[Either[C, D]] =
      F.flatMap(fOfEither) {
        case r @ Right(_) => F.pure(r)
        case Left(_) => ifLeft
      }

    inline def innerFold[D](forLeft: A => D)(forRight: B => D)(using F: Functor[F]): F[D] =
      F.map(fOfEither)(_.fold(forLeft, forRight))

    inline def innerFoldF[D](forLeft: A => F[D])(forRight: B => F[D])(using F: FlatMap[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(forLeft, forRight))
  }

}

object EitherSyntax extends EitherSyntax
