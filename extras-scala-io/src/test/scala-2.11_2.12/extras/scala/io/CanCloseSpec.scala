package extras.scala.io

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-09-20
  */
object CanCloseSpec extends Properties {
  override def tests: List[Test] = List(
    example("testAutoCloseableCanClose", testAutoCloseableCanClose),
    example("testMyResourceCanClose", testMyResourceCanClose)
  )

  def withCanClose[A: CanClose, B](a: A)(f: A => B): B =
    try f(a)
    finally CanClose[A].close(a)

  final case class MyResource(value: String) {
    private[this] var _closed: Boolean     = false
    def closed: Boolean                    = _closed
    def closed_=(newClosed: Boolean): Unit = {
      _closed = newClosed
      ()
    }
  }
  object MyResource                          {
    implicit final val myResourceCanClose: CanClose[MyResource] = new CanClose[MyResource] {
      override def close(a: MyResource): Unit = a.closed = true
    }
  }

  def testAutoCloseableCanClose: Result = {
    var result: Option[String] = None
    val autoCloseable          = new AutoCloseable {
      override def close(): Unit = {
        result = Some("Closed")
        ()
      }
    }

    val actual = withCanClose(autoCloseable)(_ => "Done")

    Result.all(
      List(
        actual ==== "Done",
        result ==== Some("Closed")
      )
    )
  }

  def testMyResourceCanClose: Result = {

    val expected = "blah"

    val autoCloseable = MyResource(expected)

    val closedBefore = autoCloseable.closed ==== false

    val actual = withCanClose(autoCloseable)(_.value)

    Result.all(
      List(
        closedBefore,
        actual ==== expected,
        autoCloseable.closed ==== true
      )
    )
  }

}
