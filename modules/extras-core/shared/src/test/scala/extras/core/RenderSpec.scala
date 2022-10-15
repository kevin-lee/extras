package extras.core

import hedgehog._
import hedgehog.runner._

import java.util.UUID

/** @author Kevin Lee
  * @since 2022-10-15
  */
object RenderSpec extends Properties {
  override def tests: List[Test] = List(
    example("test Render[Unit].render", testUnitRender),
    property("test Render[Boolean].render", testBooleanRender),
    property("test Render[Byte].render", testByteRender),
    property("test Render[Short].render", testShortRender),
    property("test Render[Int].render", testIntRender),
    property("test Render[Long].render", testLongRender),
    property("test Render[Float].render", testFloatRender),
    property("test Render[Double].render", testDoubleRender),
    property("test Render[BigInt].render", testBigIntRender),
    property("test Render[BigDecimal].render", testBigDecimalRender),
    property("test Render[Char].render", testCharRender),
    property("test Render[String].render", testStringRender),
    property("test Render[Symbol].render", testSymbolRender),
    property("test Render[UUID].render", testUuidRender),
    property("test Render[Duration].render", testDurationRender)
  )

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testUnitRender: Result =
    Render[Unit].render(()) ==== ().toString

  def testBooleanRender: Property =
    for {
      b <- Gen.boolean.log("b")
    } yield {
      Render[Boolean].render(b) ==== b.toString
    }

  def testByteRender: Property =
    for {
      n <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("n")
    } yield {
      Render[Byte].render(n) ==== n.toString
    }

  def testShortRender: Property =
    for {
      n <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("n")
    } yield {
      Render[Short].render(n) ==== n.toString
    }

  def testIntRender: Property =
    for {
      n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
    } yield {
      Render[Int].render(n) ==== n.toString
    }

  def testLongRender: Property =
    for {
      n <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("n")
    } yield {
      Render[Long].render(n) ==== n.toString
    }

  def testFloatRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("n")
    } yield {
      Render[Float].render(n) ==== n.toString
    }

  def testDoubleRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("n")
    } yield {
      Render[Double].render(n) ==== n.toString
    }

  def testBigIntRender: Property =
    for {
      n <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).map(BigInt(_)).log("n")
    } yield {
      Render[BigInt].render(n) ==== n.toString
    }

  def testBigDecimalRender: Property =
    for {
      n <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).map(BigDecimal(_)).log("n")
    } yield {
      Render[BigDecimal].render(n) ==== n.toString
    }

  def testCharRender: Property =
    for {
      n <- Gen.char(Char.MinValue, Char.MaxValue).log("n")
    } yield {
      Render[Char].render(n) ==== n.toString
    }

  def testStringRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).log("s")
    } yield {
      Render[String].render(s) ==== s
    }

  def testSymbolRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).map(Symbol(_)).log("s")
    } yield {
      Render[Symbol].render(s) ==== s.toString
    }

  def testUuidRender: Property =
    for {
      uuid <- Gen.constant(UUID.randomUUID()).log("uuid")
    } yield {
      Render[UUID].render(uuid) ==== uuid.toString
    }

  import scala.concurrent.duration._

  val timeRange: Map[TimeUnit, Long] = Map(
    NANOSECONDS  -> Long.MaxValue,
    MICROSECONDS -> Long.MaxValue / 1000,
    MILLISECONDS -> Long.MaxValue / (1000 * 1000),
    SECONDS      -> Long.MaxValue / (1000 * 1000 * 1000),
    MINUTES      -> Long.MaxValue / (1000 * 1000 * 1000) / 60,
    HOURS        -> Long.MaxValue / (1000 * 1000 * 1000) / 60 / 60,
    DAYS         -> Long.MaxValue / (1000 * 1000 * 1000) / 60 / 60 / 24,
  )

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
      duration = Duration(length, timeUnit)
    } yield {
      Render[Duration].render(duration) ==== duration.toString
    }

}
