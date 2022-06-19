package extras.scala.io.syntax

/** @author Kevin Lee
  * @since 2022-06-13
  */
package object truecolor {
  object rgb extends RgbSyntax
  object rainbow extends RainbowSyntax
  object all extends RgbSyntax with RainbowSyntax
}
