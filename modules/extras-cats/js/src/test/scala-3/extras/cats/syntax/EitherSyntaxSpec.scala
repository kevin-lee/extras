package extras.cats.syntax

import cats.data.EitherT
import cats.syntax.all.*
import cats.effect.unsafe.IORuntime
import extras.cats.testing.{ExecutionContextProvider, IoAppUtils, RandomGens}
import munit.Assertions

import scala.concurrent.{ExecutionContext, Future}

/** @author Kevin Lee
  * @since 2021-08-21
  */
class EitherSyntaxSpec extends munit.FunSuite {

  private val testNamePrefix = " 🟡 EitherSyntaxSpec."

  test(s"${testNamePrefix}EitherTFEitherOpsSpec.testEitherT")(EitherTFEitherOpsSpec.testEitherT())
  test(s"${testNamePrefix}EitherTFEitherOpsSpec.testT")(EitherTFEitherOpsSpec.testT())

  test(s"${testNamePrefix}EitherTEitherOpsSpec.testEitherT")(EitherTEitherOpsSpec.testEitherT())
  test(s"${testNamePrefix}EitherTEitherOpsSpec.testT")(EitherTEitherOpsSpec.testT())

  test(s"${testNamePrefix}EitherTFAOpsSpec.testRightT")(EitherTFAOpsSpec.testRightT())
  test(s"${testNamePrefix}EitherTFAOpsSpec.testLeftT")(EitherTFAOpsSpec.testLeftT())
  test(s"${testNamePrefix}EitherTAOpsSpec.testRightTF")(EitherTAOpsSpec.testRightTF())
  test(s"${testNamePrefix}EitherTAOpsSpec.testLeftTF")(EitherTAOpsSpec.testLeftTF())
  test(s"${testNamePrefix}EitherTSupportAllSpec.testAll")(EitherTSupportAllSpec.testAll())

  test(s"${testNamePrefix}test F[Either[A, B]].innerFind(B => Boolean): F[Option[B]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerFind((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerFind(B => Boolean): F[Option[B]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerFind((s, _) => s.asLeft)
  )

