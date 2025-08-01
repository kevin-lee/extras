package extras.render

import extras.render.Render.Rendered

import scala.annotation.tailrec
import scala.collection.mutable

/** @author Kevin Lee
  * @since 2022-10-15
  */
trait syntax {
  import syntax.*

  implicit def renderSyntaxA[A](a: A): RenderSyntaxA[A] = new RenderSyntaxA[A](a)

  implicit def renderSyntaxIterable[A](as: Iterable[A]): RenderSyntaxIterable[A] = new RenderSyntaxIterable[A](as)

  extension (stringContext: StringContext) {
    def render(args: Rendered*): String = stringContext.s(args*)
  }
}
object syntax extends syntax {
  final class RenderSyntaxA[A](private val a: A) extends AnyVal {
    def render(implicit R: Render[A]): String = R.render(a)
  }

  final class RenderSyntaxIterable[A](private val as: Iterable[A]) extends AnyVal {
    /* WARNING: This method uses mutable data structure internally for performance */
    @SuppressWarnings(
      Array(
        "org.wartremover.warts.Overloading",
        "org.wartremover.warts.NonUnitStatements",
        "org.wartremover.warts.MutableDataStructures",
      )
    )
    def renderString(start: String, sep: String, end: String)(using R: Render[A]): String = {
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

      val theFinalBuilder = if (iterator.hasNext) {
        builder.append(R.render(iterator.next()))
        loop(iterator, sep, builder)
      } else {
        builder
      }

      if (end.nonEmpty) theFinalBuilder.append(end)
      theFinalBuilder.result()
    }

    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    inline def renderString(sep: String)(using R: Render[A]): String =
      renderString("", sep, "")

    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    inline def renderString(using R: Render[A]): String =
      renderString("")
  }

}
