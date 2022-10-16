package extras.core.syntax

import extras.core.syntax.render._
import hedgehog._
import hedgehog.runner._

import java.util.UUID
import scala.concurrent.duration._

/** @author Kevin Lee
  * @since 2022-10-15
  */
object RenderSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    example("test Unit.render", testUnitRender),
    property("test Boolean.render", testBooleanRender),
    property("test Byte.render", testByteRender),
    property("test Short.render", testShortRender),
    property("test Int.render", testIntRender),
    property("test Long.render", testLongRender),
    property("test Float.render", testFloatRender),
    property("test Double.render", testDoubleRender),
    property("test BigInt.render", testBigIntRender),
    property("test BigDecimal.render", testBigDecimalRender),
    property("test Char.render", testCharRender),
    property("test String.render", testStringRender),
    property("test Symbol.render", testSymbolRender),
    property("test UUID.render", testUuidRender),
    property("test Duration.render", testDurationRender),
    property("test FiniteDuration.render", testFiniteDurationRender),
    property("test List.renderString", testListRenderString),
    property("test List.renderString(delimiter)", testListRenderStringWithDelimiter),
    property("test List.renderString(start, delimiter, end)", testListRenderStringWithStartDelimiterEnd),
    property("test Vector.renderString", testVectorRenderString),
    property("test Vector.renderString(delimiter)", testVectorRenderStringWithDelimiter),
    property("test Vector.renderString(start, delimiter, end)", testVectorRenderStringWithStartDelimiterEnd),
  )

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testUnitRender: Result =
    ().render ==== ().toString

  def testBooleanRender: Property =
    for {
      b <- Gen.boolean.log("b")
    } yield {
      b.render ==== b.toString
    }

  def testByteRender: Property =
    for {
      n <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testShortRender: Property =
    for {
      n <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testIntRender: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testLongRender: Property =
    for {
      n <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testFloatRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testDoubleRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testBigIntRender: Property =
    for {
      n <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).map(BigInt(_)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testBigDecimalRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).map(BigDecimal(_)).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testCharRender: Property =
    for {
      n <- Gen.char(Char.MinValue, Char.MaxValue).log("n")
    } yield {
      n.render ==== n.toString
    }

  def testStringRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).log("s")
    } yield {
      s.render ==== s
    }

  def testSymbolRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).map(Symbol(_)).log("s")
    } yield {
      s.render ==== s.toString
    }

  def testUuidRender: Property =
    for {
      uuid <- Gen.constant(UUID.randomUUID()).log("uuid")
    } yield {
      uuid.render ==== uuid.toString
    }

  val timeRange: Map[TimeUnit, Long] = Map(
    NANOSECONDS  -> Long.MaxValue,
    MICROSECONDS -> Long.MaxValue / 1000,
    MILLISECONDS -> Long.MaxValue / (1000 * 1000),
    SECONDS      -> Long.MaxValue / (1000 * 1000 * 1000),
    MINUTES      -> Long.MaxValue / (1000 * 1000 * 1000) / 60,
    HOURS        -> Long.MaxValue / (1000 * 1000 * 1000) / 60 / 60,
    DAYS         -> Long.MaxValue / (1000 * 1000 * 1000) / 60 / 60 / 24,
  )

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testDurationRender: Property =
    for {
      timeUnit <- Gen
                    .element1(
                      NANOSECONDS,
                      MICROSECONDS,
                      MILLISECONDS,
                      SECONDS,
                      MINUTES,
                      HOURS,
                      DAYS,
                    )
                    .log("timeUnit")
      length   <- Gen.long(Range.linear(0, timeRange(timeUnit))).log("length")
      duration = Duration(length.toDouble, timeUnit)
    } yield {
      duration.render ==== duration.toString
    }

  def testFiniteDurationRender: Property =
    for {
      timeUnit <- Gen
                    .element1(
                      NANOSECONDS,
                      MICROSECONDS,
                      MILLISECONDS,
                      SECONDS,
                      MINUTES,
                      HOURS,
                      DAYS,
                    )
                    .log("timeUnit")
      length   <- Gen.long(Range.linear(0, timeRange(timeUnit))).log("length")
      duration = Duration(length, timeUnit)
    } yield {
      duration.render ==== duration.toString
    }

  def testListRenderString: Property =
    for {
      ns <- Gen
              .int(Range.linear(Int.MinValue, Int.MaxValue))
              .list(Range.linear(0, 10))
              .log("ns")
    } yield {
      ns.renderString ==== ns.mkString
    }

  def testListRenderStringWithDelimiter: Property =
    for {
      ns        <- Gen
                     .int(Range.linear(Int.MinValue, Int.MaxValue))
                     .list(Range.linear(0, 10))
                     .log("ns")
      delimiter <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("delimiter")
    } yield {
      ns.renderString(delimiter) ==== ns.mkString(delimiter)
    }

  def testListRenderStringWithStartDelimiterEnd: Property =
    for {
      ns        <- Gen
                     .int(Range.linear(Int.MinValue, Int.MaxValue))
                     .list(Range.linear(0, 10))
                     .log("ns")
      start     <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("start")
      delimiter <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("delimiter")
      end       <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("end")
    } yield {
      ns.renderString(start, delimiter, end) ==== ns.mkString(start, delimiter, end)
    }

  def testVectorRenderString: Property =
    for {
      ns <- Gen
              .int(Range.linear(Int.MinValue, Int.MaxValue))
              .list(Range.linear(0, 10))
              .map(_.toVector)
              .log("ns")
    } yield {
      ns.renderString ==== ns.mkString
    }

  def testVectorRenderStringWithDelimiter: Property =
    for {
      ns        <- Gen
                     .int(Range.linear(Int.MinValue, Int.MaxValue))
                     .list(Range.linear(0, 10))
                     .map(_.toVector)
                     .log("ns")
      delimiter <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("delimiter")
    } yield {
      ns.renderString(delimiter) ==== ns.mkString(delimiter)
    }

  def testVectorRenderStringWithStartDelimiterEnd: Property =
    for {
      ns        <- Gen
                     .int(Range.linear(Int.MinValue, Int.MaxValue))
                     .list(Range.linear(0, 10))
                     .map(_.toVector)
                     .log("ns")
      start     <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("start")
      delimiter <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("delimiter")
      end       <- Gen.string(Gen.unicodeAll, Range.linear(0, 5)).log("end")
    } yield {
      ns.renderString(start, delimiter, end) ==== ns.mkString(start, delimiter, end)
    }

}
