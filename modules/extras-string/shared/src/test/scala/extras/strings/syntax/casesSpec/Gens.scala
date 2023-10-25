package extras.strings.syntax.casesSpec

import cats.syntax.all._
import hedgehog._

/** @author Kevin Lee
  * @since 2023-10-16
  */
object Gens {

  def genPascalCase(min: Int, max: Int): Gen[String] =
    for {
      head <- Gen.upper
      tail <- Gen.string(Gen.lower, Range.linear(min - 1, max - 1))
    } yield head +: tail

  def genFirstLowerCasesThenAllPascalCases(maxLength: Int)(min: Int, max: Int): Gen[List[String]] =
    if (maxLength < 2) {
      sys.error(show"maxLength should not be less than 2. maxLength=$maxLength")
    } else if (min > max) {
      sys.error(show"min should not be greater than max. min=$min, max=$max")
    } else if (min <= 0) {
      sys.error(show"min should be greater than 0. min=$min")
    } else {
      for {
        first <- Gen.string(Gen.lower, Range.linear(1, 5))
        rest  <- if ((max - 1) === 0)
                   Gen.constant(List.empty)
                 else
                   genPascalCase(2, 5).list(Range.linear(min - 1, max - 1))
      } yield first :: rest

    }

}
