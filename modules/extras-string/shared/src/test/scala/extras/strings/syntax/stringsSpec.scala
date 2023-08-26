package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-22
  */
object stringsSpec extends Properties {
  override def tests: List[Test] = List(
    property("test List.empty[String].commaWith", testEmptyListCommaWith),
    property("test List(\"\").commaWith", testListWithSingleEmptyStringCommaWith),
    property("test List[String].commaWith", testCommaWith),
    //
    property("test List.empty[String].serialCommaWith", testEmptyListSerialCommaWith),
    property("test List(\"\").serialCommaWith", testListWithSingleEmptyStringSerialCommaWith),
    property("test List[String].serialCommaWith", testSerialCommaWith),
    //
    example("test List.empty[String].commaAnd", testEmptyListCommaAnd),
    example("test List(\"\").commaAnd", testListWithSingleEmptyStringCommaAnd),
    property("test List[String].commaAnd", testCommaAnd),
    //
    example("test List.empty[String].serialCommaAnd", testEmptyListSerialCommaAnd),
    example("test List(\"\").serialCommaAnd", testListWithSingleEmptyStringSerialCommaAnd),
    property("test List[String].serialCommaAnd", testSerialCommaAnd),
  )

  def testEmptyListCommaWith: Property = for {
    conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
  } yield {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List.empty[String].commaWith(conjunction)
    actual ==== expected
  }

  def testListWithSingleEmptyStringCommaWith: Property = for {
    conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
  } yield {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List("").commaWith(conjunction)
    actual ==== expected
  }

  def testCommaWith: Property =
    for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
      ss          <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
      last        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

      (list, expected) <- Gen
                            .constant(ss match {
                              case Nil =>
                                (List(last), last)
                              case _ =>
                                (ss ++ List(last), s"${ss.mkString(", ")} $conjunction $last")
                            })
                            .log("(list, expected)")
    } yield {
      import extras.strings.syntax.strings._

      val actual = list.commaWith(conjunction)
      actual ==== expected
    }

  ///

  def testEmptyListSerialCommaWith: Property = for {
    conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
  } yield {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List.empty[String].serialCommaWith(conjunction)
    actual ==== expected
  }

  def testListWithSingleEmptyStringSerialCommaWith: Property = for {
    conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
  } yield {
    import extras.strings.syntax.strings._

    val expected = ""
    val actual   = List("").serialCommaWith(conjunction)
    actual ==== expected
  }

  def testSerialCommaWith: Property =
    for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
      ss          <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
      last        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

      (list, expected) <- Gen
                            .constant(ss match {
                              case Nil =>
                                (List(last), last)

                              case s :: Nil =>
                                (ss ++ List(last), s"$s $conjunction $last")

                              case _ =>
                                (ss ++ List(last), s"${ss.mkString(", ")}, $conjunction $last")
                            })
                            .log("(list, expected)")
    } yield {
      import extras.strings.syntax.strings._

      val actual = list.serialCommaWith(conjunction)
      actual ==== expected
    }

  ///

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

  ///

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
