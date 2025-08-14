package extras.cats.syntax

import cats.effect.unsafe.IORuntime
import cats.syntax.all.*
import extras.cats.testing.{IoAppUtils, RandomGens}
import munit.Assertions

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.DurationInt

/** @author Kevin Lee
  * @since 2021-08-24
  */
class AllSyntaxSpec extends munit.FunSuite {

  override val munitTimeout: FiniteDuration = 200.milliseconds

  private val testNamePrefix = " 🔵 AllSyntaxSpec."

  test(s"${testNamePrefix}OptionTFOptionOpsSpec.testOptionT")(OptionTFOptionOpsSpec.testOptionT())
  test(s"${testNamePrefix}OptionTFOptionOpsSpec.testT")(OptionTFOptionOpsSpec.testT())

  test(s"${testNamePrefix}OptionTOptionOpsSpec.testOptionT")(OptionTOptionOpsSpec.testOptionT())
  test(s"${testNamePrefix}OptionTOptionOpsSpec.testT")(OptionTOptionOpsSpec.testT())

  test(s"${testNamePrefix}OptionTFAOpsSpec.testSomeT")(OptionTFAOpsSpec.testSomeT())

  test(s"${testNamePrefix}OptionTAOpsSpec.testSomeTF")(OptionTAOpsSpec.testSomeTF())

  test(s"${testNamePrefix}OptionTSupportAllSpec.testAll")(OptionTSupportAllSpec.testAll())

