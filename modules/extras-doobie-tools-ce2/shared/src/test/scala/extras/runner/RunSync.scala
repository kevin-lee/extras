package extras.runner

/** @author Kevin Lee
  * @since 2022-11-27
  */
trait RunSync[F[*]] {
  def runSync[A](fa: F[A]): A
}

object RunSync {
  def apply[F[*]: RunSync]: RunSync[F] = implicitly[RunSync[F]]
}
