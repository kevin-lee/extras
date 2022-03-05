package extras.scala.io

/** @author Kevin Lee
  * @since 2021-09-20
  */
trait CanClose[-A] extends scala.util.Using.Releasable[A] {

  override def release(resource: A): Unit = close(resource)

  def close(a: A): Unit
}

object CanClose {

  def apply[A: CanClose]: CanClose[A] = implicitly[CanClose[A]]

  implicit val autoCloseableCanClose: CanClose[AutoCloseable] = _.close()

}
