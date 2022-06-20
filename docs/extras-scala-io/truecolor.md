---
sidebar_position: 3
id: 'truecolor'
title: 'True Color'
---

# True Color

## `Rgb`

```scala mdoc:reset-object
import extras.scala.io.truecolor.Rgb
```

## `Rgb.fromInt(Int)` - Valid
```scala mdoc
Rgb.fromInt(0x000000)
Rgb.fromInt(0x0000ff)
Rgb.fromInt(0x00ff00)
Rgb.fromInt(0xff0000)
Rgb.fromInt(0xffffff)
```

## `Rgb.fromInt(Int)` - Invalid
```scala mdoc
Rgb.fromInt(0x000000 - 1)
Rgb.fromInt(0xffffff + 1)
```

## `Rgb.unsafeFromInt(Int)` - Valid
```scala mdoc
Rgb.unsafeFromInt(0x000000)
Rgb.unsafeFromInt(0x0000ff)
Rgb.unsafeFromInt(0x00ff00)
Rgb.unsafeFromInt(0xff0000)
Rgb.unsafeFromInt(0xffffff)
```

## `Rgb.unsafeFromInt(Int)` - Invalid
```scala mdoc:crash
Rgb.unsafeFromInt(0x000000 - 1)
```

```scala mdoc:crash
Rgb.unsafeFromInt(0xffffff + 1)
```

```scala mdoc
Rgb.fromRgbInts(0x00, 0x00, 0x00)
Rgb.fromRgbInts(0x00, 0x00, 0xff)
Rgb.fromRgbInts(0x00, 0xff, 0x00)
Rgb.fromRgbInts(0xff, 0x00, 0x00)
Rgb.fromRgbInts(0xff, 0xff, 0xff)
```

```scala mdoc
Rgb.unsafeFromRgbInts(0x00, 0x00, 0x00)
Rgb.unsafeFromRgbInts(0x00, 0x00, 0xff)
Rgb.unsafeFromRgbInts(0x00, 0xff, 0x00)
Rgb.unsafeFromRgbInts(0xff, 0x00, 0x00)
Rgb.unsafeFromRgbInts(0xff, 0xff, 0xff)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00, 0x00, 0x00 - 1)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00, 0x00, 0xff + 1)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00, 0x00 - 1, 0x00)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00, 0xff + 1, 0x00)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00 - 1, 0x00, 0x00)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0xff + 1, 0x00, 0x00)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00 - 1, 0x00 - 1, 0x00 - 1)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00, 0x00 - 1, 0xff + 1)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0x00 - 1, 0xff + 1, 0x00)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0xff + 1, 0x00, 0x00 - 1)
```

```scala mdoc:crash
Rgb.unsafeFromRgbInts(0xff + 1, 0xff + 1, 0xff + 1)
```


## `Rgb.fromHexString(String)` - Valid
```scala mdoc
Rgb.fromHexString("000000")
Rgb.fromHexString("#000000")
Rgb.fromHexString("0000ff")
Rgb.fromHexString("#0000ff")
Rgb.fromHexString("00ff00")
Rgb.fromHexString("#00ff00")
Rgb.fromHexString("ff0000")
Rgb.fromHexString("#ff0000")
Rgb.fromHexString("ffffff")
Rgb.fromHexString("#ffffff")
```

## `Rgb.fromHexString(String)` - Invalid
```scala mdoc
Rgb.fromHexString("00000000")
Rgb.fromHexString("#00000000")
Rgb.fromHexString("ffffffff")
Rgb.fromHexString("#ffffffff")
```


## `Rgb.unsafeFromHexString(String)` - Valid
```scala mdoc
Rgb.unsafeFromHexString("000000")
Rgb.unsafeFromHexString("#000000")
Rgb.unsafeFromHexString("0000ff")
Rgb.unsafeFromHexString("#0000ff")
Rgb.unsafeFromHexString("00ff00")
Rgb.unsafeFromHexString("#00ff00")
Rgb.unsafeFromHexString("ff0000")
Rgb.unsafeFromHexString("#ff0000")
Rgb.unsafeFromHexString("ffffff")
Rgb.unsafeFromHexString("#ffffff")
```

## `Rgb.unsafeFromHexString(String)` - Invalid
```scala mdoc:crash
Rgb.unsafeFromHexString("00000000")
```
```scala mdoc:crash
Rgb.unsafeFromHexString("#00000000")
```
```scala mdoc:crash
Rgb.unsafeFromHexString("ffffffff")
```
```scala mdoc:crash
Rgb.unsafeFromHexString("#ffffffff")
```


## `Rgb.toAsciiEsc`
```scala mdoc
Rgb.unsafeFromInt(0x000000).toAsciiEsc
Rgb.unsafeFromInt(0x0000ff).toAsciiEsc
Rgb.unsafeFromInt(0x00ff00).toAsciiEsc
Rgb.unsafeFromInt(0xff0000).toAsciiEsc
Rgb.unsafeFromInt(0xffffff).toAsciiEsc
```


## syntax

```scala mdoc:reset-object
import extras.scala.io.syntax.truecolor.rgb._
```
```scala mdoc
"Hello".rgb(0x10e0ff)
"Hello".rgbed(0x10e0ff)
```
```scala mdoc
import extras.scala.io.truecolor.Rgb
Rgb.fromInt(0x11a02f)
  .map(rgb => "Hello".rgb(rgb))
Rgb.fromInt(0x11a02f)
  .map(rgb => "Hello".rgbed(rgb))
```
