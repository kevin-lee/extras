package extras.cats.testing

import cats.syntax.all.*

import scala.util.Random

/** @author Kevin Lee
  * @since 2025-06-17
  */
object RandomGens {
  val AlphaChars: Seq[Char]    = ('a' to 'z') ++ ('A' to 'Z')
  val AlphaNumChars: Seq[Char] = AlphaChars ++ ('0' to '9')

  def genRandomIntWithMinMax(min: Int, max: Int): Int = {
    if (min > max) then {
      throw new IllegalArgumentException(s"min ($min) must be less than or equal to max ($max)")
    } else {
      max match {
        case Int.MaxValue =>
          Random.nextLong(max.toLong + 1L - min.toLong).toInt + min
        case _ =>
          Random.nextInt(max + 1 - min) + min
      }
    }
  }

  def genRandomInt(): Int = genRandomIntWithMinMax(0, Int.MaxValue)

  def genAlphaString(length: Int): String =
    (1 to length).map(_ => AlphaChars(Random.nextInt(AlphaNumChars.length))).mkString

  def genAlphaNumericString(length: Int): String =
    (1 to length).map(_ => AlphaNumChars(Random.nextInt(AlphaNumChars.length))).mkString

  def genBoolean(): Boolean = genRandomIntWithMinMax(0, 1) === 0
}
