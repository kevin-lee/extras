package extras.refinement.syntax

import cats.data.NonEmptyList
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
  override def tests: List[Test] = List(
    property("test validate[A](String) => Right(A(NonEmptyString))", testValidateAsAStringToANonEmptyStringSyntax),
    property("test String.as[A].validate => Right(A(NonEmptyString))", testStringAsValidateToANonEmptyStringSyntax),
    example("test validate[A](\"\") => Left(error)", testValidateAsAStringToErrorSyntax),
    example("test \"\".as[A].validate => Left(error)", testStringAsValidateToErrorSyntax),
    property("test validate[A](Int) => Right(A(Int Refined Positive))", testValidateAsAIntToAIntRefinedPositiveSyntax),
    property("test Int.as[A].validate => Right(A(Int Refined Positive))", testIntAsValidateToAIntRefinedPositiveSyntax),
    property("test validate[A](non positive Int) => Left(error)", testValidateAsAIntToErrorSyntax),
    property("test (non positive Int).as[A].validate => Left(error)", testIntAsValidateToErrorSyntax)
  )

  def testValidateAsAStringToANonEmptyStringSyntax: Property = for {
    nonEmptyString <- Gen.string(Gen.unicode, Range.linear(1, 10)).log("nonEmptyString")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = validateAs[Name](nonEmptyString)
    actual ==== Right(Name(NonEmptyString.unsafeFrom(nonEmptyString)))
  }

  def testStringAsValidateToANonEmptyStringSyntax: Property = for {
    nonEmptyString <- Gen.string(Gen.unicode, Range.linear(1, 10)).log("nonEmptyString")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = nonEmptyString.as[Name].validate
    actual ==== Right(Name(NonEmptyString.unsafeFrom(nonEmptyString)))
  }

  def testValidateAsAStringToErrorSyntax: Result = {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val input  = ""
    val actual = validateAs[Name](input)
    actual ==== Left(NonEmptyList.of("Failed to create TypesForTesting.Name: Predicate isEmpty() did not fail."))
  }

  def testStringAsValidateToErrorSyntax: Result = {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val input  = ""
    val actual = input.as[Name].validate
    actual ==== Left(NonEmptyList.of("Failed to create TypesForTesting.Name: Predicate isEmpty() did not fail."))
  }

  def testValidateAsAIntToAIntRefinedPositiveSyntax: Property = for {
    positiveInt <- Gen.int(Range.linear(1, Int.MaxValue)).log("positiveInt")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = validateAs[Id](positiveInt)
    actual ==== Right(Id(PositiveInt.unsafeFrom(positiveInt)))
  }

  def testIntAsValidateToAIntRefinedPositiveSyntax: Property = for {
    positiveInt <- Gen.int(Range.linear(1, Int.MaxValue)).log("positiveInt")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = positiveInt.as[Id].validate
    actual ==== Right(Id(PositiveInt.unsafeFrom(positiveInt)))
  }

  def testValidateAsAIntToErrorSyntax: Property = for {
    nonPositiveInt <- Gen.int(Range.linear(Int.MinValue, 0)).log("nonPositiveInt")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = validateAs[Id](nonPositiveInt)
    actual ==== Left(NonEmptyList.of(s"Failed to create TypesForTesting.Id: Predicate failed: ($nonPositiveInt > 0)."))
  }

  def testIntAsValidateToErrorSyntax: Property = for {
    nonPositiveInt <- Gen.int(Range.linear(Int.MinValue, 0)).log("nonPositiveInt")
  } yield {
    import TypesForTesting._
    import extras.refinement.syntax.refinement._
    val actual = nonPositiveInt.as[Id].validate
    actual ==== Left(NonEmptyList.of(s"Failed to create TypesForTesting.Id: Predicate failed: ($nonPositiveInt > 0)."))
  }

  object TypesForTesting {
    @newtype case class Name(value: NonEmptyString)

    type PositiveInt = Int Refined Positive
    object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
    @newtype case class Id(value: PositiveInt)

  }
}
