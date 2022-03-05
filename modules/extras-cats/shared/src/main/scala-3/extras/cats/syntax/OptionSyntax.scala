package extras.cats.syntax

import cats.{Applicative, Functor}
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

}
