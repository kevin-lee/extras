package extras.cats.syntax

import cats.data.EitherT
import cats.effect.unsafe.IORuntime
import extras.cats.testing.IoAppUtils
import extras.concurrent.testing.ConcurrentSupport
import extras.cats.testing.ExecutionContextProvider

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2021-08-21
  */
class EitherSyntaxSpec extends Properties {

  override def tests: List[Test] = List(
    property(
      "EitherTFEitherOpsSpec.testEitherT",
      EitherTFEitherOpsSpec.testEitherT
    ),
    property(
      "EitherTFEitherOpsSpec.testT",
      EitherTFEitherOpsSpec.testT
    ),
    property(
      "EitherTEitherOpsSpec.testEitherT",
      EitherTEitherOpsSpec.testEitherT
    ),
    property(
      "EitherTEitherOpsSpec.testT",
      EitherTEitherOpsSpec.testT
    ),
    property(
      "EitherTFAOpsSpec.testRightT",
      EitherTFAOpsSpec.testRightT
    ),
    property(
      "EitherTFAOpsSpec.testLeftT",
      EitherTFAOpsSpec.testLeftT
    ),
    property(
      "EitherTAOpsSpec.testRightTF",
      EitherTAOpsSpec.testRightTF
    ),
    property(
      "EitherTAOpsSpec.testLeftTF",
      EitherTAOpsSpec.testLeftTF
    ),
    property(
      "testAll with EitherTSupport.*",
      EitherTSupportAllSpec.testAll
    )
  )

  object EitherTFEitherOpsSpec {

    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.data.*
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.EitherSyntax.{eitherT, t}

    def fab[F[_]: Sync, A, B](ab: Either[A, B]): F[Either[A, B]] = Sync[F].delay(ab)

    def testEitherT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input       = fab[IO, String, Int](n.asRight[String])
      val expected    = EitherT(input)
      val actual      = input.eitherT
      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

    def testT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input       = fab[IO, String, Int](n.asRight[String])
      val expected    = EitherT(input)
      val actual      = input.t
      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

  }

  object EitherTEitherOpsSpec {

    val es              = ConcurrentSupport.newExecutorService(2)
    given rt: IORuntime = IoAppUtils.runtime(es)

    import cats.*
    import cats.data.EitherT
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.EitherSyntax.{eitherT, t}

    def testEitherT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input: Either[String, Int] = n.asRight[String]

      val expected = EitherT(Applicative[IO].pure(input))

      val actual      = input.eitherT[IO]
      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

    def testT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input: Either[String, Int] = n.asRight[String]

      val expected = EitherT(Applicative[IO].pure(input))

      val actual      = input.t[IO]
      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

  }

  object EitherTFAOpsSpec {

    val es              = ConcurrentSupport.newExecutorService(2)
    given rt: IORuntime = IoAppUtils.runtime(es)

    import cats.data.EitherT
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.EitherSyntax.{rightT, leftT}

    def testRightT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input = IO(n)

      val expected = EitherT(input.map(_.asRight[String]))

      val actual = input.rightT[String]

      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

    def testLeftT: Property = for {
      s <- Gen.string(Gen.alphaNum, Range.linear(0, 20)).log("S")
    } yield {
      val input = IO(s)

      val expected = EitherT(input.map(_.asLeft[Int]))

      val actual = input.leftT[Int]

      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isLeft).log(s"actualValue should be Left. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

  }

  object EitherTAOpsSpec {

    val es              = ConcurrentSupport.newExecutorService(2)
    given rt: IORuntime = IoAppUtils.runtime(es)

    import cats.data.EitherT
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.EitherSyntax.{rightTF, leftTF}

    def testRightTF: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input = n

      val expected = EitherT(IO(input.asRight[String]))

