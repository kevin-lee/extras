package extras.cats.syntax

import cats.data.OptionT
import cats.syntax.option._
import cats.{Applicative, FlatMap, Functor, Monad}
import extras.cats.syntax.OptionSyntax._

/** @author Kevin Lee
  * @since 2021-07-22
  */
trait OptionSyntax {

  implicit def optionTFOptionOps[F[_], A](fOfOption: F[Option[A]]): OptionTFOptionOps[F, A] =
    new OptionTFOptionOps(fOfOption)

  implicit def optionTOptionOps[A](option: Option[A]): OptionTOptionOps[A] =
    new OptionTOptionOps(option)

  implicit def optionTFAOps[F[_], A](fa: F[A]): OptionTFAOps[F, A] = new OptionTFAOps(fa)

  implicit def optionTAOps[A](a: A): OptionTAOps[A] = new OptionTAOps(a)

  implicit def fOfOptionInnerOps[F[_], A](fOfOption: F[Option[A]]): FOfOptionInnerOps[F, A] =
    new FOfOptionInnerOps(fOfOption)

}

object OptionSyntax {

  final class OptionTFOptionOps[F[_], A](private val fOfOption: F[Option[A]]) extends AnyVal {
    @inline def optionT: OptionT[F, A] = OptionT[F, A](fOfOption)
    @inline def t: OptionT[F, A]       = optionT
  }

  final class OptionTOptionOps[A](private val option: Option[A]) extends AnyVal {
    @inline def optionT[F[_]: Applicative]: OptionT[F, A] = OptionT.fromOption[F](option)
    @inline def t[F[_]: Applicative]: OptionT[F, A]       = optionT[F]
  }

  final class OptionTFAOps[F[_], A](private val fa: F[A]) extends AnyVal {
    @inline def someT(implicit F: Functor[F]): OptionT[F, A] = OptionT.liftF(fa)
  }

  final class OptionTAOps[A](private val a: A) extends AnyVal {
    @inline def someTF[F[_]: Applicative]: OptionT[F, A] = OptionT.some[F](a)
  }

  final class FOfOptionInnerOps[F[_], A](private val fOfOption: F[Option[A]]) extends AnyVal {

    @inline def innerFilter(f: A => Boolean)(implicit F: Functor[F]): F[Option[A]] =
      F.map(fOfOption)(_.filter(f))

    @inline def innerExists(f: A => Boolean)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.exists(f))

    @inline def innerContains(a: A)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.contains(a))

    @inline def innerForall(f: A => Boolean)(implicit F: Functor[F]): F[Boolean] =
      F.map(fOfOption)(_.forall(f))

    @inline def innerCollect[B](pf: PartialFunction[A, B])(implicit F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.collect(pf))

    @inline def innerMap[B](f: A => B)(implicit F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.map(f))

    @inline def innerFlatMap[B](f: A => Option[B])(implicit F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.flatMap(f))

    @inline def innerFlatMapF[B](f: A => F[Option[B]])(implicit F: Monad[F]): F[Option[B]] =
      F.flatMap(fOfOption)(oa => oa.fold(F.pure(none[B]))(f))

    @inline def innerGetOrElse[B >: A](ifEmpty: => B)(implicit F: Functor[F]): F[B] =
      F.map(fOfOption)(_.getOrElse(ifEmpty))

    @inline def innerGetOrElseF[B >: A](ifEmpty: => F[B])(implicit F: Monad[F]): F[B] =
      F.flatMap(fOfOption)(_.fold(ifEmpty)(F.pure))

    @inline def innerOrElse[B >: A](alternative: => Option[B])(implicit F: Functor[F]): F[Option[B]] =
      F.map(fOfOption)(_.orElse(alternative))

    @inline def innerOrElseF[B >: A](alternative: => F[Option[B]])(implicit F: Monad[F]): F[Option[B]] =
      F.flatMap(fOfOption) {
        case someA @ Some(_) => F.pure(someA)
        case None => alternative
      }

    @inline def innerFold[B](ifEmpty: => B)(f: A => B)(implicit F: Functor[F]): F[B] =
      F.map(fOfOption)(_.fold(ifEmpty)(f))

    @inline def innerFoldF[B](ifEmpty: => F[B])(f: A => F[B])(implicit F: FlatMap[F]): F[B] =
      F.flatMap(fOfOption)(_.fold(ifEmpty)(f))

    @inline def innerForeach(f: A => Unit)(implicit F: Functor[F]): F[Unit] =
      F.map(fOfOption)(_.foreach(f))

    @inline def innerForeachF(f: A => F[Unit])(implicit F: FlatMap[F], AP: Applicative[F]): F[Unit] =
      F.flatMap(fOfOption)(_.fold(Applicative[F].unit)(f))

  }

}
