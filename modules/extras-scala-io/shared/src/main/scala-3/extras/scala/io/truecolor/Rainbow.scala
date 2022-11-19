package extras.scala.io.truecolor

/** @author Kevin Lee
  * @since 2022-06-13
  */
object Rainbow {
  val Red: Rgb    = Rgb.unsafeFromRgbInts(255, 0, 0)
  val Orange: Rgb = Rgb.unsafeFromRgbInts(255, 97, 0)
  val Yellow: Rgb = Rgb.unsafeFromRgbInts(247, 255, 0)
  val Green: Rgb  = Rgb.unsafeFromRgbInts(0, 255, 0)
  val Blue: Rgb   = Rgb.unsafeFromRgbInts(100, 149, 255)
  val Indigo: Rgb = Rgb.unsafeFromRgbInts(143, 0, 200)
  val Violet: Rgb = Rgb.unsafeFromRgbInts(200, 0, 255)

  val RainbowColor: List[Rgb] = List(Red, Orange, Yellow, Green, Blue, Indigo, Violet)

  type Index = Index.Index
  object Index {
    opaque type Index = 0 | 1 | 2 | 3 | 4 | 5 | 6
    def apply(index: 0 | 1 | 2 | 3 | 4 | 5 | 6): Index = index

    given indexCanEqual: CanEqual[Index, Index] = CanEqual.derived

    extension (index: Index) {
      def value: 0 | 1 | 2 | 3 | 4 | 5 | 6 = index
    }

    def zero: Index  = 0
    def one: Index   = 1
    def two: Index   = 2
    def three: Index = 3
    def four: Index  = 4
    def five: Index  = 5
    def six: Index   = 6

    def unsafeFromInt(index: Int): Index = index match {
      case 0 => Index.zero
      case 1 => Index.one
      case 2 => Index.two
      case 3 => Index.three
      case 4 => Index.four
      case 5 => Index.five
      case 6 => Index.six
      case _ => sys.error(s"ColorIndex must be Int between 0 and 6 (min: 0 / max: 6). Input: ${index.toString}")
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  private def mkRainbow(s: String, start: String, f: (String, Index) => String, end: String): String = {
    val length    = s.length
    val remainder = length % 7
    val size      = if (length < 7) 1 else length / 7
    if (remainder == 0 || length < 7) {
      start + s
        .sliding(size, size)
        .zipWithIndex
        .map { case (each, index) => f(each, Index.unsafeFromInt(index)) }
        .mkString + end
    } else {
      val half       = remainder / 2
      val halfRemain = remainder % 2
      val leftTake   = half + halfRemain
      val rightTake  = half

      val sizes = List.fill(7)(size).zipWithIndex.map {
        case (n, index) =>
          /* 1 = 0
           * 2 = 0, 6
           * 3 = 0, 1, 6
           * 4 = 0, 1, 5, 6
           * 5 = 0, 1, 2, 5, 6
           * 6 = 0, 1, 2, 4, 5, 6
           */
          if (index < leftTake || index >= (7 - rightTake))
            n + 1
          else
            n
      }
      sizes
        .zipWithIndex
        .foldLeft((Vector.empty[String], s)) {
          case ((acc, prev), (size, index)) =>
            (acc :+ f(prev.take(size), Index.unsafeFromInt(index)), prev.drop(size))
        }
        ._1
        .mkString + end
    }

  }

  def rainbow(s: String): String =
    if s.isEmpty then ""
    else
      mkRainbow(
        s,
        "",
        (each, index) => RainbowColor(index.value).toAsciiEsc + each,
        extras.scala.io.Color.Reset.toAnsi,
      )

  def rainbows(ss: Seq[String]): Seq[String] = {
    val maxLength = ss.map(_.length).foldLeft(0)(Ordering[Int].max)
    ss.map { s =>
      val length = s.length
      rainbow(s + (" " * (maxLength - length)))
    }
  }

  def rainbowHtml(s: String): String =
    if s.isEmpty then ""
    else
      mkRainbow(
        s,
        "",
        (each, index) => s"""<span style="color: ${RainbowColor(index.value).toHexHtml};">$each</span>""",
        "",
      )

}
