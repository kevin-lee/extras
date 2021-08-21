package extras.cats.syntax

import cats.{Applicative, Functor}
import cats.data.EitherT
import EitherSyntax.{EitherTAOps, EitherTEitherOps, EitherTFAOps, EitherTFEitherOps}

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

}

object EitherSyntax {

  final class EitherTFEitherOps[F[_], A, B](private val fOfEither: F[Either[A, B]]) extends AnyVal {
    def eitherT: EitherT[F, A, B] = EitherT[F, A, B](fOfEither)
  }

  final class EitherTEitherOps[A, B](private val either: Either[A, B]) extends AnyVal {
    def eitherT[F[_]: Applicative]: EitherT[F, A, B] = EitherT.fromEither[F](either)
  }

  final class EitherTFAOps[F[_], A](private val fa: F[A]) extends AnyVal {
    def rightT[B](implicit F: Functor[F]): EitherT[F, B, A] = EitherT.right[B](fa)
    def leftT[B](implicit F: Functor[F]): EitherT[F, A, B]  = EitherT.left[B](fa)
  }

  final class EitherTAOps[A](private val a: A) extends AnyVal {
    def rightTF[F[_]: Applicative, B]: EitherT[F, B, A] = EitherT.rightT[F, B](a)
    def leftTF[F[_]: Applicative, B]: EitherT[F, A, B]  = EitherT.leftT[F, B](a)
  }

}
