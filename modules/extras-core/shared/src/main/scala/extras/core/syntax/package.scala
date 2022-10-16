package extras.core

/** @author Kevin Lee
  * @since 2022-06-25
  */
package object syntax {
  object string extends StringSyntax

  object render extends RenderSyntax

  object all extends StringSyntax with RenderSyntax
}
