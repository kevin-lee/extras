package extras.testing

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CompileTimeError {
  def from(code: String): String = macro MacroCompileTimeError.fromImpl
}
