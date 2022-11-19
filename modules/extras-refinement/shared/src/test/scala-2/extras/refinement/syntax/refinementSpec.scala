package extras.refinement.syntax

import cats.data.NonEmptyChain
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.types.string.NonEmptyString
import hedgehog._
import hedgehog.runner._
import io.estatico.newtype.macros.newtype

/** @author Kevin Lee
  * @since 2022-03-14
  */
object refinementSpec extends Properties {
  override def tests: List[Test] = allTests(extras.refinement.syntax.refinement)

  def allTests(refinementSyn: refinement): List[Test] = List(
    property(
      "test validate[A](String) => Right(A(NonEmptyString))",
      testValidateAsAStringToANonEmptyStringSyntax(refinementSyn),
    ),
    property(
      "test String.as[A].validate => Right(A(NonEmptyString))",
      testStringAsValidateToANonEmptyStringSyntax(refinementSyn),
    ),
    example("test validate[A](\"\") => Left(error)", testValidateAsAStringToErrorSyntax(refinementSyn)),
    example("test \"\".as[A].validate => Left(error)", testStringAsValidateToErrorSyntax(refinementSyn)),
    property(
      "test validate[A](Int) => Right(A(Int Refined Positive))",
      testValidateAsAIntToAIntRefinedPositiveSyntax(refinementSyn),
    ),
    property(
      "test Int.as[A].validate => Right(A(Int Refined Positive))",
      testIntAsValidateToAIntRefinedPositiveSyntax(refinementSyn),
    ),
    property("test validate[A](non positive Int) => Left(error)", testValidateAsAIntToErrorSyntax(refinementSyn)),
    property("test (non positive Int).as[A].validate => Left(error)", testIntAsValidateToErrorSyntax(refinementSyn)),
    property("test newtype(refined(T)).toValue should return T", testCoerciblerefinementSynToValue(refinementSyn)),
  )

  def testValidateAsAStringToANonEmptyStringSyntax(refinementSyn: refinement): Property = for {
    nonEmptyString <- Gen.string(Gen.unicode, Range.linear(1, 10)).log("nonEmptyString")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = validateAs[Name](nonEmptyString)
    actual ==== Right(Name(NonEmptyString.unsafeFrom(nonEmptyString)))
  }

  def testStringAsValidateToANonEmptyStringSyntax(refinementSyn: refinement): Property = for {
    nonEmptyString <- Gen.string(Gen.unicode, Range.linear(1, 10)).log("nonEmptyString")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = nonEmptyString.validateAs[Name]
    actual ==== Right(Name(NonEmptyString.unsafeFrom(nonEmptyString)))
  }

  def testValidateAsAStringToErrorSyntax(refinementSyn: refinement): Result = {
    import TypesForTesting._
    import refinementSyn._
    val input  = ""
    val actual = validateAs[Name](input)
    actual ==== Left(NonEmptyChain("Failed to create TypesForTesting.Name: Predicate isEmpty() did not fail."))
  }

  def testStringAsValidateToErrorSyntax(refinementSyn: refinement): Result = {
    import TypesForTesting._
    import refinementSyn._
    val input  = ""
    val actual = input.validateAs[Name]
    actual ==== Left(NonEmptyChain("Failed to create TypesForTesting.Name: Predicate isEmpty() did not fail."))
  }

  def testValidateAsAIntToAIntRefinedPositiveSyntax(refinementSyn: refinement): Property = for {
    positiveInt <- Gen.int(Range.linear(1, Int.MaxValue)).log("positiveInt")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = validateAs[Id](positiveInt)
    actual ==== Right(Id(PositiveInt.unsafeFrom(positiveInt)))
  }

  def testIntAsValidateToAIntRefinedPositiveSyntax(refinementSyn: refinement): Property = for {
    positiveInt <- Gen.int(Range.linear(1, Int.MaxValue)).log("positiveInt")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = positiveInt.validateAs[Id]
    actual ==== Right(Id(PositiveInt.unsafeFrom(positiveInt)))
  }

  def testValidateAsAIntToErrorSyntax(refinementSyn: refinement): Property = for {
    nonPositiveInt <- Gen.int(Range.linear(Int.MinValue, 0)).log("nonPositiveInt")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = validateAs[Id](nonPositiveInt)
    actual ==== Left(
      NonEmptyChain(s"Failed to create TypesForTesting.Id: Predicate failed: (${nonPositiveInt.toString} > 0).")
    )
  }

  def testIntAsValidateToErrorSyntax(refinementSyn: refinement): Property = for {
    nonPositiveInt <- Gen.int(Range.linear(Int.MinValue, 0)).log("nonPositiveInt")
  } yield {
    import TypesForTesting._
    import refinementSyn._
    val actual = nonPositiveInt.validateAs[Id]
    actual ==== Left(
      NonEmptyChain(s"Failed to create TypesForTesting.Id: Predicate failed: (${nonPositiveInt.toString} > 0).")
    )
  }

  def testCoerciblerefinementSynToValue(refinementSyn: refinement): Property = for {
    s <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).log("s")
    n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
  } yield {
    import TypesForTesting._
    import refinementSyn._

    val name = Name(NonEmptyString.unsafeFrom(s))
    val id   = Id(PositiveInt.unsafeFrom(n))

    val expected1 = s
    val actual1   = name.toValue

    val expected2 = n
    val actual2   = id.toValue

    Result.all(
      List(
        (actual1.getClass.getName ==== "java.lang.String")
          .log("""Failed: actual1.getClass.getName ==== "java.lang.String""""),
        (actual1 ==== expected1).log("Failed: actual1 ==== expected1"),
        (actual2.getClass.getName ==== "int").log("""actual2.getClass.getName ==== "int""""),
        (actual2 ==== expected2).log("actual2 ==== expected2"),
      )
    )
  }

  object TypesForTesting {
    @newtype case class Name(value: NonEmptyString)

    type PositiveInt = Int Refined Positive
    object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
    @newtype case class Id(value: PositiveInt)

  }
}
