package extras.testing

import scala.annotation.nowarn
import scala.reflect.macros.ParseException
import scala.reflect.macros.TypecheckException
import scala.reflect.macros.blackbox.Context

/** This is from munit's MacroCompatScala2.compileErrorsImpl
  *   https://github.com/scalameta/munit/blob/effba46e485b718b03ce215835450c7e51a381f0/munit/shared/src/main/scala/munit/internal/MacroCompatScala2.scala#L60-L86
  * @author Kevin Lee
  * @since 2025-07-28
  */
object MacroCompileTimeError {

  def fromImpl(c: Context)(code: c.Tree): c.Tree = {
    import c.universe._
    val toParse: String = code match {
      case Literal(Constant(literal: String)) => literal
      case _ =>
        c.abort(
          code.pos,
          "cannot compile dynamic expressions, only constant literals.\n" +
            "To fix this problem, pass in a string literal in double quotes \"...\"",
        )
    }

    @nowarn("msg=deprecated")
    def formatError(message: String, pos: scala.reflect.api.Position): String =
      new StringBuilder()
        .append("error:")
        .append(if (message.contains('\n')) "\n" else " ")
        .append(message)
        .append("\n")
        .append(pos.lineContent)
        .append("\n")
        .append(" " * (pos.column - 1))
        .append("^")
        .toString()

    val message: String =
      try {
        val _ = c.typecheck(c.parse(s"{\n$toParse\n}"))
        ""
      } catch {
        case e: ParseException => formatError(e.getMessage(), e.pos)
        case e: TypecheckException => formatError(e.getMessage(), e.pos)
      }
    Literal(Constant(message))
  }

}
