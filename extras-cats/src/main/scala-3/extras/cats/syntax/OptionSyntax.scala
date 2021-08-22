package extras.cats.syntax

import cats.{Applicative, Functor}
import cats.data.OptionT

/**
 * @author Kevin Lee
 * @since 2021-07-22
 */
trait OptionSyntax {

  extension [F[_], A](fOfOption: F[Option[A]]) {
    def optionT: OptionT[F, A] = OptionT[F, A](fOfOption)
  }

  extension [A](option: Option[A]) {
    def optionT[F[_]: Applicative]: OptionT[F, A] = OptionT.fromOption[F](option)
  }

  extension [F[_], A](fa: F[A]) {
    def someT(using F: Functor[F]): OptionT[F, A] = OptionT.liftF(fa)
  }

  extension [A](a: A) {
    def someTF[F[_]: Applicative]: OptionT[F, A] = OptionT.some[F](a)
  }

}
