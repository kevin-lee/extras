package extras.core.syntax

import hedgehog.runner._

/** @author Kevin Lee
  * @since 2025-12-27
  */
object allSpec extends Properties with CommonStringsSpec with CommonPredefsSpec {

  override val stringsImport: strings = extras.core.syntax.all

  override val predefsImport: predefs = extras.core.syntax.all

  override def tests: List[Test] = super[CommonStringsSpec].tests ++ super[CommonPredefsSpec].tests
}
