---
sidebar_position: 2
id: 'strings'
title: 'strings syntax'
---

## Import `strings` syntax

```scala mdoc
import extras.strings.syntax.strings._
```

## `commaWith(String)`

An extension method for a `Seq[String]`, providing a way to join the `String` elements with a comma and a given conjunction.

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

An extension method for a `Seq[String]`, providing a way to join the `String` elements with a serial comma and a given conjunction.

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

