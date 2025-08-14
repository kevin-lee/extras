package extras.cats.syntax

import cats.effect.unsafe.IORuntime
import cats.syntax.all.*
import extras.cats.testing.{ExecutionContextProvider, IoAppUtils}
import extras.concurrent.testing.ConcurrentSupport
import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2021-08-24
  */
object AllSyntaxSpec extends Properties {
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
      "OptionTFAOpsSpec.testSomeT",
      OptionTFAOpsSpec.testSomeT,
    ),
    property(
      "OptionTAOpsSpec.testSomeTF",
      OptionTAOpsSpec.testSomeTF,
    ),
    property(
      "OptionTSupportAllSpec.testAll",
      OptionTSupportAllSpec.testAll,
    ),
    property(
      "test F[Option[A]].innerFilter(A => Boolean): F[Option[A]]",
      FOfOptionInnerOpsSpec.testInnerFilter,
    ),
    property(
      "test F[Option[A]].innerExists(A => Boolean): F[Boolean]",
      FOfOptionInnerOpsSpec.testInnerExists,
    ),
    property(
      "test F[Option[A]].innerContains(A): F[Boolean]",
      FOfOptionInnerOpsSpec.testInnerContains,
    ),
    property(
      "test F[Option[A]].innerForall(A => Boolean): F[Boolean]",
      FOfOptionInnerOpsSpec.testInnerForall,
    ),
    property(
      "test F[Option[A]].innerCollect(PartialFunction[A, B]): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerCollect,
    ),
    property(
      "test F[Option[A]].innerMap(A => B): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerMap,
    ),
    property(
      "test F[Option[A]].innerFlatMap(A => Option[B]): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerFlatMap,
    ),
    property(
      "test F[Option[A]].innerFlatMapF(A => F[Option[B]]): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerFlatMapF,
    ),
    property(
      "test F[Option[A]].innerGetOrElse[B >: A](=> B): F[B]",
      FOfOptionInnerOpsSpec.testInnerGetOrElse,
    ),
    property(
      "test F[Option[A]].innerGetOrElseF[B >: A](=> F[B]): F[B]",
      FOfOptionInnerOpsSpec.testInnerGetOrElseF,
    ),
    property(
      "test F[Option[A]].innerOrElse[B >: A](=> Option[B]): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerOrElse,
    ),
    property(
      "test F[Option[A]].innerOrElseF[B >: A](=> F[Option[B]]): F[Option[B]]",
      FOfOptionInnerOpsSpec.testInnerOrElseF,
    ),
    property(
      "test F[Option[A]].innerFold[B >: A](=> B)(A => B): F[B]",
      FOfOptionInnerOpsSpec.testInnerFold,
    ),
    property(
      "test F[Option[A]].innerFoldF[B >: A](=> F[B])(A => F[B]): F[B]",
      FOfOptionInnerOpsSpec.testInnerFoldF,
    ),
  ) ++ List(
    property(
      "EitherTFEitherOpsSpec.testEitherT",
      EitherTFEitherOpsSpec.testEitherT,
    ),
    property(
      "EitherTEitherOpsSpec.testEitherT",
      EitherTEitherOpsSpec.testEitherT,
    ),
    property(
      "EitherTFAOpsSpec.testRightT",
      EitherTFAOpsSpec.testRightT,
    ),
    property(
      "EitherTFAOpsSpec.testLeftT",
      EitherTFAOpsSpec.testLeftT,
    ),
    property(
      "EitherTAOpsSpec.testRightTF",
      EitherTAOpsSpec.testRightTF,
    ),
    property(
      "EitherTAOpsSpec.testLeftTF",
      EitherTAOpsSpec.testLeftTF,
    ),
    property(
      "testAll with EitherTSupport.*",
      EitherTSupportAllSpec.testAll,
    ),
    property(
      "test F[Either[A, B]].innerFind(B => Boolean): F[Option[B]",
      FOfEitherInnerOpsSpec.testInnerFind,
    ),
    property(
      "test F[Either[A, B]].innerFilterOrElse[C >: A](B => Boolean, => C): F[Either[C, B]]",
      FOfEitherInnerOpsSpec.testInnerFilterOrElse,
    ),
    property(
      "test F[Either[A, B]].innerExists(B => Boolean): F[Boolean]",
      FOfEitherInnerOpsSpec.testInnerExists,
    ),
    property(
      "test F[Either[A, B]].innerForall(B => Boolean): F[Boolean]",
      FOfEitherInnerOpsSpec.testInnerForall,
    ),
    property(
      "test F[Either[A, B]].innerContains(B): F[Boolean]",
      FOfEitherInnerOpsSpec.testInnerContains,
    ),
    property(
      "test F[Either[A, B]].innerCollectFirst[D >: B](PartialFunction[B, D]): F[D]",
      FOfEitherInnerOpsSpec.testInnerCollectFirst,
    ),
    property(
      "test F[Either[A, B]].innerMap(B => D): F[Either[A, D]]",
      FOfEitherInnerOpsSpec.testInnerMap,
    ),
    property(
      "test F[Either[A, B]].innerFlatMap(B => Either[A, D]): F[Either[A, D]]",
      FOfEitherInnerOpsSpec.testInnerFlatMap,
    ),
    property(
      "test F[Either[A, B]].innerFlatMapF(B => F[Either[A, D]]): F[Either[A, D]]",
      FOfEitherInnerOpsSpec.testInnerFlatMapF,
    ),
    property(
      "test F[Either[A, B]].innerLeftMap(A => C): F[Either[C, B]]",
      FOfEitherInnerOpsSpec.testInnerLeftMap,
    ),
    property(
      "test F[Either[A, B]].innerLeftFlatMap(A => Either[C, B]): F[Either[C, B]]",
      FOfEitherInnerOpsSpec.testInnerLeftFlatMap,
    ),
    property(
      "test F[Either[A, B]].innerLeftFlatMapF(A => F[Either[C, B]]): F[Either[C, B]]",
      FOfEitherInnerOpsSpec.testInnerLeftFlatMapF,
    ),
    property(
      "test F[Either[A, B]].innerGetOrElse[D >: B](=> B): F[D]",
      FOfEitherInnerOpsSpec.testInnerGetOrElse,
    ),
    property(
      "test F[Either[A, B]].innerGetOrElseF[D >: B](=> F[B]): F[D]",
      FOfEitherInnerOpsSpec.testInnerGetOrElseF,
    ),
    property(
      "test F[Either[A, B]].innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]]",
      FOfEitherInnerOpsSpec.testInnerOrElse,
    ),
    property(
      "test F[Either[A, B]].innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]]",
      FOfEitherInnerOpsSpec.testInnerOrElseF,
    ),
    property(
      "test F[Either[A, B]].innerFold[D](=> D)(B => D): F[D]",
      FOfEitherInnerOpsSpec.testInnerFold,
    ),
    property(
      "test F[Either[A, B]].innerFoldF[D](=> F[D])(B => F[D]): F[D]",
      FOfEitherInnerOpsSpec.testInnerFoldF,
    ),
  )

  object OptionTFOptionOpsSpec {
    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.option.{optionT, t}

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
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
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
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTOptionOpsSpec {
    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.option.{optionT, t}

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
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
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
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTFAOpsSpec {
    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import extras.cats.syntax.option.someT

    def testSomeT: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = Applicative[IO].pure(n)
      val expected      = OptionT.liftF(input)
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.someT
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTAOpsSpec {
    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.Applicative
    import cats.data.OptionT
    import cats.effect.IO
    import cats.syntax.option.*
    import extras.cats.syntax.option.someTF

    def testSomeTF: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input         = n
      val expected      = OptionT(Applicative[IO].pure(input.some))
      val expectedValue = expected.value.unsafeRunSync()

      val actual      = input.someTF[IO]
      val actualValue = actual.value.unsafeRunSync()

      Result.all(
        List(
          Result.assert(actualValue.isDefined).log(s"actualValue should be Some. actualValue: $actualValue"),
          actualValue ==== expectedValue,
        )
      )
    }

  }

  object OptionTSupportAllSpec {
    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.Applicative
    import cats.data.*
    import cats.effect.*
    import cats.syntax.option.*
    import extras.cats.syntax.option.*

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testAll: Property = for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      val input1         = fab[IO, Int](n.some)
      val expected1      = OptionT(input1)
      val expected1Value = expected1.value.unsafeRunSync()

      val actual1      = input1.optionT
      val actual1Value = actual1.value.unsafeRunSync()

      val input2         = n.some
      val expected2      = OptionT(Applicative[IO].pure(input2))
      val expected2Value = expected2.value.unsafeRunSync()

      val actual2      = input2.optionT[IO]
      val actual2Value = actual2.value.unsafeRunSync()

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
          Result.assert(actual1Value.isDefined).log(s"actual1Value should be Some. actual1Value: $actual1Value"),
          actual1Value ==== expected1Value,
          Result.assert(actual2Value.isDefined).log(s"actual2Value should be Some. actual2Value: $actual2Value"),
          actual2Value ==== expected2Value,
          Result.assert(actual3Value.isDefined).log(s"actual3Value should be Some. actual3Value: $actual3Value"),
          actual3Value ==== expected3Value,
          Result.assert(actual4Value.isDefined).log(s"actual4Value should be Some. actual4Value: $actual4Value"),
          actual4Value ==== expected4Value,
        )
      )

    }

  }

  object FOfOptionInnerOpsSpec {

    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.effect.*
    import extras.cats.syntax.all.*

    def fab[F[_]: Sync, A](oa: Option[A]): F[Option[A]] = Sync[F].delay(oa)

    def testInnerFilter: Property =
      for {
        n       <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        optionN <- Gen.constant(n).option.log("optionN")
        filterF <- Gen
                     .element1(
                       NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                       NamedFunction("Always false")((_: Int) => false),
                     )
                     .log("filterF")
      } yield {

        val input    = fab[IO, Int](optionN)
        val expected = optionN.filter(filterF)

        input
          .innerFilter(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerExists: Property =
      for {
        n       <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        optionN <- Gen.constant(n).option.log("optionN")
        filterF <- Gen
                     .element1(
                       NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                       NamedFunction("Always false")((_: Int) => false),
                     )
                     .log("filterF")
      } yield {

        val input    = fab[IO, Int](optionN)
        val expected = optionN.exists(filterF)

        input
          .innerExists(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerContains: Property =
      for {
        n         <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        optionN   <- Gen.constant(n).option.log("optionN")
        toCompare <- Gen
                       .element1(
                         n,
                         n + 10,
                       )
                       .log("toCompare")
      } yield {

        val input    = fab[IO, Int](optionN)
        val expected = optionN.contains(toCompare)

        input
          .innerContains(toCompare)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerForall: Property =
      for {
        n       <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        optionN <- Gen.constant(n).option.log("optionN")
        filterF <- Gen
                     .element1(
                       NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                       NamedFunction("Always false")((_: Int) => false),
                     )
                     .log("filterF")
      } yield {

        val input    = fab[IO, Int](optionN)
        val expected = optionN.forall(filterF)

        input
          .innerForall(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerCollect: Property =
      for {
        n       <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        optionN <- Gen.constant(n).option.log("optionN")
        filterF <- Gen
                     .element1(
                       NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                       NamedFunction("Always false")((_: Int) => false),
                     )
                     .log("filterF")

      } yield {
        val alternative = n + 10

        val input = fab[IO, Int](optionN)

        val pf: PartialFunction[Int, Int] = {
          case `n` => if (filterF(n)) n else alternative
        }

        val expected = optionN.collect(pf)

        input
          .innerCollect(pf)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

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

    def testInnerFlatMap: Property =
      for {
        optionN <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).option.log("optionN")
      } yield {
        val f: Int => Option[Int] = n => (n * 2).some

        val input    = fab[IO, Int](optionN)
        val expected = optionN.flatMap(f)

        input
          .innerFlatMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFlatMapF: Property =
      for {
        optionN <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).option.log("optionN")
      } yield {
        val f: Int => Option[Int] = n => (n * 2).some

        val input    = fab[IO, Int](optionN)
        val expected = optionN.flatMap(f)

        input
          .innerFlatMapF(a => IO.pure(f(a)))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerGetOrElse: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val input    = fab[IO, Int](optionN)
        val expected = optionN.getOrElse(defaultValue)

        input
          .innerGetOrElse(defaultValue)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerGetOrElseF: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val input    = fab[IO, Int](optionN)
        val expected = optionN.getOrElse(defaultValue)

        input
          .innerGetOrElseF(IO.pure(defaultValue))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerOrElse: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val defaultOption = defaultValue.some

        val input    = fab[IO, Int](optionN)
        val expected = optionN.orElse(defaultOption)

        input
          .innerOrElse(defaultOption)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerOrElseF: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val defaultOption = defaultValue.some

        val input    = fab[IO, Int](optionN)
        val expected = optionN.orElse(defaultOption)

        input
          .innerOrElseF(IO.pure(defaultOption))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFold: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val f: Int => Int = _ * 2

        val input    = fab[IO, Int](optionN)
        val expected = optionN.fold(defaultValue)(f)

        input
          .innerFold(defaultValue)(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFoldF: Property =
      for {
        defaultValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        optionN      <- Gen
                          .int(Range.linear(Int.MinValue, Int.MaxValue))
                          .map { n => if (n === defaultValue) n + 1 else n }
                          .option
                          .log("optionN")
      } yield {
        val f: Int => Int = _ * 2
        val input         = fab[IO, Int](optionN)
        val expected      = optionN.fold(defaultValue)(f)

        input
          .innerFoldF(IO.pure(defaultValue))(a => IO.pure(f(a)))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

  }

  object EitherTFEitherOpsSpec {

    val ecp             = new ExecutionContextProvider
    given rt: IORuntime = IoAppUtils.runtime(ecp.es)

    import cats.data.*
    import cats.effect.*
    import cats.syntax.either.*
    import extras.cats.syntax.EitherSyntax.eitherT

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
          actualValue ==== expected.value.unsafeRunSync(),
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
    import extras.cats.syntax.EitherSyntax.eitherT

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
          actualValue ==== expected.value.unsafeRunSync(),
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
    import extras.cats.syntax.EitherSyntax.{leftT, rightT}

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
          actualValue ==== expected.value.unsafeRunSync(),
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
          actualValue ==== expected.value.unsafeRunSync(),
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
    import extras.cats.syntax.EitherSyntax.{leftTF, rightTF}

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
          actualValue ==== expected.value.unsafeRunSync(),
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
          actualValue ==== expected.value.unsafeRunSync(),
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
      import extras.cats.syntax.EitherSyntax.*
      import cats.Applicative
      import cats.data.EitherT
      import cats.effect.*
      import cats.syntax.all.*

      val input1         = EitherTFEitherOpsSpec.fab[IO, String, Int](n.asRight[String])
      val expected1      = EitherT(input1)
      val expected1Value = expected1.value.unsafeRunSync()

      val actual1      = input1.eitherT
      val actual1Value = actual1.value.unsafeRunSync()

      val actual1_1      = input1.eitherT
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
          actual6Value ==== expected6Value,
        )
      )
    }

  }

  object FOfEitherInnerOpsSpec {

    val es              = ConcurrentSupport.newExecutorService(2)
    given rt: IORuntime = IoAppUtils.runtime(es)

    import cats.effect.*
    import extras.cats.syntax.all.*

    def fab[F[_]: Sync, A, B](eab: Either[A, B]): F[Either[A, B]] = Sync[F].delay(eab)

    def testInnerFind: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
        filterF  <- Gen
                      .element1(
                        NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                        NamedFunction("Always false")((_: Int) => false),
                      )
                      .log("filterF")

      } yield {

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.find(filterF)

        input
          .innerFind(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFilterOrElse: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
        s        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
        filterF  <- Gen
                      .element1(
                        NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                        NamedFunction("Always false")((_: Int) => false),
                      )
                      .log("filterF")

      } yield {

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.filterOrElse(filterF, s)

        input
          .innerFilterOrElse(filterF, s)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerExists: Property =
      for {
        n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")

        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")
        filterF  <- Gen
                      .element1(
                        NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                        NamedFunction("Always false")((_: Int) => false),
                      )
                      .log("filterF")
      } yield {

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.exists(filterF)

        input
          .innerExists(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerForall: Property =
      for {
        n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")

        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")
        filterF  <- Gen
                      .element1(
                        NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                        NamedFunction("Always false")((_: Int) => false),
                      )
                      .log("filterF")
      } yield {

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.forall(filterF)

        input
          .innerForall(filterF)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerContains: Property =
      for {
        n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")

        eitherSI  <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")
        toCompare <- Gen
                       .element1(
                         n,
                         n + 10,
                       )
                       .log("toCompare")
      } yield {

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.contains(toCompare)

        input
          .innerContains(toCompare)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerCollectFirst: Property =
      for {
        n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")

        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")

        filterF <- Gen
                     .element1(
                       NamedFunction(s"The number should be equal to ${n.toString}")((a: Int) => a === n),
                       NamedFunction("Always false")((_: Int) => false),
                     )
                     .log("filterF")

      } yield {
        val alternative = n + 10

        val input = fab[IO, String, Int](eitherSI)

        val pf: PartialFunction[Int, Int] = {
          case `n` => if (filterF(n)) n else alternative
        }

        val expected = eitherSI.collectFirst(pf)

        input
          .innerCollectFirst(pf)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerMap: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
      } yield {
        val f: Int => Int = _ * 2

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.map(f)

        input
          .innerMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFlatMap: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
      } yield {
        val f: Int => Either[String, Int] = n => (n * 2).asRight

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.flatMap(f)

        input
          .innerFlatMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFlatMapF: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
      } yield {
        val f: Int => Either[String, Int] = n => (n * 2).asRight

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.flatMap(f)

        input
          .innerFlatMapF(a => IO.pure(f(a)))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerLeftMap: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
        prefix   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("prefix")
      } yield {
        val f: String => String = prefix + _

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.leftMap(f)

        input
          .innerLeftMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerLeftFlatMap: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
        prefix   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("prefix")
      } yield {
        val f: String => Either[String, Int] = a => (prefix + a).asLeft[Int]

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.leftFlatMap(f)

        input
          .innerLeftFlatMap(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerLeftFlatMapF: Property =
      for {
        eitherSI <- Gen
                      .choice1(
                        Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                        Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                      )
                      .log("eitherSI")
        prefix   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("prefix")
      } yield {
        val f: String => Either[String, Int] = a => (prefix + a).asLeft[Int]

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.leftFlatMap(f)

        input
          .innerLeftFlatMapF(a => IO.pure(f(a)))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerGetOrElse: Property =
      for {
        defaultLeft <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        eitherSI    <- Gen
                         .choice1(
                           Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                           Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                         )
                         .log("eitherSI")
      } yield {
        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.getOrElse(defaultLeft)

        input
          .innerGetOrElse(defaultLeft)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerGetOrElseF: Property =
      for {
        defaultLeft <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
        eitherSI    <- Gen
                         .choice1(
                           Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(_.asRight[String]),
                           Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(_.asLeft[Int]),
                         )
                         .log("eitherSI")
      } yield {
        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.getOrElse(defaultLeft)

        input
          .innerGetOrElseF(IO.pure(defaultLeft))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerOrElse: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")

        prefix        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("prefix")
        defaultEither <- Gen.element1((n + 10).asRight[String], (prefix + s).asLeft[Int]).log("defaultEither")

      } yield {
        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.orElse(defaultEither)

        input
          .innerOrElse(defaultEither)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerOrElseF: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")

        prefix        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("prefix")
        defaultEither <- Gen.element1((n + 10).asRight[String], (prefix + s).asLeft[Int]).log("defaultEither")

      } yield {
        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.orElse(defaultEither)

        input
          .innerOrElseF(IO.pure(defaultEither))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFold: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")

        defaultLeft <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
      } yield {
        val f: Int => Int     = _ * 2
        val lf: String => Int = leftValue => if (leftValue === s) defaultLeft else defaultLeft + 10

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.fold(_ => defaultLeft, f)

        input
          .innerFold(lf)(f)
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

    def testInnerFoldF: Property =
      for {
        n        <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
        s        <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("s")
        eitherSI <- Gen.element1(n.asRight[String], s.asLeft[Int]).log("eitherSI")

        defaultLeft <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("defaultValue")
      } yield {
        val f: Int => Int     = _ * 2
        val lf: String => Int = leftValue => if (leftValue === s) defaultLeft else defaultLeft + 10

        val input    = fab[IO, String, Int](eitherSI)
        val expected = eitherSI.fold(_ => defaultLeft, f)

        input
          .innerFoldF(l => IO.pure(lf(l)))(a => IO.pure(f(a)))
          .map(actual => actual ==== expected)
      }.unsafeRunSync()

  }

}
