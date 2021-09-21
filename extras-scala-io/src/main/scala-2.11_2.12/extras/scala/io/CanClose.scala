package extras.scala.io

/** @author Kevin Lee
  * @since 2021-09-20
  */
trait CanClose[-A] {

  def close(a: A): Unit
}

object CanClose {

  def apply[A: CanClose]: CanClose[A] = implicitly[CanClose[A]]

  implicit final val autoCloseableCanClose: CanClose[AutoCloseable] = new CanClose[AutoCloseable] {
    override def close(a: AutoCloseable): Unit = a.close()
  }

}
