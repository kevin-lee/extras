package extras.cats.syntax

import EitherSyntax.{EitherTAOps, EitherTEitherOps, EitherTFAOps, EitherTFEitherOps, FOfEitherInnerOps}
import cats.data.EitherT
import cats.syntax.either._
import cats.syntax.foldable._
import cats.{Applicative, FlatMap, Functor, Monad}

/** @author Kevin Lee
  * @since 2021-07-21
  */
trait EitherSyntax {

  implicit final def eitherTFEitherOps[F[_], A, B](fOfEither: F[Either[A, B]]): EitherTFEitherOps[F, A, B] =
    new EitherTFEitherOps(fOfEither)

  implicit final def eitherTEitherOps[A, B](either: Either[A, B]): EitherTEitherOps[A, B] =
    new EitherTEitherOps(either)

  implicit final def eitherTFAOps[F[_], A](fa: F[A]): EitherTFAOps[F, A] = new EitherTFAOps(fa)

  implicit final def eitherTAOps[A](a: A): EitherTAOps[A] = new EitherTAOps(a)

  implicit final def fOfEitherInnerOps[F[_], A, B](fOfEither: F[Either[A, B]]): FOfEitherInnerOps[F, A, B] =
    new FOfEitherInnerOps(fOfEither)
}

object EitherSyntax {

  final class EitherTFEitherOps[F[_], A, B](private val fOfEither: F[Either[A, B]]) extends AnyVal {
    @inline def eitherT: EitherT[F, A, B] = EitherT[F, A, B](fOfEither)
    @inline def t: EitherT[F, A, B]       = eitherT
  }

  final class EitherTEitherOps[A, B](private val either: Either[A, B]) extends AnyVal {
    @inline def eitherT[F[_]: Applicative]: EitherT[F, A, B] = EitherT.fromEither[F](either)
    @inline def t[F[_]: Applicative]: EitherT[F, A, B]       = eitherT[F]
  }

  final class EitherTFAOps[F[_], A](private val fa: F[A]) extends AnyVal {
    @inline def rightT[B](implicit F: Functor[F]): EitherT[F, B, A] = EitherT.right[B](fa)
    @inline def leftT[B](implicit F: Functor[F]): EitherT[F, A, B]  = EitherT.left[B](fa)
  }

  final class EitherTAOps[A](private val a: A) extends AnyVal {
    @inline def rightTF[F[_]: Applicative, B]: EitherT[F, B, A] = EitherT.rightT[F, B](a)
    @inline def leftTF[F[_]: Applicative, B]: EitherT[F, A, B]  = EitherT.leftT[F, B](a)
  }

  final class FOfEitherInnerOps[F[_], A, B](private val fOfEither: F[Either[A, B]]) extends AnyVal {

    @inline def innerFind(f: B => Boolean)(implicit F: Functor[F]): F[Option[B]] =
      F.map(fOfEither)(_.find(f))

    @inline def innerFilterOrElse[C >: A](f: B => Boolean, leftIfFalse: => C)(implicit F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.filterOrElse(f, leftIfFalse))

    @inline def innerExists(f: B => Boolean)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.exists(f))

    @inline def innerForall(f: B => Boolean)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.forall(f))

    @inline def innerContains(b: B)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfEither)(_.contains(b))

    @inline def innerCollectFirst[D >: B](pf: PartialFunction[B, D])(implicit F: Functor[F]): F[Option[D]] =
      F.map(fOfEither)(_.collectFirst(pf))

    @inline def innerMap[D](f: B => D)(implicit F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.map(f))

    @inline def innerFlatMap[D](f: B => Either[A, D])(implicit F: Functor[F]): F[Either[A, D]] =
      F.map(fOfEither)(_.flatMap(f))

    @inline def innerFlatMapF[D](f: B => F[Either[A, D]])(implicit F: Monad[F]): F[Either[A, D]] =
      F.flatMap(fOfEither) {
        case Left(a) => F.pure(a.asLeft[D])
        case Right(b) => f(b)
      }

    @inline def innerLeftMap[C](f: A => C)(implicit F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.leftMap(f))

    @inline def innerLeftFlatMap[C](f: A => Either[C, B])(implicit F: Functor[F]): F[Either[C, B]] =
      F.map(fOfEither)(_.leftFlatMap(f))

    @inline def innerLeftFlatMapF[C](f: A => F[Either[C, B]])(implicit F: Monad[F]): F[Either[C, B]] =
      F.flatMap(fOfEither) {
        case Left(a) => f(a)
        case Right(b) => F.pure(b.asRight[C])
      }

    @inline def innerGetOrElse[D >: B](ifLeft: => D)(implicit F: Functor[F]): F[D] =
      F.map(fOfEither)(_.getOrElse(ifLeft))

    @inline def innerGetOrElseF[D >: B](ifLeft: => F[D])(implicit F: Monad[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(_ => ifLeft, F.pure))

    @inline def innerOrElse[C >: A, D >: B](ifLeft: => Either[C, D])(implicit F: Functor[F]): F[Either[C, D]] =
      F.map(fOfEither)(_.orElse(ifLeft))

    @inline def innerOrElseF[C >: A, D >: B](ifLeft: => F[Either[C, D]])(implicit F: Monad[F]): F[Either[C, D]] =
      F.flatMap(fOfEither) {
        case r @ Right(_) => F.pure(r)
        case Left(_) => ifLeft
      }

    @inline def innerFold[D](ifLeft: => D)(f: B => D)(implicit F: Functor[F]): F[D] =
      F.map(fOfEither)(_.fold(_ => ifLeft, f))

    @inline def innerFoldF[D](ifLeft: => F[D])(f: B => F[D])(implicit F: FlatMap[F]): F[D] =
      F.flatMap(fOfEither)(_.fold(_ => ifLeft, f))

  }

}
