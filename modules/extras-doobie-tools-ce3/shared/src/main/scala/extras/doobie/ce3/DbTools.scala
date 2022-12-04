package extras.doobie.ce3

import cats.effect.MonadCancelThrow
import doobie.syntax.all.toConnectionIOOps
import doobie.util.Write
import doobie.{Fragment, Read, Transactor, Update}

/** @author Kevin Lee
  * @since 2022-11-27
  */
object DbTools {

  def fetchSingleRow[F[*]]: PartialyAppliedSingleRowFetcher[F] = new PartialyAppliedSingleRowFetcher[F]

  def fetchMultipleRows[F[*]]: PartialyAppliedMultipleRowsFetcher[F] = new PartialyAppliedMultipleRowsFetcher[F]

  def updateSingle[F[*]]: PartialyAppliedSingleUpdater[F] = new PartialyAppliedSingleUpdater[F]

  def updateMultiple[F[*]]: PartialyAppliedUpdaterMultiParams[F] = new PartialyAppliedUpdaterMultiParams[F]

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final class PartialyAppliedSingleRowFetcher[F[*]](private val dummy: Boolean = false) extends AnyVal {

    def apply[A: Read](
      fragment: Fragment
    )(transactor: Transactor[F])(implicit monadCancelThrow: MonadCancelThrow[F]): F[Option[A]] =
      fragment
        .query[A]
        .option
        .transact(transactor)
  }

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final class PartialyAppliedMultipleRowsFetcher[F[*]](private val dummy: Boolean = false) extends AnyVal {

    def apply[A: Read](
      fragment: Fragment
    )(transactor: Transactor[F])(implicit monadCancelThrow: MonadCancelThrow[F]): F[List[A]] =
      fragment
        .query[A]
        .to[List]
        .transact(transactor)
  }

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final class PartialyAppliedSingleUpdater[F[*]](private val dummy: Boolean = false) extends AnyVal {
    def apply(fragment: Fragment)(transactor: Transactor[F])(
      implicit monadCancelThrow: MonadCancelThrow[F]
    ): F[Int] =
      fragment
        .update
        .run
        .transact(transactor)
  }

  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final class PartialyAppliedUpdaterMultiParams[F[*]](private val dummy: Boolean = false) extends AnyVal {
    def apply[A: Write](sql: String)(a: List[A])(transactor: Transactor[F])(
      implicit monadCancelThrow: MonadCancelThrow[F]
    ): F[Int] =
      Update[A](sql)
        .updateMany(a)
        .transact(transactor)
  }

}
