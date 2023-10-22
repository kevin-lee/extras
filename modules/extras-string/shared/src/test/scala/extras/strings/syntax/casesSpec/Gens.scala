package extras.strings.syntax.casesSpec

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

  def genCamelCaseList(range: Range[Int]): Gen[List[String]] =
    Gen.string(Gen.lower, Range.linear(1, 5)).list(range)

}
