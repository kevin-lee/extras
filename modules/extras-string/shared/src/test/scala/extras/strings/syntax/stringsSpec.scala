package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-22
  */
object stringsSpec extends Properties {
  override def tests: List[Test] = allStringsSpec(extras.strings.syntax.strings).tests

  final class allStringsSpec(strings: extras.strings.syntax.strings) {
    import strings._

    def tests: List[Test] = List(
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
      //
      example("test List.empty[String].commaOr", testEmptyListCommaOr),
      example("test List(\"\").commaOr", testListWithSingleEmptyStringCommaOr),
      property("test List[String].commaOr", testCommaOr),
      //
      example("test List.empty[String].serialCommaOr", testEmptyListSerialCommaOr),
      example("test List(\"\").serialCommaOr", testListWithSingleEmptyStringSerialCommaOr),
      property("test List[String].serialCommaOr", testSerialCommaOr),
    )

    def testEmptyListCommaWith: Property = for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
    } yield {

      val expected = ""
      val actual   = List.empty[String].commaWith(conjunction)
      actual ==== expected
    }

    def testListWithSingleEmptyStringCommaWith: Property = for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
    } yield {

      val expected = ""
      val actual   = List("").commaWith(conjunction)
      actual ==== expected
    }

    def testCommaWith: Property =
      for {
        conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
        ss          <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)
                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")} $conjunction $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.commaWith(conjunction)
        actual ==== expected
      }

    ///

    def testEmptyListSerialCommaWith: Property = for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
    } yield {

      val expected = ""
      val actual   = List.empty[String].serialCommaWith(conjunction)
      actual ==== expected
    }

    def testListWithSingleEmptyStringSerialCommaWith: Property = for {
      conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
    } yield {

      val expected = ""
      val actual   = List("").serialCommaWith(conjunction)
      actual ==== expected
    }

    def testSerialCommaWith: Property =
      for {
        conjunction <- Gen.string(Gen.alphaNum, Range.linear(1, 5)).log("conjunction")
        ss          <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)

                               case s :: Nil =>
                                 (ss ++ List(last), s"$s $conjunction $last")

                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")}, $conjunction $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.serialCommaWith(conjunction)
        actual ==== expected
      }

    ///

    def testEmptyListCommaAnd: Result = {

      val expected = ""
      val actual   = List.empty[String].commaAnd
      actual ==== expected
    }

    def testListWithSingleEmptyStringCommaAnd: Result = {

      val expected = ""
      val actual   = List("").commaAnd
      actual ==== expected
    }

    def testCommaAnd: Property =
      for {
        ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)
                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")} and $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.commaAnd
        actual ==== expected
      }

    ///

    def testEmptyListSerialCommaAnd: Result = {

      val expected = ""
      val actual   = List.empty[String].serialCommaAnd
      actual ==== expected
    }

    def testListWithSingleEmptyStringSerialCommaAnd: Result = {

      val expected = ""
      val actual   = List("").serialCommaAnd
      actual ==== expected
    }

    def testSerialCommaAnd: Property =
      for {
        ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)
                               case s :: Nil =>
                                 (ss ++ List(last), s"$s and $last")
                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")}, and $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.serialCommaAnd
        actual ==== expected
      }

    ///

    def testEmptyListCommaOr: Result = {

      val expected = ""
      val actual   = List.empty[String].commaOr
      actual ==== expected
    }

    def testListWithSingleEmptyStringCommaOr: Result = {

      val expected = ""
      val actual   = List("").commaOr
      actual ==== expected
    }

    def testCommaOr: Property =
      for {
        ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)
                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")} or $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.commaOr
        actual ==== expected
      }

    ///

    def testEmptyListSerialCommaOr: Result = {

      val expected = ""
      val actual   = List.empty[String].serialCommaOr
      actual ==== expected
    }

    def testListWithSingleEmptyStringSerialCommaOr: Result = {

      val expected = ""
      val actual   = List("").serialCommaOr
      actual ==== expected
    }

    def testSerialCommaOr: Property =
      for {
        ss   <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).list(Range.linear(0, 5)).log("ss")
        last <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("last")

        listAndExpected <- Gen
                             .constant(ss match {
                               case Nil =>
                                 (List(last), last)
                               case s :: Nil =>
                                 (ss ++ List(last), s"$s or $last")
                               case _ =>
                                 (ss ++ List(last), s"${ss.mkString(", ")}, or $last")
                             })
                             .log("(list, expected)")
        (list, expected) = listAndExpected
      } yield {

        val actual = list.serialCommaOr
        actual ==== expected
      }
  }
  object allStringsSpec {
    def apply(strings: extras.strings.syntax.strings): allStringsSpec = new allStringsSpec(strings)
  }
}