  test(s"${testNamePrefix}test F[Option[A]].innerFilter(A => Boolean): F[Option[A]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerFilter(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerFilter(A => Boolean): F[Option[A]] - None case")(
    FOfOptionInnerOpsSpec.testInnerFilter(_ => none[Int])
  )
  test(s"${testNamePrefix}test F[Option[A]].innerExists(A => Boolean): F[Boolean] - Some case")(
    FOfOptionInnerOpsSpec.testInnerExists(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerExists(A => Boolean): F[Boolean] - None case")(
    FOfOptionInnerOpsSpec.testInnerExists(_ => none[Int])
  )
  test(s"${testNamePrefix}test F[Option[A]].innerContains(A): F[Boolean] - Some case")(
    FOfOptionInnerOpsSpec.testInnerContains(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerContains(A): F[Boolean] - None case")(
    FOfOptionInnerOpsSpec.testInnerContains(_ => none[Int])
  )
  test(s"${testNamePrefix}test F[Option[A]].innerForall(A => Boolean): F[Boolean] - Some case")(
    FOfOptionInnerOpsSpec.testInnerForall(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerForall(A => Boolean): F[Boolean] - None case")(
    FOfOptionInnerOpsSpec.testInnerForall(_ => none[Int])
  )
  test(s"${testNamePrefix}test F[Option[A]].innerCollect(PartialFunction[A, B]): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerCollect(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerCollect(PartialFunction[A, B]): F[Option[B]] - None case")(
    FOfOptionInnerOpsSpec.testInnerCollect(_ => none[Int])
  )
  test(s"${testNamePrefix}test F[Option[A]].innerMap(A => B): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerMap(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerMap(A => B): F[Option[B]] - None")(
    FOfOptionInnerOpsSpec.testInnerMap(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerFlatMap(A => Option[B]): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerFlatMap(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerFlatMap(A => Option[B]): F[Option[B]] - None case")(
    FOfOptionInnerOpsSpec.testInnerFlatMap(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerFlatMapF(A => F[Option[B]]): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerFlatMapF(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerFlatMapF(A => F[Option[B]]): F[Option[B]] - None case")(
    FOfOptionInnerOpsSpec.testInnerFlatMapF(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerGetOrElse[B >: A](=> B): F[B] - Some case")(
    FOfOptionInnerOpsSpec.testInnerGetOrElse(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerGetOrElse[B >: A](=> B): F[B] - None case")(
    FOfOptionInnerOpsSpec.testInnerGetOrElse(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerGetOrElseF[B >: A](=> F[B]): F[B] - Some case")(
    FOfOptionInnerOpsSpec.testInnerGetOrElseF(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerGetOrElseF[B >: A](=> F[B]): F[B] - None case")(
    FOfOptionInnerOpsSpec.testInnerGetOrElseF(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerOrElse[B >: A](=> Option[B]): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerOrElse(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerOrElse[B >: A](=> Option[B]): F[Option[B]] - None case")(
    FOfOptionInnerOpsSpec.testInnerOrElse(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerOrElseF[B >: A](=> F[Option[B]]): F[Option[B]] - Some case")(
    FOfOptionInnerOpsSpec.testInnerOrElseF(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerOrElseF[B >: A](=> F[Option[B]]): F[Option[B]] - None case")(
    FOfOptionInnerOpsSpec.testInnerOrElseF(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerFold[B >: A](=> B)(A => B): F[B] - Some case")(
    FOfOptionInnerOpsSpec.testInnerFold(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerFold[B >: A](=> B)(A => B): F[B] - None case")(
    FOfOptionInnerOpsSpec.testInnerFold(_ => none[Int])
  )

  test(s"${testNamePrefix}test F[Option[A]].innerFoldF[B >: A](=> F[B])(A => F[B]): F[B] - Some case")(
    FOfOptionInnerOpsSpec.testInnerFoldF(_.some)
  )
  test(s"${testNamePrefix}test F[Option[A]].innerFoldF[B >: A](=> F[B])(A => F[B]): F[B] - None case")(
    FOfOptionInnerOpsSpec.testInnerFoldF(_ => none[Int])
  )

  test(s"${testNamePrefix}EitherTFEitherOpsSpec.testEitherT")(EitherTFEitherOpsSpec.testEitherT())
  test(s"${testNamePrefix}EitherTFEitherOpsSpec.testT")(EitherTFEitherOpsSpec.testT())
  test(s"${testNamePrefix}EitherTEitherOpsSpec.testEitherT")(EitherTEitherOpsSpec.testEitherT())
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
  )(
    FOfEitherInnerOpsSpec.testInnerFilterOrElse((_, n) => n.asRight)
  )
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerFilterOrElse[C >: A](B => Boolean, => C): F[Either[C, B]] - Left case"
  )(
    FOfEitherInnerOpsSpec.testInnerFilterOrElse((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerExists(B => Boolean): F[Boolean] - Right case")(
    FOfEitherInnerOpsSpec.testInnerExists((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerExists(B => Boolean): F[Boolean] - Left case")(
    FOfEitherInnerOpsSpec.testInnerExists((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerForall(B => Boolean): F[Boolean] - Right case")(
    FOfEitherInnerOpsSpec.testInnerForall((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerForall(B => Boolean): F[Boolean] - Left case")(
    FOfEitherInnerOpsSpec.testInnerForall((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerContains(B): F[Boolean] - Right case")(
    FOfEitherInnerOpsSpec.testInnerContains((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerContains(B): F[Boolean] - Left case")(
    FOfEitherInnerOpsSpec.testInnerContains((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerCollectFirst[D >: B](PartialFunction[B, D]): F[D] - Right case")(
    FOfEitherInnerOpsSpec.testInnerCollectFirst((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerCollectFirst[D >: B](PartialFunction[B, D]): F[D] - Left case")(
    FOfEitherInnerOpsSpec.testInnerCollectFirst((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerMap(B => D): F[Either[A, D]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerMap((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerMap(B => D): F[Either[A, D]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerMap((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMap(B => Either[A, D]): F[Either[A, D]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerFlatMap((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMap(B => Either[A, D]): F[Either[A, D]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerFlatMap((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMapF(B => F[Either[A, D]]): F[Either[A, D]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerFlatMapF((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerFlatMapF(B => F[Either[A, D]]): F[Either[A, D]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerFlatMapF((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftMap(A => C): F[Either[C, B]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerLeftMap((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftMap(A => C): F[Either[C, B]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerLeftMap((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMap(A => Either[C, B]): F[Either[C, B]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerLeftFlatMap((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMap(A => Either[C, B]): F[Either[C, B]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerLeftFlatMap((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMapF(A => F[Either[C, B]]): F[Either[C, B]] - Right case")(
    FOfEitherInnerOpsSpec.testInnerLeftFlatMapF((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerLeftFlatMapF(A => F[Either[C, B]]): F[Either[C, B]] - Left case")(
    FOfEitherInnerOpsSpec.testInnerLeftFlatMapF((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElse[D >: B](=> B): F[D] - Right case")(
    FOfEitherInnerOpsSpec.testInnerGetOrElse((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElse[D >: B](=> B): F[D] - Left case")(
    FOfEitherInnerOpsSpec.testInnerGetOrElse((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElseF[D >: B](=> F[B]): F[D] - Right case")(
    FOfEitherInnerOpsSpec.testInnerGetOrElseF((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerGetOrElseF[D >: B](=> F[B]): F[D] - Left case")(
    FOfEitherInnerOpsSpec.testInnerGetOrElseF((s, _) => s.asLeft)
  )

  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]] - Right case"
  )(
    FOfEitherInnerOpsSpec.testInnerOrElse((_, n) => n.asRight)
  )
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]] - Left case"
  )(
    FOfEitherInnerOpsSpec.testInnerOrElse((s, _) => s.asLeft)
  )

  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]] - Right case"
  )(
    FOfEitherInnerOpsSpec.testInnerOrElseF((_, n) => n.asRight)
  )
  test(
    s"${testNamePrefix}test F[Either[A, B]].innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]] - Left case"
  )(
    FOfEitherInnerOpsSpec.testInnerOrElseF((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerFold[D](=> D)(B => D): F[D] - Right case")(
    FOfEitherInnerOpsSpec.testInnerFold((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerFold[D](=> D)(B => D): F[D] - Left case")(
    FOfEitherInnerOpsSpec.testInnerFold((s, _) => s.asLeft)
  )

  test(s"${testNamePrefix}test F[Either[A, B]].innerFoldF[D](=> F[D])(B => F[D]): F[D] - Right case")(
    FOfEitherInnerOpsSpec.testInnerFoldF((_, n) => n.asRight)
  )
  test(s"${testNamePrefix}test F[Either[A, B]].innerFoldF[D](=> F[D])(B => F[D]): F[D] - Left case")(
    FOfEitherInnerOpsSpec.testInnerFoldF((s, _) => s.asLeft)
  )

  object OptionTFOptionOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.all.{optionT, t}

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testOptionT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input         = fab[IO, Int](n.some)
      val expectedValue = n

      val ot: OptionT[IO, Int] = input.optionT
      ot.value
        .map { actualValue =>
          Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
          Assertions.assertEquals(actualValue, expectedValue.some)
        }
        .unsafeToFuture()
    }

    def testT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input = fab[IO, Int](n.some)

      val expectedValue = n

      val ot: OptionT[IO, Int] = input.t
      ot.value
        .map { actualValue =>
          Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
          Assertions.assertEquals(actualValue, expectedValue.some)
        }
        .unsafeToFuture()
    }

  }

  object OptionTOptionOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.all.{optionT, t}

    def testOptionT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input = n.some

      val expectedValue = n.some

      val ot: OptionT[IO, Int] = input.optionT[IO]
      ot.value
        .map { actualValue =>
          Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
          Assertions.assertEquals(actualValue, expectedValue)
        }
        .unsafeToFuture()

    }

    def testT(): Future[Unit] = {
      val n     = RandomGens.genRandomInt()
      val input = n.some

      val expectedValue = n

      val ot: OptionT[IO, Int] = input.t[IO]
      ot.value.unsafeToFuture().map { actualValue =>
        Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
        Assertions.assertEquals(actualValue, expectedValue.some)
      }

    }

  }

  object OptionTFAOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import extras.cats.syntax.all.someT

    def testSomeT(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input = Applicative[IO].pure(n)

      val expectedValue = n

      val ot: OptionT[IO, Int] = input.someT
      ot.value.unsafeToFuture().map { actualValue =>
        Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
        Assertions.assertEquals(actualValue, expectedValue.some)
      }
    }

  }

  object OptionTAOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.all.someTF

    def testSomeTF(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input = n

      val expectedValue = n

      val ot: OptionT[IO, Int] = input.someTF[IO]
      ot.value.unsafeToFuture().map { actualValue =>
        Assertions.assert(actualValue.isDefined, s"actualValue should be Some. actualValue: $actualValue")
        Assertions.assertEquals(actualValue, expectedValue.some)
      }
    }

  }

  object OptionTSupportAllSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.Applicative
    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.all.*

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testAll(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input1 = fab[IO, Int](n.some)

      val expected1Value = n

      val ot1: OptionT[IO, Int] = input1.optionT
      val result1               = ot1.value.unsafeToFuture().map { actual1Value =>
        Assertions.assert(actual1Value.isDefined, s"actual1Value should be Some. actual1Value: $actual1Value")
        Assertions.assertEquals(actual1Value, expected1Value.some)
      }

      val input2 = n.some

      val expected2Value = n

      val ot2: OptionT[IO, Int] = input2.optionT[IO]
      val result2               = ot2.value.unsafeToFuture().map { actual2Value =>
        Assertions.assert(actual2Value.isDefined, s"actual2Value should be Some. actual2Value: $actual2Value")
        Assertions.assertEquals(actual2Value, expected2Value.some)
      }

      val input3 = Applicative[IO].pure(n)

      val expected3Value = n

      val ot3: OptionT[IO, Int] = input3.someT
      val result3               = ot3.value.unsafeToFuture().map { actual3Value =>
        Assertions.assert(actual3Value.isDefined, s"actual3Value should be Some. actual3Value: $actual3Value")
        Assertions.assertEquals(actual3Value, expected3Value.some)
      }

      val input4 = n

      val expected4Value = n

      val ot4: OptionT[IO, Int] = input4.someTF[IO]
      val result4               = ot4.value.unsafeToFuture().map { actual4Value =>
        Assertions.assert(actual4Value.isDefined, s"actual4Value should be Some. actual4Value: $actual4Value")
        Assertions.assertEquals(actual4Value, expected4Value.some)
      }

      for {
        _ <- result1
        _ <- result2
        _ <- result3
        _ <- result4
      } yield ()

    }

  }

  object FOfOptionInnerOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.effect.*
    import extras.cats.syntax.all.*

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testInnerFilter(toOption: Int => Option[Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val optionN  = toOption(n)
      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.filter(filterF1)
      val expected2 = optionN.filter(filterF2)

      input
        .innerFilter(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input
            .innerFilter(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }

    }

    def testInnerExists(toOption: Int => Option[Int]): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val optionN  = toOption(n)
      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.exists(filterF1)
      val expected2 = optionN.exists(filterF2)

      input
        .innerExists(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input
            .innerExists(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }

    }

    def testInnerContains(toOption: Int => Option[Int]): Future[Unit] = {
      val n          = RandomGens.genRandomInt()
      val optionN    = toOption(n)
      val toCompare1 = n
      val toCompare2 = n + 10

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.contains(toCompare1)
      val expected2 = optionN.contains(toCompare2)

      input
        .innerContains(toCompare1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input
            .innerContains(toCompare2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerForall(toOption: Int => Option[Int]): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val optionN = toOption(n)

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.forall(filterF1)
      val expected2 = optionN.forall(filterF2)

      input
        .innerForall(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input
            .innerForall(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerCollect(toOption: Int => Option[Int]): Future[Unit] = {
      val n       = RandomGens.genRandomInt()
      val optionN = toOption(n)

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val alternative = n + 10

      val input = fab[IO, Int](optionN)

      val pf1: PartialFunction[Int, Int] = {
        case `n` => if (filterF1(n)) n else alternative
      }

      val pf2: PartialFunction[Int, Int] = {
        case `n` => if (filterF2(n)) n else alternative
      }

      val expected1 = optionN.collect(pf1)
      val expected2 = optionN.collect(pf2)

      input
        .innerCollect(pf1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input
            .innerCollect(pf2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerMap(toOption: Int => Option[Int]): Future[Unit] = {
      val n       = RandomGens.genRandomInt()
      val optionN = toOption(n)

      val f: Int => Int = _ * 2

      val input    = fab[IO, Int](optionN)
      val expected = optionN.map(f)

      input
        .innerMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMap(toOption: Int => Option[Int]): Future[Unit] = {
      val n       = RandomGens.genRandomInt()
      val optionN = toOption(n)

      val f: Int => Option[Int] = n => (n * 2).some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.flatMap(f)

      input
        .innerFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMapF(toOption: Int => Option[Int]): Future[Unit] = {
      val n       = RandomGens.genRandomInt()
      val optionN = toOption(n)

      val f: Int => Option[Int] = n => (n * 2).some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.flatMap(f)

      input
        .innerFlatMapF(a => IO.pure(f(a)))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElse(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue = RandomGens.genRandomInt()
      val n            = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN      = toOption(n)

      val input    = fab[IO, Int](optionN)
      val expected = optionN.getOrElse(defaultValue)

      input
        .innerGetOrElse(defaultValue)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElseF(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue = RandomGens.genRandomInt()
      val n            = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN      = toOption(n)

      val input    = fab[IO, Int](optionN)
      val expected = optionN.getOrElse(defaultValue)

      input
        .innerGetOrElseF(IO.pure(defaultValue))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElse(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue  = RandomGens.genRandomInt()
      val n             = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN       = toOption(n)
      val defaultOption = defaultValue.some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.orElse(defaultOption)

      input
        .innerOrElse(defaultOption)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElseF(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue  = RandomGens.genRandomInt()
      val n             = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN       = toOption(n)
      val defaultOption = defaultValue.some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.orElse(defaultOption)

      input
        .innerOrElseF(IO.pure(defaultOption))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFold(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue  = RandomGens.genRandomInt()
      val n             = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN       = toOption(n)
      val f: Int => Int = _ * 2

      val input    = fab[IO, Int](optionN)
      val expected = optionN.fold(defaultValue)(f)

      input
        .innerFold(defaultValue)(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFoldF(toOption: Int => Option[Int]): Future[Unit] = {
      val defaultValue  = RandomGens.genRandomInt()
      val n             = {
        val temp = RandomGens.genRandomInt()
        if (temp == defaultValue) temp + 1 else temp
      }
      val optionN       = toOption(n)
      val f: Int => Int = _ * 2

      val input    = fab[IO, Int](optionN)
      val expected = optionN.fold(defaultValue)(f)

      input
        .innerFoldF(IO.pure(defaultValue))(a => IO.pure(f(a)))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

  }

  object EitherTFEitherOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.*
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.all.{eitherT, t}

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
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

  }

  object EitherTEitherOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext

    given rt: IORuntime = IoAppUtils.runtime

    import cats.*
    import cats.data.EitherT
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.all.eitherT

    def testEitherT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = n.asRight[String]
      val expected = EitherT(Applicative[IO].pure(input))
      val actual   = input.eitherT[IO]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
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
    import cats.syntax.either.*
    import extras.cats.syntax.all.{leftT, rightT}

    def testRightT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = IO(n)
      val expected = EitherT(input.map(_.asRight[String]))
      val actual   = input.rightT[String]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

    def testLeftT(): Future[Unit] = {
      val s        = RandomGens.genAlphaNumericString(20)
      val input    = IO(s)
      val expected = EitherT(input.map(_.asLeft[Int]))
      val actual   = input.leftT[Int]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
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
    import cats.syntax.either.*
    import extras.cats.syntax.all.{leftTF, rightTF}

    def testRightTF(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = n
      val expected = EitherT(IO(input.asRight[String]))
      val actual   = input.rightTF[IO, String]

      actual
        .value
        .flatMap { actualEither =>
          expected.value.map { expectedEither =>
            Assertions.assertEquals(actualEither, expectedEither)
          }
        }
        .unsafeToFuture()
    }

    def testLeftTF(): Future[Unit] = {
      val s        = RandomGens.genAlphaNumericString(20)
      val input    = s
      val expected = EitherT(IO(input.asLeft[Int]))
      val actual   = input.leftTF[IO, Int]

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
      import extras.cats.syntax.all.*
      import cats.Applicative
      import cats.data.EitherT
      import cats.effect.*
      import cats.syntax.all.*

      val n = RandomGens.genRandomInt()
      val s = RandomGens.genAlphaNumericString(10)

      /* Test case 1: F[Either[A, B]].eitherT */
      val input1 = EitherTFEitherOpsSpec.fab[IO, String, Int](n.asRight[String])

      val expected1 = EitherT(input1)

      val actual1 = input1.eitherT
      val result1 = actual1
        .value
        .flatMap(actualValue =>
          expected1.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      val actual1_1 = input1.eitherT
      val result1_1 = actual1_1
        .value
        .flatMap(actualValue =>
          expected1.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      /* Test case 2: Either[A, B].eitherT[F] */
      val input2    = n.asRight[String]
      val expected2 = EitherT(Applicative[IO].pure(input2))

      val actual2 = input2.eitherT[IO]
      val result2 = actual2
        .value
        .flatMap(actualValue =>
          expected2.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      val actual2_1 = input2.eitherT[IO]
      val result2_1 = actual2_1
        .value
        .flatMap(actualValue =>
          expected2.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      /* Test case 3: F[A].rightT[B] */
      val input3    = IO(n)
      val actual3   = input3.rightT[String]
      val expected3 = EitherT(input3.map(_.asRight[String]))
      val result3   = actual3
        .value
        .flatMap(actualValue =>
          expected3.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      /* Test case 4: F[B].leftT[A] */
      val input4    = IO(s)
      val actual4   = input4.leftT[Int]
      val expected4 = EitherT(input4.map(_.asLeft[Int]))
      val result4   = actual4
        .value
        .flatMap(actualValue =>
          expected4.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      /* Test case 5: A.rightTF[F, B] */
      val input5    = n
      val actual5   = input5.rightTF[IO, String]
      val expected5 = EitherT(IO(input5.asRight[String]))
      val result5   = actual5
        .value
        .flatMap(actualValue =>
          expected5.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      /* Test case 6: B.leftTF[F, A] */
      val input6    = s
      val actual6   = input6.leftTF[IO, Int]
      val expected6 = EitherT(IO(input6.asLeft[Int]))
      val result6   = actual6
        .value
        .flatMap(actualValue =>
          expected6.value.map(expectedValue => Assertions.assertEquals(actualValue, expectedValue))
        )
        .unsafeToFuture()

      for {
        _ <- result1
        _ <- result1_1
        _ <- result2
        _ <- result2_1
        _ <- result3
        _ <- result4
        _ <- result5
        _ <- result6
      } yield ()
    }

  }

  object FOfEitherInnerOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.effect.*
    import extras.cats.syntax.all.*

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
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerFind(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerFilterOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }
      val s        = RandomGens.genAlphaNumericString(10)

      val eitherSI = toEither(RandomGens.genAlphaNumericString(10), RandomGens.genRandomIntWithMinMax(n, max))

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.filterOrElse(filterF1, s)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.filterOrElse(filterF2, s)

      input1
        .innerFilterOrElse(filterF1, s)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerFilterOrElse(filterF2, s)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerExists(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }

      val eitherSI = toEither(RandomGens.genAlphaNumericString(10), RandomGens.genRandomIntWithMinMax(n, max))

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.exists(filterF1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.exists(filterF2)

      input1
        .innerExists(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerExists(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerForall(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }

      val eitherSI = toEither(RandomGens.genAlphaNumericString(10), RandomGens.genRandomIntWithMinMax(n, max))

      val filterF1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val filterF2 = NamedFunction("Always false")((_: Int) => false)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.forall(filterF1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.forall(filterF2)

      input1
        .innerForall(filterF1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerForall(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerContains(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }
      val s        = RandomGens.genAlphaNumericString(10)

      val eitherSI = toEither(s, RandomGens.genRandomIntWithMinMax(n, max))

      val toCompare1 = eitherSI.getOrElse(n) // Value that should be in Right, or n if Left
      val toCompare2 = n + 10 // Value that should not be in Right (unless n+10 was the random value)

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.contains(toCompare1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.contains(toCompare2)

      input1
        .innerContains(toCompare1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerContains(toCompare2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerCollectFirst(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val (n, max) = {
        val n1 = RandomGens.genRandomInt()
        (if (n1 == Int.MaxValue) n1 - 1 else n1, n1 + 1)
      }
      val s        = RandomGens.genAlphaNumericString(10)

      val eitherSI = toEither(s, RandomGens.genRandomIntWithMinMax(n, max))
      val _        = n + 10

      val pf1: PartialFunction[Int, Int] = {
        case x if x == eitherSI.getOrElse(Int.MinValue) => x // Should match if Right
      }
      val pf2: PartialFunction[Int, Int] = {
        case x if x == x + 1 => x // It always shouldn't match.
      }

      val input1    = fab[IO, String, Int](eitherSI)
      val expected1 = eitherSI.collectFirst(pf1)

      val input2    = fab[IO, String, Int](eitherSI)
      val expected2 = eitherSI.collectFirst(pf2)

      input1
        .innerCollectFirst(pf1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .unsafeToFuture()
        .flatMap { _ =>
          input2
            .innerCollectFirst(pf2)
            .map(actual => Assertions.assertEquals(actual, expected2))
            .unsafeToFuture()
        }
    }

    def testInnerMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n = RandomGens.genRandomInt()
      val s = RandomGens.genAlphaNumericString(10)

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
      val n = RandomGens.genRandomInt()
      val s = RandomGens.genAlphaNumericString(10)

      val eitherSI                      = toEither(s, n)
      val f: Int => Either[String, Int] = n => (n * 2).asRight

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.flatMap(f)

      input
        .innerFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMapF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n = RandomGens.genRandomInt()
      val s = RandomGens.genAlphaNumericString(10)

      val eitherSI                          = toEither(s, n)
      val f: Int => IO[Either[String, Int]] = n => IO.pure((n * 2).asRight)

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.flatMap(n => (n * 2).asRight)

      input
        .innerFlatMapF(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n      = RandomGens.genRandomInt()
      val s      = RandomGens.genAlphaNumericString(10)
      val prefix = RandomGens.genAlphaNumericString(5)

      val eitherSI            = toEither(s, n)
      val f: String => String = prefix + _

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftMap(f)

      input
        .innerLeftMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftFlatMap(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n      = RandomGens.genRandomInt()
      val s      = RandomGens.genAlphaNumericString(10)
      val prefix = RandomGens.genAlphaNumericString(5)

      val eitherSI                         = toEither(s, n)
      val f: String => Either[String, Int] = a => (prefix + a).asLeft[Int]

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftFlatMap(f)

      input
        .innerLeftFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerLeftFlatMapF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n      = RandomGens.genRandomInt()
      val s      = RandomGens.genAlphaNumericString(10)
      val prefix = RandomGens.genAlphaNumericString(5)

      val eitherSI                             = toEither(s, n)
      val f: String => IO[Either[String, Int]] = a => IO.pure((prefix + a).asLeft[Int])

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.leftFlatMap(a => (prefix + a).asLeft[Int])

      input
        .innerLeftFlatMapF(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n           = RandomGens.genRandomInt()
      val s           = RandomGens.genAlphaNumericString(10)
      val defaultLeft = n + 10

      val eitherSI = toEither(s, n)

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.getOrElse(defaultLeft)

      input
        .innerGetOrElse(defaultLeft)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElseF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n           = RandomGens.genRandomInt()
      val s           = RandomGens.genAlphaNumericString(10)
      val defaultLeft = n + 10

      val eitherSI = toEither(s, n)

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.getOrElse(defaultLeft)

      input
        .innerGetOrElseF(IO.pure(defaultLeft))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElse(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n             = RandomGens.genRandomInt()
      val s             = RandomGens.genAlphaNumericString(10)
      val defaultEither = (n + 10).asRight[String]

      val eitherSI = toEither(s, n)

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.orElse(defaultEither)

      input
        .innerOrElse(defaultEither)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElseF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n             = RandomGens.genRandomInt()
      val s             = RandomGens.genAlphaNumericString(10)
      val defaultEither = (n + 10).asRight[String]

      val eitherSI = toEither(s, n)

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.orElse(defaultEither)

      input
        .innerOrElseF(IO.pure(defaultEither))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFold(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n           = RandomGens.genRandomInt()
      val s           = RandomGens.genAlphaNumericString(10)
      val defaultLeft = RandomGens.genRandomInt()

      val eitherSI = toEither(s, n)

      val f: Int => Int     = _ * 2
      val lf: String => Int = leftValue => if (leftValue === s) defaultLeft else defaultLeft + 10

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.fold(lf, f)

      input
        .innerFold(lf)(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFoldF(toEither: (String, Int) => Either[String, Int]): Future[Unit] = {
      val n           = RandomGens.genRandomInt()
      val s           = RandomGens.genAlphaNumericString(10)
      val defaultLeft = RandomGens.genRandomInt()

      val eitherSI = toEither(s, n)

      val f: Int => Int     = x => x * 2
      val lf: String => Int = leftValue => if (leftValue === s) defaultLeft else defaultLeft + 10

      val input    = fab[IO, String, Int](eitherSI)
      val expected = eitherSI.fold(lf, f)

      val fLf = IO.pure.compose(lf)
      val ff  = IO.pure.compose(f)

      input
        .innerFoldF(fLf)(ff)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

  }

}
