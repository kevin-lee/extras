---
sidebar_position: 2
id: 'color'
title: 'Color'
---

# Color

## `extras.scala.io.syntax`

`extras-scala-io` provides `syntax` to use `scala.io.AnsiColor` easily (The missing ones will be added later).

```scala mdoc:reset-object
import extras.scala.io.syntax.color._
```

```scala mdoc:reset-object
import extras.scala.io.syntax.color._

"Hello".blue

"Hello".red

"Hello".green

"Hello".bold

"Hello".underlined

println("Hello".blue)

println("Hello".red)

println("Hello".green)

println("Hello".bold)

println("Hello".underlined)
```

![AnsiColor syntax support Example 1](/img/docs/extras-scala-io/extras-scala-io-color-examples.png)
![AnsiColor syntax support Example 2](/img/docs/extras-scala-io/extras-scala-io-color-examples-2.png)

You can also chain them like this.
```scala mdoc:reset-object
import extras.scala.io.syntax.color._

println("Hello".blue)

println("Hello".blue.bold)

println("Hello".blue.bold.underlined)

println("Hello".underlined.bold.blue)
```
![AnsiColor syntax support Example 3](/img/docs/extras-scala-io/extras-scala-io-color-examples-3.png)
