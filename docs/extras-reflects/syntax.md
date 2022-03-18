---
sidebar_position: 2
id: 'syntax'
title: 'Reflects Syntax'
---

## `reflects` syntax for `WeakTypeTag`

```scala mdoc:reset-object
import scala.reflect.runtime.universe._
import extras.reflects.syntax.reflects._

import java.time._

sealed trait Status
object Status {
  final case class InProgress(startedAt: Instant) extends Status
  case object Done extends Status
  
  def inProgress(startedAt: Instant): Status = InProgress(startedAt)
  def done: Status = Done
}
```
```scala mdoc
def infoWithWeakTypeTag[A: WeakTypeTag](a: A): Unit =
  println(
    s"""value: $a
       | type: ${weakTypeTag[A].nestedTypeName}
       |""".stripMargin)

infoWithWeakTypeTag(Status.inProgress(Instant.now()))
infoWithWeakTypeTag(Status.InProgress(Instant.now()))

infoWithWeakTypeTag(Status.done)
infoWithWeakTypeTag(Status.Done)
```


## `reflects` syntax for `ClassTag`

```scala mdoc:reset-object
import extras.reflects.syntax.reflects._

import java.time._

sealed trait Status
object Status {
  final case class InProgress(startedAt: Instant) extends Status
  case object Done extends Status
  
  def inProgress(startedAt: Instant): Status = InProgress(startedAt)
  def done: Status = Done
}
```
```scala mdoc
def infoWithClassTag[A](a: A): Unit =
  println(
    s"""value: $a
       | type: ${a.nestedRuntimeClassName}
       |""".stripMargin)

infoWithClassTag(Status.inProgress(Instant.now()))
infoWithClassTag(Status.InProgress(Instant.now()))

infoWithClassTag(Status.done)
infoWithClassTag(Status.Done)
```

## `reflects` syntax for `Class`

```scala mdoc:reset-object
import extras.reflects.syntax.reflects._

import java.time._

sealed trait Status
object Status {
  final case class InProgress(startedAt: Instant) extends Status
  case object Done extends Status
  
  def inProgress(startedAt: Instant): Status = InProgress(startedAt)
  def done: Status = Done
}
```
```scala mdoc
def infoWithClass[A](a: A): Unit =
  println(
    s"""value: $a
       | type: ${a.nestedClassName}
       |""".stripMargin)

infoWithClass(Status.inProgress(Instant.now()))
infoWithClass(Status.InProgress(Instant.now()))

infoWithClass(Status.done)
infoWithClass(Status.Done)
```