      val actual = input.rightTF[IO, String]

      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isRight).log(s"actualValue should be Right. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

    def testLeftTF: Property = for {
      s <- Gen.string(Gen.alphaNum, Range.linear(0, 20)).log("S")
    } yield {
      val input = s

      val expected = EitherT(IO(input.asLeft[Int]))

      val actual = input.leftTF[IO, Int]

      val actualValue = actual.value.unsafeRunSync()
      Result.all(
        List(
          Result.assert(actualValue.isLeft).log(s"actualValue should be Left. actualValue: $actualValue"),
          actualValue ==== expected.value.unsafeRunSync()
        )
      )
    }

  }

  object EitherTSupportAllSpec {

    val es              = ConcurrentSupport.newExecutorService(2)
    given rt: IORuntime = IoAppUtils.runtime(es)

    def testAll: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
      s <- Gen.string(Gen.alphaNum, Range.linear(0, 20)).log("S")
    } yield {
      import extras.cats.syntax.EitherSyntax.{*, given}
      import cats.Applicative
      import cats.data.EitherT
      import cats.effect.*
      import cats.syntax.all.*

      val input1         = EitherTFEitherOpsSpec.fab[IO, String, Int](n.asRight[String])
      val expected1      = EitherT(input1)
      val expected1Value = expected1.value.unsafeRunSync()

      val actual1      = input1.eitherT
      val actual1Value = actual1.value.unsafeRunSync()

      val actual1_1      = input1.t
      val actual1_1Value = actual1_1.value.unsafeRunSync()

      val input2: Either[String, Int] = n.asRight[String]
      val expected2                   = EitherT(Applicative[IO].pure(input2))
      val expected2Value              = expected2.value.unsafeRunSync()

      val actual2      = input2.eitherT[IO]
      val actual2Value = actual2.value.unsafeRunSync()

      val actual2_1      = input2.eitherT[IO]
      val actual2_1Value = actual2_1.value.unsafeRunSync()

      val input3         = IO(n)
      val expected3      = EitherT(input3.map(_.asRight[String]))
      val expected3Value = expected3.value.unsafeRunSync()

      val actual3      = input3.rightT[String]
      val actual3Value = actual3.value.unsafeRunSync()

      val input4         = IO(s)
      val expected4      = EitherT(input4.map(_.asLeft[Int]))
      val expected4Value = expected4.value.unsafeRunSync()

      val actual4      = input4.leftT[Int]
      val actual4Value = actual4.value.unsafeRunSync()

      val input5         = n
      val expected5      = EitherT(IO(input5.asRight[String]))
      val expected5Value = expected5.value.unsafeRunSync()

      val actual5      = input5.rightTF[IO, String]
      val actual5Value = actual5.value.unsafeRunSync()

      val input6         = s
      val expected6      = EitherT(IO(input6.asLeft[Int]))
      val expected6Value = expected6.value.unsafeRunSync()

      val actual6      = input6.leftTF[IO, Int]
      val actual6Value = actual6.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actual1Value.isRight).log(s"actual1Value should be Right. actual1Value: $actual1Value"),
          actual1Value ==== expected1Value,
          Result.assert(actual1_1Value.isRight).log(s"actual1_1Value should be Right. actual1_1Value: $actual1_1Value"),
          actual1_1Value ==== expected1Value,
          Result.assert(actual2Value.isRight).log(s"actual2Value should be Right. actual2Value: $actual2Value"),
          actual2Value ==== expected2Value,
          Result.assert(actual2_1Value.isRight).log(s"actual2_1Value should be Right. actual2_1Value: $actual2_1Value"),
          actual2_1Value ==== expected2Value,
          Result.assert(actual3Value.isRight).log(s"actual3Value should be Right. actual3Value: $actual3Value"),
          actual3Value ==== expected3Value,
          Result.assert(actual4Value.isLeft).log(s"actual4Value should be Left. actual4Value: $actual4Value"),
          actual4Value ==== expected4Value,
          Result.assert(actual5Value.isRight).log(s"actual5Value should be Right. actual5Value: $actual5Value"),
          actual5Value ==== expected5Value,
          Result.assert(actual6Value.isLeft).log(s"actual6Value should be Left. actual6Value: $actual6Value"),
          actual6Value ==== expected6Value
        )
      )
    }

  }

}
