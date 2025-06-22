package extras.cats.syntax

import cats.syntax.all.*
import cats.effect.unsafe.IORuntime
import extras.cats.testing.{ExecutionContextProvider, IoAppUtils, RandomGens}
import munit.Assertions

import scala.concurrent.{ExecutionContext, Future}

/** @author Kevin Lee
  * @since 2021-08-22
  */
class OptionSyntaxSpec extends munit.FunSuite {

  private val testNamePrefix = " 🟢 OptionSyntaxSpec."

  test(s"${testNamePrefix}OptionTFOptionOpsSpec.testOptionT")(OptionTFOptionOpsSpec.testOptionT())
  test(s"${testNamePrefix}OptionTFOptionOpsSpec.testT")(OptionTFOptionOpsSpec.testT())
  test(s"${testNamePrefix}OptionTOptionOpsSpec.testOptionT")(OptionTOptionOpsSpec.testOptionT())
  test(s"${testNamePrefix}OptionTOptionOpsSpec.testT")(OptionTOptionOpsSpec.testT())
  test(s"${testNamePrefix}OptionTFAOpsSpec.testOptionT")(OptionTFAOpsSpec.testOptionT())
  test(s"${testNamePrefix}OptionTAOpsSpec.testOptionTF")(OptionTAOpsSpec.testOptionTF())
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
  test(s"${testNamePrefix}test F[Option[A]].innerMap(A => B): F[Option[B]] - None case")(
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

  object OptionTFOptionOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.option.{optionT, t}

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testOptionT(): Future[Unit] = {

      val n = RandomGens.genRandomInt()

      val input         = fab[IO, Int](n.some)
      val expectedValue = n

      val actual: OptionT[IO, Int] = input.optionT
      actual
        .value
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

      val actual: OptionT[IO, Int] = input.t
      actual
        .value
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

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.option.{optionT, t}

    def testOptionT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = n.some
      val expected = OptionT(Applicative[IO].pure(input))

      val actual: OptionT[IO, Int] = input.optionT[IO]
      actual
        .value
        .flatMap { actualOption =>
          expected.value.map { expectedOption =>
            Assertions.assert(actualOption.isDefined, s"actualOption should be Some. actualOption: $actualOption")
            Assertions.assertEquals(actualOption, expectedOption)
          }
        }
        .unsafeToFuture()
    }

    def testT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = n.some
      val expected = OptionT(Applicative[IO].pure(n.some))

      val actual: OptionT[IO, Int] = input.t[IO]
      actual
        .value
        .flatMap { actualOption =>
          expected.value.map { expectedOption =>
            Assertions.assert(actualOption.isDefined, s"actualOption should be Some. actualOption: $actualOption")
            Assertions.assertEquals(actualOption, expectedOption)
          }
        }
        .unsafeToFuture()
    }

  }

  object OptionTFAOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import extras.cats.syntax.option.someT

    def testOptionT(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = Applicative[IO].pure(n)
      val expected = OptionT.liftF(input)

      val actual = input.someT
      actual
        .value
        .flatMap { actualOption =>
          expected.value.map { expectedOption =>
            Assertions.assert(actualOption.isDefined, s"actualOption should be Some. actualOption: $actualOption")
            Assertions.assertEquals(actualOption, expectedOption)
          }
        }
        .unsafeToFuture()
    }

  }

  object OptionTAOpsSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.option.someTF

    def testOptionTF(): Future[Unit] = {
      val n        = RandomGens.genRandomInt()
      val input    = n
      val expected = OptionT(Applicative[IO].pure(input.some))

      val actual = input.someTF[IO]
      actual
        .value
        .flatMap { actualOption =>
          expected.value.map { expectedOption =>
            Assertions.assert(actualOption.isDefined, s"actualOption should be Some. actualOption: $actualOption")
            Assertions.assertEquals(actualOption, expectedOption)
          }
        }
        .unsafeToFuture()
    }

  }

  object OptionTSupportAllSpec {
    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.Applicative
    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.option.*

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testAll(): Future[Unit] = {
      val n = RandomGens.genRandomInt()

      val input1    = fab[IO, Int](n.some)
      val expected1 = OptionT(input1)

      val actual1   = input1.optionT
      val actual1_1 = input1.t

      val input2    = n.some
      val expected2 = OptionT(Applicative[IO].pure(input2))

      val actual2   = input2.optionT[IO]
      val actual2_1 = input2.t[IO]

      val input3    = Applicative[IO].pure(n)
      val expected3 = OptionT.liftF(input3)

      val actual3 = input3.someT

      val input4    = n
      val expected4 = OptionT(Applicative[IO].pure(input4.some))

      val actual4 = input4.someTF[IO]

      val test1   =
        actual1.value.flatMap { actual =>
          expected1.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }
      val test1_1 =
        actual1_1.value.flatMap { actual =>
          expected1.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }
      val test2   =
        actual2.value.flatMap { actual =>
          expected2.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }
      val test2_1 =
        actual2_1.value.flatMap { actual =>
          expected2.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }
      val test3   =
        actual3.value.flatMap { actual =>
          expected3.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }
      val test4   =
        actual4.value.flatMap { actual =>
          expected4.value.map { expected =>
            Assertions.assertEquals(actual, expected)
          }
        }

      (for {
        _ <- test1
        _ <- test1_1
        _ <- test2
        _ <- test2_1
        _ <- test3
        _ <- test4
      } yield ()).unsafeToFuture()

    }

  }

  object FOfOptionInnerOpsSpec {

    given ec: ExecutionContext = IORuntime.defaultComputeExecutionContext
    given rt: IORuntime        = IoAppUtils.runtime

    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.option.*

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
        .flatMap { _ =>
          input
            .innerFilter(filterF2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()

    }

    def testInnerExists(toOption: Int => Option[Int]): Future[Unit] = {
      val n          = RandomGens.genRandomInt()
      val optionN    = toOption(n)
      val predicate1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val predicate2 = NamedFunction("Always false")((_: Int) => false)

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.exists(predicate1)
      val expected2 = optionN.exists(predicate2)

      input
        .innerExists(predicate1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input
            .innerExists(predicate2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerContains(toOption: Int => Option[Int]): Future[Unit] = {
      val n          = RandomGens.genRandomInt()
      val optionN    = toOption(n)
      val toCompare1 = n
      val toCompare2 = n + 1

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.contains(toCompare1)
      val expected2 = optionN.contains(toCompare2)

      input
        .innerContains(toCompare1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input
            .innerContains(toCompare2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerForall(toOption: Int => Option[Int]): Future[Unit] = {
      val n          = RandomGens.genRandomInt()
      val optionN    = toOption(n)
      val predicate1 = NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n)
      val predicate2 = NamedFunction("Always true")((_: Int) => true)
      val predicate3 = NamedFunction("Always false")((_: Int) => false)

      val input     = fab[IO, Int](optionN)
      val expected1 = optionN.forall(predicate1)
      val expected2 = optionN.forall(predicate2)
      val expected3 = optionN.forall(predicate3)

      input
        .innerForall(predicate1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input
            .innerForall(predicate2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .flatMap { _ =>
          input
            .innerForall(predicate3)
            .map(actual => Assertions.assertEquals(actual, expected3))
        }
        .unsafeToFuture()
    }

    def testInnerCollect(toOption: Int => Option[Int]): Future[Unit] = {
      val n           = RandomGens.genRandomInt()
      val alternative = n + 10
      val optionN     = toOption(n)

      val pf1: PartialFunction[Int, Int] = {
        case `n` => n
      }
      val pf2: PartialFunction[Int, Int] = {
        case i if i != n => alternative
      }

      val input = fab[IO, Int](optionN)

      val expected1 = optionN.collect(pf1)
      val expected2 = optionN.collect(pf2)

      input
        .innerCollect(pf1)
        .map(actual => Assertions.assertEquals(actual, expected1))
        .flatMap { _ =>
          input
            .innerCollect(pf2)
            .map(actual => Assertions.assertEquals(actual, expected2))
        }
        .unsafeToFuture()
    }

    def testInnerMap(toOption: Int => Option[Int]): Future[Unit] = {
      val n             = RandomGens.genRandomInt()
      val optionN       = toOption(n)
      val f: Int => Int = _ * 2

      val input    = fab[IO, Int](optionN)
      val expected = optionN.map(f)

      input
        .innerMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMap(toOption: Int => Option[Int]): Future[Unit] = {
      val n                     = RandomGens.genRandomInt()
      val optionN               = toOption(n)
      val f: Int => Option[Int] = n => (n * 2).some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.flatMap(f)

      input
        .innerFlatMap(f)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerFlatMapF(toOption: Int => Option[Int]): Future[Unit] = {
      val n                     = RandomGens.genRandomInt()
      val optionN               = toOption(n)
      val f: Int => Option[Int] = n => (n * 2).some

      val input    = fab[IO, Int](optionN)
      val expected = optionN.flatMap(f)

      input
        .innerFlatMapF(a => IO.pure(f(a)))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElse(toOption: Int => Option[Int]): Future[Unit] = {
      val n            = RandomGens.genRandomInt()
      val defaultValue = n + 10
      val optionN      = toOption(n)

      val input    = fab[IO, Int](optionN)
      val expected = optionN.getOrElse(defaultValue)

      input
        .innerGetOrElse(defaultValue)
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerGetOrElseF(toOption: Int => Option[Int]): Future[Unit] = {
      val n            = RandomGens.genRandomInt()
      val defaultValue = n + 10
      val optionN      = toOption(n)

      val input    = fab[IO, Int](optionN)
      val expected = optionN.getOrElse(defaultValue)

      input
        .innerGetOrElseF(IO.pure(defaultValue))
        .map(actual => Assertions.assertEquals(actual, expected))
        .unsafeToFuture()
    }

    def testInnerOrElse(toOption: Int => Option[Int]): Future[Unit] = {
      val n             = RandomGens.genRandomInt()
      val defaultValue  = n + 10
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
      val n             = RandomGens.genRandomInt()
      val defaultValue  = n + 10
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
      val n             = RandomGens.genRandomInt()
      val defaultValue  = n + 10
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
      val n             = RandomGens.genRandomInt()
      val defaultValue  = n + 10
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

}