  test(
    s"${testNamePrefix}test F[Either[A, B]].innerFilterOrElse[C >: A](B => Boolean, => C): F[Either[C, B]] - Right case"
  ) {
    FOfEitherInnerOpsSpec.testInnerFilterOrElse((_, n) => n.asRight)
  }
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerFilterOrElse[C >: A](B => Boolean, => C): F[Either[C, B]] - Left case"
  ) {
    FOfEitherInnerOpsSpec.testInnerFilterOrElse((s, _) => s.asLeft)
  }

  test(s"${testNamePrefix}test F[Either[A, B]].innerExists(B => Boolean): F[Boolean] - Right case") {
    FOfEitherInnerOpsSpec.testInnerExists((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerExists(B => Boolean): F[Boolean] - Left case") {
    FOfEitherInnerOpsSpec.testInnerExists((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerForall(B => Boolean): F[Boolean] - Right case") {
    FOfEitherInnerOpsSpec.testInnerForall((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerForall(B => Boolean): F[Boolean] - Left case") {
    FOfEitherInnerOpsSpec.testInnerForall((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerContains(B): F[Boolean] - Right case") {
    FOfEitherInnerOpsSpec.testInnerContains((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerContains(B): F[Boolean] - Left case") {
    FOfEitherInnerOpsSpec.testInnerContains((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerCollectFirst[D >: B](PartialFunction[B, D]): F[D] - Right case") {
    FOfEitherInnerOpsSpec.testInnerCollectFirst((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerCollectFirst[D >: B](PartialFunction[B, D]): F[D] - Left case") {
    FOfEitherInnerOpsSpec.testInnerCollectFirst((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerMap(B => D): F[Either[A, D]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerMap((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerMap(B => D): F[Either[A, D]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerMap((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMap(B => Either[A, D]): F[Either[A, D]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerFlatMap((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMap(B => Either[A, D]): F[Either[A, D]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerFlatMap((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMapF(B => F[Either[A, D]]): F[Either[A, D]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerFlatMapF((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMapF(B => F[Either[A, D]]): F[Either[A, D]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerFlatMapF((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftMap(A => C): F[Either[C, B]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerLeftMap((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftMap(A => C): F[Either[C, B]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerLeftMap((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMap(A => Either[C, B]): F[Either[C, B]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerLeftFlatMap((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMap(A => Either[C, B]): F[Either[C, B]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerLeftFlatMap((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMapF(A => F[Either[C, B]]): F[Either[C, B]] - Right case") {
    FOfEitherInnerOpsSpec.testInnerLeftFlatMapF((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMapF(A => F[Either[C, B]]): F[Either[C, B]] - Left case") {
    FOfEitherInnerOpsSpec.testInnerLeftFlatMapF((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElse[D >: B](=> B): F[D] - Right case") {
    FOfEitherInnerOpsSpec.testInnerGetOrElse((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElse[D >: B](=> B): F[D] - Left case") {
    FOfEitherInnerOpsSpec.testInnerGetOrElse((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElseF[D >: B](=> F[B]): F[D] - Right case") {
    FOfEitherInnerOpsSpec.testInnerGetOrElseF((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElseF[D >: B](=> F[B]): F[D] - Left case") {
    FOfEitherInnerOpsSpec.testInnerGetOrElseF((s, _) => s.asLeft)
  }
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]] - Right case"
  ) {
    FOfEitherInnerOpsSpec.testInnerOrElse((_, n) => n.asRight)
  }
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]] - Left case"
  ) {
    FOfEitherInnerOpsSpec.testInnerOrElse((s, _) => s.asLeft)
  }
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]] - Right case"
  ) {
    FOfEitherInnerOpsSpec.testInnerOrElseF((_, n) => n.asRight)
  }
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]] - Left case"
  ) {
    FOfEitherInnerOpsSpec.testInnerOrElseF((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFold[D](=> D)(B => D): F[D] - Right case") {
    FOfEitherInnerOpsSpec.testInnerFold((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFold[D](=> D)(B => D): F[D] - Left case") {
    FOfEitherInnerOpsSpec.testInnerFold((s, _) => s.asLeft)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFoldF[D](=> F[D])(B => F[D]): F[D] - Right case") {
    FOfEitherInnerOpsSpec.testInnerFoldF((_, n) => n.asRight)
  }
  test(s"${testNamePrefix}test F[Either[A, B]].innerFoldF[D](=> F[D])(B => F[D]): F[D] - Left case") {
    FOfEitherInnerOpsSpec.testInnerFoldF((s, _) => s.asLeft)
  }

  object EitherTFEitherOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.*
    import cats.effect.*
    import extras.cats.syntax.EitherSyntax.{eitherT, t}

    def fab[F[_]: Sync, A, B](ab: Either[A, B]): F[Either[A, B]] = Sync[F].delay(ab)

    def testEitherT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = fab[IO, String, Int](n.asRight[String])
      val expected = EitherT(input)
      val actual   = input.eitherT

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

    def testT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = fab[IO, String, Int](n.asRight[String])
      val expected = EitherT(input)
      val actual   = input.t

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

  }

  object EitherTEitherOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.*
    import cats.data.EitherT
    import cats.effect.*
    import extras.cats.syntax.EitherSyntax.{eitherT, t}

    def testEitherT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input: Either[String, Int] = n.asRight[String]

      val expected = EitherT(Applicative[IO].pure(input))

      val actual = input.eitherT[IO]
      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()

    }

    def testT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input: Either[String, Int] = n.asRight[String]

      val expected = EitherT(Applicative[IO].pure(input))

      val actual = input.t[IO]
      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()

    }

  }

  object EitherTFAOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.EitherT
    import cats.effect.*
    import extras.cats.syntax.EitherSyntax.{leftT, rightT}

    def testRightT(): Future[Unit] = {
      val n     = RandomGens.genRandomInt()
      val input = IO(n)

      val expected = EitherT(input.map(_.asRight[String]))

      val actual = input.rightT[String]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

    def testLeftT(): Future[Unit] = {
      val s     = RandomGens.genAlphaNumericString(20)
      val input = IO(s)

      val expected = EitherT(input.map(_.asLeft[Int]))

      val actual = input.leftT[Int]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isLeft, s"actualValue should be Left. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

  }

  object EitherTAOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.EitherT
    import cats.effect.*
    import extras.cats.syntax.EitherSyntax.{leftTF, rightTF}

    def testRightTF(): Future[Unit] = {
      val n     = RandomGens.genRandomInt()
      val input = n

      val expected = EitherT(IO(input.asRight[String]))

      val actual = input.rightTF[IO, String]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isRight, s"actualValue should be Right. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

    def testLeftTF(): Future[Unit] = {
      val s     = RandomGens.genAlphaNumericString(20)
      val input = s

      val expected = EitherT(IO(input.asLeft[Int]))

      val actual = input.leftTF[IO, Int]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assert(actualEither.isLeft, s"actualValue should be Left. actualEither: $actualEither")
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

  }

  object EitherTSupportAllSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    def testAll(): Future[Unit] = {
      import extras.cats.syntax.EitherSyntax.{*, given}
      import cats.Applicative
      import cats.data.EitherT
      import cats.effect.*

      val n = RandomGens.genRandomInt()
      val s = RandomGens.genAlphaNumericString(20)

      // Test 1: F[Either[A, B]].eitherT
      val input1    = EitherTFEitherOpsSpec.fab[IO, String, Int](n.asRight[String])
      val expected1 = EitherT(input1)
      val actual1   = input1.eitherT
      val actual1_1 = input1.t

      val test1 = for {
        actualEither   <- actual1.value
        expectedEither <- expected1.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual1.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      val test1_1 = for {
        actualEither   <- actual1_1.value
        expectedEither <- expected1.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual1_1.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      // Test 2: Either[A, B].eitherT[F]
      val input2: Either[String, Int] = n.asRight[String]
      val expected2                   = EitherT(Applicative[IO].pure(input2))
      val actual2                     = input2.eitherT[IO]
      val actual2_1                   = input2.t[IO]

      val test2 = for {
        actualEither   <- actual2.value
        expectedEither <- expected2.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual2.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      val test2_1 = for {
        actualEither   <- actual2_1.value
        expectedEither <- expected2.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual2_1.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      // Test 3: F[A].rightT[B]
      val input3    = IO(n)
      val expected3 = EitherT(input3.map(_.asRight[String]))
      val actual3   = input3.rightT[String]

      val test3 = for {
        actualEither   <- actual3.value
        expectedEither <- expected3.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual3.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      // Test 4: F[B].leftT[A]
      val input4    = IO(s)
      val expected4 = EitherT(input4.map(_.asLeft[Int]))
      val actual4   = input4.leftT[Int]

      val test4 = for {
        actualEither   <- actual4.value
        expectedEither <- expected4.value
      } yield {
        Assertions.assert(actualEither.isLeft, s"actual4.value should be Left. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      // Test 5: A.rightTF[F, B]
      val input5    = n
      val expected5 = EitherT(IO(input5.asRight[String]))
      val actual5   = input5.rightTF[IO, String]

      val test5 = for {
        actualEither   <- actual5.value
        expectedEither <- expected5.value
      } yield {
        Assertions.assert(actualEither.isRight, s"actual5.value should be Right. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      // Test 6: B.leftTF[F, A]
      val input6    = s
      val expected6 = EitherT(IO(input6.asLeft[Int]))
      val actual6   = input6.leftTF[IO, Int]

      val test6 = for {
        actualEither   <- actual6.value
        expectedEither <- expected6.value
      } yield {
        Assertions.assert(actualEither.isLeft, s"actual6.value should be Left. actualEither: $actualEither")
        Assertions.assertEquals(actualEither, expectedEither)
      }

      (for {
        _ <- test1
        _ <- test1_1
        _ <- test2
        _ <- test2_1
        _ <- test3
        _ <- test4
        _ <- test5
        _ <- test6
      } yield ()).unsafeToFuture()
    }

  }

  object FOfEitherInnerOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.effect.*
    import extras.cats.syntax.either.*

    def fab[F[_]: Sync, A, B](eab: Either[A, B]): F[Either[A, B]] = Sync[F].delay(eab)

    def testInnerFind(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }

      val eitherSI = toEither(RandomGens.genAlphaNumericString(10), RandomGens.genRandomIntWithMinMax(n, max))

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.find(filterF1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.find(filterF2)

      input1
        .innerFind(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerFind(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerFilterOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val s2 = RandomGens.genAlphaNumericString(10)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.filterOrElse(filterF1, s2)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.filterOrElse(filterF2, s2)

      input1
        .innerFilterOrElse(filterF1, s2)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerFilterOrElse(filterF2, s2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerExists(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.exists(filterF1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.exists(filterF2)

      input1
        .innerExists(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerExists(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerForall(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.forall(filterF1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.forall(filterF2)

      input1
        .innerForall(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerForall(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerContains(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val toCompare1 = n
      val toCompare2 = if (n === Int.MaxValue) n - 1 else n + 1

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.contains(toCompare1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.contains(toCompare2)

      input1
        .innerContains(toCompare1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerContains(toCompare2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerCollectFirst(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val pf1: PartialFunction[Int, Int] = {
        case i if i === n => i
      }
      val pf2: PartialFunction[Int, Int] = {
        case i if i === (if (n === Int.MaxValue) n - 1 else n + 1) => i
      }

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.collectFirst(pf1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.collectFirst(pf2)

      input1
        .innerCollectFirst(pf1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input2
            .innerCollectFirst(pf2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n             = RandomGens.genRandomInt()
      val s             = RandomGens.genAlphaNumericString(10)
      val eitherSI      = toEither(s, n)
      val f: Int => Int = _ * 2

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.map(f)

      input
        .innerMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n                             = RandomGens.genRandomInt()
      val s                             = RandomGens.genAlphaNumericString(10)
      val eitherSI                      = toEither(s, n)
      val f: Int => Either[String, Int] = num => (num * 2).asRight

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.flatMap(f)

      input
        .innerFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMapF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n                                  = RandomGens.genRandomInt()
      val s                                  = RandomGens.genAlphaNumericString(10)
      val eitherSI                           = toEither(s, n)
      val f: Int => Either[String, Int]      = num => (num * 2).asRight
      val ff: Int => IO[Either[String, Int]] = num => IO.pure(f(num))

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.flatMap(f)

      input
        .innerFlatMapF(ff)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n                   = RandomGens.genRandomInt()
      val s                   = RandomGens.genAlphaNumericString(10)
      val eitherSI            = toEither(s, n)
      val prefix              = RandomGens.genAlphaNumericString(5)
      val f: String => String = prefix + _

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftMap(f)

      input
        .innerLeftMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftFlatMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n                                = RandomGens.genRandomInt()
      val s                                = RandomGens.genAlphaNumericString(10)
      val eitherSI                         = toEither(s, n)
      val prefix                           = RandomGens.genAlphaNumericString(5)
      val f: String => Either[String, Int] = ss => (prefix + ss).asLeft

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftFlatMap(f)

      input
        .innerLeftFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftFlatMapF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n                                     = RandomGens.genRandomInt()
      val s                                     = RandomGens.genAlphaNumericString(10)
      val eitherSI                              = toEither(s, n)
      val prefix                                = RandomGens.genAlphaNumericString(5)
      val f: String => Either[String, Int]      = ss => (prefix + ss).asLeft
      val ff: String => IO[Either[String, Int]] = ss => IO.pure(f(ss))

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftFlatMap(f)

      input
        .innerLeftFlatMapF(ff)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)
      val n2       = RandomGens.genRandomInt()

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.getOrElse(n2)

      input
        .innerGetOrElse(n2)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElseF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)
      val n2       = RandomGens.genRandomInt()

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.getOrElse(n2)

      input
        .innerGetOrElseF(IO.pure(n2))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val defaultEither =
        if (RandomGens.genBoolean()) (n + 10).asRight[String] else RandomGens.genAlphaNumericString(10).asLeft[Int]

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.orElse(defaultEither)

      input
        .innerOrElse(defaultEither)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElseF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val defaultEither =
        if (RandomGens.genBoolean()) (n + 10).asRight[String] else RandomGens.genAlphaNumericString(10).asLeft[Int]

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.orElse(defaultEither)

      input
        .innerOrElseF(IO.pure(defaultEither))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFold(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val ifLeft: String => Int = _.length
      val ifRight: Int => Int   = _ + n

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.fold(ifLeft, ifRight)

      input
        .innerFold(ifLeft)(ifRight)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFoldF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val s        = RandomGens.genAlphaNumericString(10)
      val eitherSI = toEither(s, n)

      val ifLeft: String => Int = _.length
      val ifRight: Int => Int   = _ + n

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.fold(ifLeft, ifRight)

      input
        .innerFoldF(s => IO.pure(ifLeft(s)))(n => IO.pure(ifRight(n)))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

  }

}
