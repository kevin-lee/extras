---
sidebar_position: 2
id: 'strings'
title: 'strings syntax'
---

## Import `strings` syntax

```scala mdoc
import extras.strings.syntax.strings._
```
Or
```scala
import extras.strings.syntax.all._
```

## `commaWith(String)`

An extension method for a `Seq[String]`, providing a way to join the `String` elements with a comma and a given
conjunction.

It joins `String` values with commas and uses the given conjunction before the last element.

```scala mdoc
List.empty[String].commaWith("BLAH")

List("").commaWith("BLAH")

List("aaa").commaWith("BLAH")

List("aaa", "bbb").commaWith("BLAH")

List("aaa", "bbb", "ccc").commaWith("BLAH")

List("aaa", "bbb", "ccc", "ddd").commaWith("BLAH")

```

## `serialCommaWith(String)`

An extension method for a `Seq[String]`, providing a way to join the `String` elements with a serial comma and a given
conjunction.

It joins String values with commas and uses the given conjunction before the last element.

This method employs the serial comma (also known as the Oxford comma),
which means it always inserts a comma before the conjunction unless there are only two elements.

```scala mdoc
List.empty[String].serialCommaWith("BLAH")

List("").serialCommaWith("BLAH")

List("aaa").serialCommaWith("BLAH")

List("aaa", "bbb").serialCommaWith("BLAH")

List("aaa", "bbb", "ccc").serialCommaWith("BLAH")

List("aaa", "bbb", "ccc", "ddd").serialCommaWith("BLAH")
```

## `commaAnd`

Format `Seq[String]` into a human-readable list using comma and the conjunction `and`.

It separates elements by commas and uses the term `and` before the last element.

```scala mdoc
List.empty[String].commaAnd

List("").commaAnd

List("aaa").commaAnd

List("aaa", "bbb").commaAnd

List("aaa", "bbb", "ccc").commaAnd

List("aaa", "bbb", "ccc", "ddd").commaAnd
```

## `serialCommaAnd`

Format `Seq[String]` into a human-readable list using comma and the conjunction `and`.

It separates elements by commas and uses the term `and` before the last element following the "Oxford comma" style.  e.g.) `"aaa, bbb, and ccc"`

```scala mdoc
List.empty[String].serialCommaAnd

List("").serialCommaAnd

List("aaa").serialCommaAnd

List("aaa", "bbb").serialCommaAnd

List("aaa", "bbb", "ccc").serialCommaAnd

List("aaa", "bbb", "ccc", "ddd").serialCommaAnd

```

## `commaOr`

Format `Seq[String]` into a human-readable list using comma and the conjunction `or`.

It separates elements by commas and uses the term `or` before the last element.

```scala mdoc
List.empty[String].commaOr

List("").commaOr

List("aaa").commaOr

List("aaa", "bbb").commaOr

List("aaa", "bbb", "ccc").commaOr

List("aaa", "bbb", "ccc", "ddd").commaOr
```

## `serialCommaOr`

Format `Seq[String]` into a human-readable list using comma and the conjunction `or`.

It separates elements by commas and uses the term `or` before the last element following the "Oxford comma" style.  e.g.) `"aaa, bbb, or ccc"`

```scala mdoc
List.empty[String].serialCommaOr

List("").serialCommaOr

List("aaa").serialCommaOr

List("aaa", "bbb").serialCommaOr

List("aaa", "bbb", "ccc").serialCommaOr

List("aaa", "bbb", "ccc", "ddd").serialCommaOr
```

