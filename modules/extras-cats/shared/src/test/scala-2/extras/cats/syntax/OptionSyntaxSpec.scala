package extras.cats.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-08-22
  */
@SuppressWarnings(Array("org.wartremover.warts.ToString"))
object OptionSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "OptionTFOptionOpsSpec.testOptionT",
      OptionTFOptionOpsSpec.testOptionT,
    ),
    property(
      "OptionTFOptionOpsSpec.testT",
      OptionTFOptionOpsSpec.testT,
    ),
    property(
      "OptionTOptionOpsSpec.testOptionT",
      OptionTOptionOpsSpec.testOptionT,
    ),
    property(
      "OptionTOptionOpsSpec.testT",
      OptionTOptionOpsSpec.testT,
    ),
    property(
      "OptionTFAOpsSpec.testOptionT",
      OptionTFAOpsSpec.testOptionT,
    ),
    property(
      "OptionTAOpsSpec.testOptionTF",
      OptionTAOpsSpec.testOptionTF,
    ),
    property(
      "OptionTSupportAllSpec.testAll",
      OptionTSupportAllSpec.testAll,
    ),
    property(
      "test F[Option[A]].InnerMap(A => B): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerMap,
    ),
  )

  object OptionTFOptionOpsSpec {

    import cats.data._
    import cats.effect._
    import cats.syntax.option._
    import extras.cats.syntax.option.optionTFOptionOps

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testOptionT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = fab[IO, Int](n.some)
      val expected      = OptionT(input)
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.optionT
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

    def testT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = fab[IO, Int](n.some)
      val expected      = OptionT(input)
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.t
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTOptionOpsSpec {

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option._
    import extras.cats.syntax.option.optionTOptionOps

    def testOptionT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = n.some
      val expected      = OptionT(Applicative[IO].pure(input))
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.optionT[IO]
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

    def testT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = n.some
      val expected      = OptionT(Applicative[IO].pure(input))
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.t[IO]
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTFAOpsSpec {

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import extras.cats.syntax.option.optionTFAOps

    def testOptionT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = Applicative[IO].pure(n)
      val expected      = OptionT.liftF(input)
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.someT
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTAOpsSpec {

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option._
    import extras.cats.syntax.option.optionTAOps

    def testOptionTF: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = n
      val expected      = OptionT(Applicative[IO].pure(input.some))
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.someTF[IO]
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: ${actualValue.toString}"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTSupportAllSpec {

    import cats.Applicative
    import cats.data._
    import cats.effect._
    import cats.syntax.option._
    import extras.cats.syntax.option._

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testAll: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input1         = fab[IO, Int](n.some)
      val expected1      = OptionT(input1)
      val expected1Value = expected1.value.unsafeRunSync()

      val actual1      = input1.optionT
      val actual1Value = actual1.value.unsafeRunSync()

      val actual1_1      = input1.t
      val actual1_1Value = actual1_1.value.unsafeRunSync()

      val input2         = n.some
      val expected2      = OptionT(Applicative[IO].pure(input2))
      val expected2Value = expected2.value.unsafeRunSync()

      val actual2      = input2.optionT[IO]
      val actual2Value = actual2.value.unsafeRunSync()

      val actual2_1      = input2.t[IO]
      val actual2_1Value = actual2_1.value.unsafeRunSync()

      val input3         = Applicative[IO].pure(n)
      val expected3      = OptionT.liftF(input3)
      val expected3Value = expected3.value.unsafeRunSync()

      val actual3      = input3.someT
      val actual3Value = actual3.value.unsafeRunSync()

      val input4         = n
      val expected4      = OptionT(Applicative[IO].pure(input4.some))
      val expected4Value = expected4.value.unsafeRunSync()

      val actual4      = input4.someTF[IO]
      val actual4Value = actual4.value.unsafeRunSync()

      Result.all(
        List(
          Result
            .assert(actual1Value.isDefined)
            .log(s"actual1Value should be Some. actual1Value: ${actual1Value.toString}"),
          actual1Value ==== expected1Value,
          Result
            .assert(actual1_1Value.isDefined)
            .log(s"actual1_1Value should be Some. actual1_1Value: ${actual1_1Value.toString}"),
          actual1_1Value ==== expected1Value,
          Result
            .assert(actual2Value.isDefined)
            .log(s"actual2Value should be Some. actual2Value: ${actual2Value.toString}"),
          actual2Value ==== expected2Value,
          Result
            .assert(actual2_1Value.isDefined)
            .log(s"actual2_1Value should be Some. actual2_1Value: ${actual2_1Value.toString}"),
          actual2_1Value ==== expected2Value,
          Result
            .assert(actual3Value.isDefined)
            .log(s"actual3Value should be Some. actual3Value: ${actual3Value.toString}"),
          actual3Value ==== expected3Value,
          Result
            .assert(actual4Value.isDefined)
            .log(s"actual4Value should be Some. actual4Value: ${actual4Value.toString}"),
          actual4Value ==== expected4Value,
        )
      )

    }

  }

  object FOfOptionInnerOpsSpec {

    import cats.effect._
    import extras.cats.syntax.option.fOfOptionInnerOps

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testInnerMap: Property =
      for {
        optionN <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).option.log("optionN")
      } yield {
        val f: Int => Int = _ * 2

        val input    = fab[IO, Int](optionN)
        val expected = optionN.map(f)

        input
          .innerMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

  }

}
