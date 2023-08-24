package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-22
  */
object stringsSpec extends Properties {
  override def tests: List[Test] = List(
    example("test List[String].empty.commaAnd", testEmptyListCommaAnd),
    example("test List(\"\").commaAnd", testListWithSingleEmptyStringCommaAnd),
    property("test List[String].commaAnd", testCommaAnd),
    example("test List[String].empty.serialCommaAnd", testEmptyListSerialCommaAnd),
    example("test List(\"\").serialCommaAnd", testListWithSingleEmptyStringSerialCommaAnd),
    property("test List[String].serialCommaAnd", testSerialCommaAnd),
  )

  def testEmptyListCommaAnd: Result = {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List.empty[String].commaAnd
    actual ==== expected
  }

  def testListWithSingleEmptyStringCommaAnd: Result = {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List("").commaAnd
    actual ==== expected
  }

  def testCommaAnd: Property =
    for {
      ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
      last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

      (list, expected) <- Gen
                            .constant(ss match {
                              case Nil =>
                                (List(last), last)
                              case _ =>
                                (ss ++ List(last), s"${ss.mkString(", ")} and $last")
                            })
                            .log("(list, expected)")
    } yield {
      import extras.strings.syntax.strings._

      val actual = list.commaAnd
      actual ==== expected
    }

  def testEmptyListSerialCommaAnd: Result = {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List.empty[String].serialCommaAnd
    actual ==== expected
  }

  def testListWithSingleEmptyStringSerialCommaAnd: Result = {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List("").serialCommaAnd
    actual ==== expected
  }

  def testSerialCommaAnd: Property =
    for {
      ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
      last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

      (list, expected) <- Gen
                            .constant(ss match {
                              case Nil =>
                                (List(last), last)
                              case s :: Nil =>
                                (ss ++ List(last), s"$s and $last")
                              case _ =>
                                (ss ++ List(last), s"${ss.mkString(", ")}, and $last")
                            })
                            .log("(list, expected)")
    } yield {
      import extras.strings.syntax.strings._

      val actual = list.serialCommaAnd
      actual ==== expected
    }

}
