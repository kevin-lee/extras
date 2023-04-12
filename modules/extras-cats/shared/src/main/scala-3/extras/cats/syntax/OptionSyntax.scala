package extras.cats.syntax

import cats.{Applicative, FlatMap, Functor, Monad}
import cats.syntax.option.*
import cats.data.OptionT

/** @author Kevin Lee
  * @since 2021-07-22
  */
trait OptionSyntax {

  extension [F[_], A](fOfOption: F[Option[A]]) {
    inline def optionT: OptionT[F, A] = OptionT[F, A](fOfOption)
    inline def t: OptionT[F, A]       = optionT
  }

  extension [A](option: Option[A]) {
    inline def optionT[F[_]: Applicative]: OptionT[F, A] = OptionT.fromOption[F](option)
    inline def t[F[_]: Applicative]: OptionT[F, A]       = optionT[F]
  }

  extension [F[_], A](fa: F[A]) {
    inline def someT(using F: Functor[F]): OptionT[F, A] = OptionT.liftF(fa)
  }

  extension [A](a: A) {
    inline def someTF[F[_]: Applicative]: OptionT[F, A] = OptionT.some[F](a)
  }

  extension [F[_], A](fOfOption: F[Option[A]]) {

    inline def innerFilter(f: A => Boolean)(using F: Functor[F]): F[Option[A]] =
      F.map(fOfOption)(_.filter(f))

    inline def innerExists(f: A => Boolean)(using F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.exists(f))

    inline def innerContains(a: A)(using F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.contains(a))

    inline def innerForall(f: A => Boolean)(using F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.forall(f))

    inline def innerCollect[B](pf: PartialFunction[A, B])(using F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.collect(pf))

    inline def innerMap[B](f: A => B)(using F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.map(f))

    inline def innerFlatMap[B](f: A => Option[B])(using F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.flatMap(f))

    inline def innerFlatMapF[B](f: A => F[Option[B]])(using F: Monad[F]): F[Option[B]] =
      F.flatMap(fOfOption)(oa => oa.fold(F.pure(none[B]))(f))

    inline def innerGetOrElse[B >: A](ifEmpty: => B)(using F: Functor[F]): F[B] =
      F.map(fOfOption)(_.getOrElse(ifEmpty))

    inline def innerGetOrElseF[B >: A](ifEmpty: => F[B])(using F: Monad[F]): F[B] =
      F.flatMap(fOfOption)(_.fold(ifEmpty)(F.pure))

    inline def innerOrElse[B >: A](alternative: => Option[B])(using F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.orElse(alternative))

    inline def innerOrElseF[B >: A](alternative: => F[Option[B]])(using F: Monad[F]): F[Option[B]] =
      F.flatMap(fOfOption) {
        case someA @ Some(_) => F.pure(someA)
        case None => alternative
      }

    inline def innerFold[B](ifEmpty: => B)(f: A => B)(using F: Functor[F]): F[B] =
      F.map(fOfOption)(_.fold(ifEmpty)(f))

    inline def innerFoldF[B](ifEmpty: => F[B])(f: A => F[B])(using F: FlatMap[F]): F[B] =
      F.flatMap(fOfOption)(_.fold(ifEmpty)(f))

    inline def innerForeach(f: A => Unit)(using F: Functor[F]): F[Unit] =
      F.map(fOfOption)(_.foreach(f))

    inline def innerForeachF(f: A => F[Unit])(using F: FlatMap[F], AP: Applicative[F]): F[Unit] =
      F.flatMap(fOfOption)(_.fold(Applicative[F].unit)(f))

  }

}
