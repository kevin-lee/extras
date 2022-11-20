package extras.typeinfo.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-03-14
  */
object classesSpec extends Properties {
  override def tests: List[Test] = List(
    example("test A.fullClassName", testAFullClassName),
    example("test A.nestedClassName", testANestedClassName),
    example("test Class[A].nestedClassName", testClassANestedClassName),
  ) ++ List[(String, String => Property)](
    ("test [%INDEX%] classes.getNestedName(a.b.[.?]*)", testClassesGetNestedName1),
    ("test [%INDEX%] classes.getNestedName(a.b.[.?]*$)", testClassesGetNestedName2),
    ("test [%INDEX%] classes.getNestedName(a.b$[.?]*)", testClassesGetNestedName3),
    ("test [%INDEX%] classes.getNestedName(a.b$[.?]*$)", testClassesGetNestedName4),
    ("test [%INDEX%] classes.getNestedName(a.b)", testClassesGetNestedName5),
    ("test [%INDEX%] classes.getNestedName(a)", testClassesGetNestedName6),
    ("test [%INDEX%] classes.getNestedName(a.b$)", testClassesGetNestedName7),
    ("test [%INDEX%] classes.getNestedName(a$)", testClassesGetNestedName8),
    ("test [%INDEX%] classes.getNestedName(a$b)", testClassesGetNestedName9),
    ("test [%INDEX%] classes.getNestedName(a$b$)", testClassesGetNestedName10),
  ).zipWithIndex.map {
    case ((name, test), index) =>
      val testName = name.replace("%INDEX%", index.toString)
      property(testName, test(testName))
  }

  import extras.typeinfo.syntax.classes._

  private val thisClassName = getClass.getName.stripSuffix("$")

  def testAFullClassName: Result = {
    import SomeTestTypes._
    val expected1 = s"$thisClassName.SomeTestTypes.Aaa.Bbb"
    val actual1a  = Aaa.bbb(123).fullClassName
    val expected2 = s"$thisClassName.SomeTestTypes.Aaa.Ccc"
    val actual2a  = Aaa.ccc.fullClassName
    val expected3 = s"$thisClassName.SomeTestTypes.Something"
    val actual3a  = Something("abc").fullClassName
    val expected4 = s"$thisClassName.SomeTestTypes"
    val actual4a  = SomeTestTypes.fullClassName

    Result.all(
      List(
        actual1a ==== expected1,
        actual2a ==== expected2,
        actual3a ==== expected3,
        actual4a ==== expected4,
      )
    )

  }

  def testANestedClassName: Result = {
    import SomeTestTypes._
    val expected1 = "Aaa.Bbb"
    val actual1a  = Aaa.bbb(123).nestedClassName
    val expected2 = "Aaa.Ccc"
    val actual2a  = Aaa.ccc.nestedClassName
    val expected3 = "SomeTestTypes.Something"
    val actual3a  = Something("abc").nestedClassName
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual4a  = SomeTestTypes.nestedClassName

    Result.all(
      List(
        actual1a ==== expected1,
        actual2a ==== expected2,
        actual3a ==== expected3,
        actual4a ==== expected4,
      )
    )

  }

  def testClassANestedClassName: Result = {
    import SomeTestTypes._
    val expected1 = "Aaa.Bbb"
    val expected2 = "Aaa.Ccc"
    val expected3 = "SomeTestTypes.Something"
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"

    val actual1 = Aaa.Bbb.getClass.nestedClassName
    val actual2 = Aaa.Ccc.getClass.nestedClassName
    val actual3 = Something.getClass.nestedClassName
    val actual4 = SomeTestTypes.getClass.nestedClassName

    Result.all(
      List(
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4,
      )
    )

  }

  def testClassesGetNestedName1(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
    prefix   <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(0, 5))
                  .map(_.mkString("."))
                  .log("prefix")
  } yield {
    val input  = List(prefix, expected).filter(_.nonEmpty).mkString(".")
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName2(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
    prefix   <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(0, 5))
                  .map(_.mkString("."))
                  .log("prefix")
  } yield {
    val input  = List(prefix, expected + "$").filter(_.nonEmpty).mkString(".")
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName3(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
    prefix   <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(0, 5))
                  .map(_.mkString("."))
                  .log("prefix")
  } yield {
    val input  = List(prefix, expected.replace(".", "$")).filter(_.nonEmpty).mkString(".")
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName4(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
    prefix   <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(0, 5))
                  .map(_.mkString("."))
                  .log("prefix")
  } yield {
    val input  = List(prefix, expected.replace(".", "$") + "$").filter(_.nonEmpty).mkString(".")
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName5(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
  } yield {
    val input  = expected
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName6(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .log("expected")
  } yield {
    val input  = expected
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName7(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
  } yield {
    val input  = expected + "$"
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName8(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .log("expected")
  } yield {
    val input  = expected + "$"
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName9(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
  } yield {
    val input  = expected.replace(".", "$")
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def testClassesGetNestedName10(testName: String): Property = for {
    expected <- Gen
                  .string(Gen.alphaNum, Range.linear(1, 10))
                  .list(Range.constant(2, 2))
                  .map(_.mkString("."))
                  .log("expected")
  } yield {
    val input  = expected.replace(".", "$") + "$"
    val actual = getNestedName(input)
    (actual ==== expected)
      .log(inputActualExpected(testName, input, actual, expected))
  }

  def inputActualExpected(testName: String, input: String, actual: String, expected: String): String =
    s""">> $testName
       |   input: $input
       |  actual: $actual
       |expected: $expected
       |""".stripMargin

  object SomeTestTypes {
    sealed trait Aaa
    object Aaa {
      final case class Bbb(n: Int) extends Aaa
      case object Ccc extends Aaa

      def bbb(n: Int): Aaa = Bbb(n)
      def ccc: Aaa         = Ccc
    }

    final case class Something[A](a: A)
  }

}
