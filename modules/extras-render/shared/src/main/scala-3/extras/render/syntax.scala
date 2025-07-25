package extras.render

import extras.render.Render.Rendered

import scala.annotation.tailrec
import scala.collection.mutable

/** @author Kevin Lee
  * @since 2022-10-15
  */
trait syntax {

  extension [A](a: A) {
    def render(using R: Render[A]): String = R.render(a)
  }

  extension [A](as: Iterable[A]) {
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

  extension (stringContext: StringContext) {
    def render(args: Rendered*): String = stringContext.s(args: _*)
  }
}
object syntax extends syntax
