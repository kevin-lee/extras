package extras.cats.syntax

import cats.data.OptionT
import cats.{Applicative, Functor}
import extras.cats.syntax.OptionSyntax.{OptionTAOps, OptionTFAOps, OptionTFOptionOps, OptionTOptionOps}

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

}

object OptionSyntax {

  final class OptionTFOptionOps[F[_], A](private val fOfOption: F[Option[A]]) extends AnyVal {
    def optionT: OptionT[F, A] = OptionT[F, A](fOfOption)
  }

  final class OptionTOptionOps[A](private val option: Option[A]) extends AnyVal {
    def optionT[F[_]: Applicative]: OptionT[F, A] = OptionT.fromOption[F](option)
  }

  final class OptionTFAOps[F[_], A](private val fa: F[A]) extends AnyVal {
    def someT(implicit F: Functor[F]): OptionT[F, A] = OptionT.liftF(fa)
  }

  final class OptionTAOps[A](private val a: A) extends AnyVal {
    def someTF[F[_]: Applicative]: OptionT[F, A] = OptionT.some[F](a)
  }

}
