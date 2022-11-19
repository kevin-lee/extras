package extras.render

import scala.annotation.tailrec
import scala.collection.mutable

/** @author Kevin Lee
  * @since 2022-10-15
  */
trait syntax {
  import syntax._
  implicit def renderSyntaxA[A](a: A): RenderSyntaxA[A] = new RenderSyntaxA[A](a)

  implicit def renderSyntaxIterable[A](as: Iterable[A]): RenderSyntaxIterable[A] = new RenderSyntaxIterable[A](as)

  implicit def renderInterpolator(stringContext: StringContext): Render.RenderInterpolator =
    Render.RenderInterpolator(stringContext)
}
object syntax extends syntax {
  final class RenderSyntaxA[A](private val a: A) extends AnyVal {
    def render(implicit R: Render[A]): String = R.render(a)
  }

  final class RenderSyntaxIterable[A](private val as: Iterable[A]) extends AnyVal {
    @SuppressWarnings(
      Array(
        "org.wartremover.warts.Overloading",
        "org.wartremover.warts.NonUnitStatements",
        "org.wartremover.warts.MutableDataStructures",
      )
    )
    def renderString(start: String, sep: String, end: String)(implicit R: Render[A]): String = {
      val builder = new mutable.StringBuilder()
      if (start.nonEmpty) builder.append(start)

      @tailrec
      def loop(iterator: Iterator[A], sep: String, builder: mutable.StringBuilder): mutable.StringBuilder =
        if (iterator.hasNext)
          loop(
            iterator,
            sep,
            builder
              .append(sep)
              .append(R.render(iterator.next())),
          )
        else
          builder

      val iterator = as.iterator
      if (iterator.hasNext) {
        builder.append(R.render(iterator.next()))
        loop(iterator, sep, builder)
      }

      if (end.nonEmpty) builder.append(end)
      builder.result()
    }

    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    @inline def renderString(sep: String)(implicit R: Render[A]): String =
      renderString("", sep, "")

    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    @inline def renderString(implicit R: Render[A]): String =
      renderString("")
  }

}
