package extras.runner.ce2

import cats.effect.IO
import extras.runner.RunSync

/** @author Kevin Lee
  * @since 2022-11-27
  */
trait RunSyncCe2 {

  type F[A] = IO[A]
  val F: IO.type = IO

  implicit val RunSyncIo: RunSync[IO] = new RunSync[IO] {
    override def runSync[A](fa: IO[A]): A = fa.unsafeRunSync()
  }
}
