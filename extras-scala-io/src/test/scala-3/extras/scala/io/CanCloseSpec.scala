package extras.scala.io

import hedgehog.*
import hedgehog.runner.*

import scala.io.Source
import scala.util.Using

/** @author Kevin Lee
  * @since 2021-09-20
  */
object CanCloseSpec extends Properties {
  override def tests: List[Test] = List(
    example("testAutoCloseableCanClose", testAutoCloseableCanClose),
    example("testSourceCanClose", testSourceCanClose),
    example("testMyResourceCanClose", testMyResourceCanClose),
    example("testMyResourceCanCloseWithUsing", testMyResourceCanCloseWithUsing)
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
    implicit final val myResourceCanClose: CanClose[MyResource] =
      _.closed = true
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

  def testSourceCanClose: Result = {
    var result: Option[String] = None

    val expected = "abcde"

    val autoCloseable = new Source {
      override protected val iter: Iterator[Char] = expected.iterator
    }.withClose({ () =>
      result = Some("Closed")
      ()
    })

    val actual = Using.resource(autoCloseable)(_.getLines().mkString)

    Result.all(
      List(
        actual ==== expected,
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

  def testMyResourceCanCloseWithUsing: Result = {

    val expected = "blah"

    val autoCloseable = MyResource(expected)

    val closedBefore = autoCloseable.closed ==== false

    val actual = Using.resource(autoCloseable)(_.value)

    Result.all(
      List(
        closedBefore,
        actual ==== expected,
        autoCloseable.closed ==== true
      )
    )
  }

}
