---
sidebar_position: 1
id: 'rgb'
title: 'Rgb'
---

# Rgb

## Module
```scala
"io.kevinlee" %% "extras-scala-io" % "@VERSION@"
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-scala-io" % "@VERSION@"
```


## `Rgb`

```scala mdoc:reset-object
import extras.scala.io.truecolor.Rgb
```

## `Rgb.fromInt(Int)`

### `Rgb.fromInt(Int)` - Valid
```scala mdoc
Rgb.fromInt(0x000000)
Rgb.fromInt(0x0000ff)
Rgb.fromInt(0x00ff00)
Rgb.fromInt(0xff0000)
Rgb.fromInt(0xffffff)
```

### `Rgb.fromInt(Int)` - Invalid
```scala mdoc
Rgb.fromInt(0x000000 - 1)
Rgb.fromInt(0xffffff + 1)
```

## `Rgb.unsafeFromInt(Int)`

### `Rgb.unsafeFromInt(Int)` - Valid
```scala mdoc
Rgb.unsafeFromInt(0x000000)
Rgb.unsafeFromInt(0x0000ff)
Rgb.unsafeFromInt(0x00ff00)
Rgb.unsafeFromInt(0xff0000)
Rgb.unsafeFromInt(0xffffff)
```

### `Rgb.unsafeFromInt(Int)` - Invalid
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


## `Rgb.fromHexString(String)`

### `Rgb.fromHexString(String)` - Valid
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

### `Rgb.fromHexString(String)` - Invalid
```scala mdoc
Rgb.fromHexString("00000000")
Rgb.fromHexString("#00000000")
Rgb.fromHexString("ffffffff")
Rgb.fromHexString("#ffffffff")
```


## `Rgb.unsafeFromHexString(String)`

### `Rgb.unsafeFromHexString(String)` - Valid
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

### `Rgb.unsafeFromHexString(String)` - Invalid
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

## To Colored String Form

### `Rgb.toAsciiEsc`
```scala mdoc
Rgb.unsafeFromInt(0x000000).toAsciiEsc
Rgb.unsafeFromInt(0x0000ff).toAsciiEsc
Rgb.unsafeFromInt(0x00ff00).toAsciiEsc
Rgb.unsafeFromInt(0xff0000).toAsciiEsc
Rgb.unsafeFromInt(0xffffff).toAsciiEsc
```

### `Rgb.toHex`
```scala mdoc
Rgb.unsafeFromInt(0x000000).toHex
Rgb.unsafeFromInt(0x0000ff).toHex
Rgb.unsafeFromInt(0x00ff00).toHex
Rgb.unsafeFromInt(0xff0000).toHex
Rgb.unsafeFromInt(0xffffff).toHex
```

### `Rgb.toHexHtml`
```scala mdoc
Rgb.unsafeFromInt(0x000000).toHexHtml
Rgb.unsafeFromInt(0x0000ff).toHexHtml
Rgb.unsafeFromInt(0x00ff00).toHexHtml
Rgb.unsafeFromInt(0xff0000).toHexHtml
Rgb.unsafeFromInt(0xffffff).toHexHtml
```

### `Rgb.toRgbInts`
```scala mdoc
Rgb.unsafeFromInt(0x000000).toRgbInts
Rgb.unsafeFromInt(0x0000ff).toRgbInts
Rgb.unsafeFromInt(0x00ff00).toRgbInts
Rgb.unsafeFromInt(0xff0000).toRgbInts
Rgb.unsafeFromInt(0xffffff).toRgbInts
```

### `Rgb.color(String)`
```scala mdoc
println(s"${Rgb.unsafeFromInt(0x000000).color("Hello")} World")
println(s"${Rgb.unsafeFromInt(0x0000ff).color("Hello")} World")
println(s"${Rgb.unsafeFromInt(0x00ff00).color("Hello")} World")
println(s"${Rgb.unsafeFromInt(0xff0000).color("Hello")} World")
println(s"${Rgb.unsafeFromInt(0xffffff).color("Hello")} World")
```

### `Rgb.colored(String)`
```scala mdoc
println(s"${Rgb.unsafeFromInt(0x000000).colored("Hello")} World")
println(s"${Rgb.unsafeFromInt(0x0000ff).colored("Hello")} World")
println(s"${Rgb.unsafeFromInt(0x00ff00).colored("Hello")} World")
println(s"${Rgb.unsafeFromInt(0xff0000).colored("Hello")} World")
println(s"${Rgb.unsafeFromInt(0xffffff).colored("Hello")} World")
```


## syntax
To use `Rgb` syntax, import `extras.scala.io.syntax.truecolor.rgb._`.
```scala mdoc:reset-object
import extras.scala.io.syntax.truecolor.rgb._
```

### `String.rgb(Int)` and `String.rgbed(Int)`
```scala mdoc
"Hello".rgb(0x10e0ff)
"Hello".rgbed(0x10e0ff)
```

### Invalid RGB Value Handling
In `.rgb(Int)` and `.rgbed(Int)` syntax,
 * If the given RGB `Int` value is less than `0` (`0x000000`), it uses `0` (`0x000000`).
 * If the given RGB `Int` value is greater than `16777215` (`0xffffff`), it uses `16777215` (`0xffffff`).
```scala mdoc
"Hello".rgb(0x000000 - 1)
"Hello".rgb(0xffffff + 1)

"Hello".rgbed(0xffffff + 1)
"Hello".rgbed(0xffffff + 1)
```

### `String.rgb(Rgb)` and `String.rgbed(Rgb)`
```scala mdoc
import extras.scala.io.truecolor.Rgb
Rgb.fromInt(0x11a02f)
  .map(rgb => "Hello".rgb(rgb))
Rgb.fromInt(0x11a02f)
  .map(rgb => "Hello".rgbed(rgb))

for {
  a <- Rgb.fromInt(0x11a02f)
  b <- Rgb.fromInt(0x00eab0)
} yield {
  println("Hello".rgb(a))
  println("Hello".rgb(b))
  println("Hello".rgbed(a))
  println("Hello".rgbed(b))
}
```
