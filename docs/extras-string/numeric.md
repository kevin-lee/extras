---
sidebar_position: 1
id: 'numeric'
title: 'numeric syntax'
---

## Import `numeric` syntax 

```scala mdoc
import extras.strings.syntax.numeric._
```

## `numeric` syntax for `Int`

```scala mdoc
1.toOrdinal
2.toOrdinal
3.toOrdinal
4.toOrdinal
```

```scala mdoc
(1 to 100)
  .map(_.toOrdinal)
  .grouped(10)
  .map(_.mkString(", "))
  .foreach(println(_))
```


## `numeric` syntax for `Long`

```scala mdoc
1L.toOrdinal
2L.toOrdinal
3L.toOrdinal
4L.toOrdinal
```

```scala mdoc
(1L to 100L)
  .map(_.toOrdinal)
  .grouped(10)
  .map(_.mkString(", "))
  .foreach(println(_))
```

