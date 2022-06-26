package extras.scala.io

import extras.core.syntax.string._
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-09-19
  */
object ColorSpec extends Properties {

  override def tests: List[Test] = List(
    example("test all Color.toAnsi", ColorTestUtils.testColorToAnsi),
    property("test Color.color(non empty String)", testColor),
    property("test Color.color(an empty String)", testColorEmpty),
    property("test Color.colored(non empty String)", testColored),
    property("test Color.colored(an empty String)", testColoredEmpty),
    property("test Color.show", testColorShow),
    property("test Color.render(color)", testColorRender)
  )

  def testColor: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, _) = colorAndAnsi
    s <- Gen.string(Gen.alphaNum, Range.linear(1, 20)).log("s")
  } yield {
    val expected = s"${color.toAnsi}$s"
    val actual   = color.color(s)
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be ${expected.encodeToUnicode}""")
  }

  def testColorEmpty: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, _) = colorAndAnsi
  } yield {
    val expected = ""
    val actual   = color.color("")
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be "" (an empty String)""")
  }

  def testColored: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, _) = colorAndAnsi
    s <- Gen.string(Gen.alphaNum, Range.linear(1, 20)).log("s")
  } yield {
    val expected = s"${color.toAnsi}$s${Color.reset.toAnsi}"
    val actual   = color.colored(s)
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be ${expected.encodeToUnicode}""")
  }

  def testColoredEmpty: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, _) = colorAndAnsi
  } yield {
    val expected = ""
    val actual   = color.colored("")
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be "" (an empty String)""")
  }

  def testColorShow: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, _) = colorAndAnsi
  } yield {
    @SuppressWarnings(Array("org.wartremover.warts.ToString"))
    val expected = color.toString
    val actual   = color.show
    actual ==== expected
  }

  def testColorRender: Property = for {
    colorAndAnsi <- ColorGens.genColor.log("(color, ansi)")
    (color, ansi) = colorAndAnsi
  } yield {
    val expected = color.toAnsi
    val actual   = Color.render(color)
    actual ==== expected
  }

}
