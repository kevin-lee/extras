---
sidebar_position: 2
id: 'syntax'
title: 'Reflects Syntax'
---

## `reflects` syntax for `WeakTypeTag`

### `value.nestedTypeName`

```scala mdoc:reset-object
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
import scala.reflect.runtime.universe._
import extras.reflects.syntax.tags._

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

### `WeakTypeTag[A].nestedTypeName`

```scala mdoc:reset-object
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
import scala.reflect.runtime.universe._
import extras.reflects.syntax.tags._

def infoWithWeakTypeTag[A](implicit weakTypeTag: WeakTypeTag[A]): Unit =
  println(
    s"""type: ${weakTypeTag.nestedTypeName}
       |""".stripMargin)

infoWithWeakTypeTag[Status.InProgress]
infoWithWeakTypeTag[Status.Done.type]

println(weakTypeTag[Status.InProgress].nestedTypeName)
println(weakTypeTag[Status.Done.type].nestedTypeName)
```

### Works for `@newtype`

:::info It works for `newtype` as well.
If you use [newtype](https://github.com/estatico/scala-newtype) and want to get the `newtype` name, `WeakTypeTag` syntax is what you should use since you can get the name of `newtype` with it.
:::

An example showing that it works with `@newtype`:
```scala mdoc:reset-object
import io.estatico.newtype.macros.newtype

object Types {
  @newtype case class Id(value: Long)
  @newtype case class Username(value: String)
}
```
```scala mdoc
import scala.reflect.runtime.universe._
import extras.reflects.syntax.tags._
```
```scala mdoc
def infoWithClassTag[A](a: A)(implicit weakTypeTag: WeakTypeTag[A]): Unit =
  println(
    s"""value: $a
       | type: ${weakTypeTag.nestedTypeName}
       |""".stripMargin)

import Types._

infoWithClassTag(Id(1L))
infoWithClassTag(Username("someuser"))

println(weakTypeTag[Id].nestedTypeName)
println(weakTypeTag[Username].nestedTypeName)
```


## `reflects` syntax for `ClassTag`

### `value.nestedRuntimeClassName`

```scala mdoc:reset-object
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
import extras.reflects.syntax.tags._

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
```scala mdoc
println(Status.inProgress(Instant.now()).nestedRuntimeClassName)
println(Status.InProgress(Instant.now()).nestedRuntimeClassName)

println(Status.done.nestedRuntimeClassName)
println(Status.Done.nestedRuntimeClassName)
```

### `ClassTag[A].nestedRuntimeClassName`

```scala mdoc:reset-object
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
import scala.reflect.{classTag, ClassTag}
import extras.reflects.syntax.tags._

def infoWithClassTag[A](implicit classTag: ClassTag[A]): Unit =
  println(
    s"""type: ${classTag.nestedRuntimeClassName}
       |""".stripMargin)

infoWithClassTag[Status.InProgress]
infoWithClassTag[Status.Done.type]
```
```scala mdoc
println(classTag[Status.InProgress].nestedRuntimeClassName)
println(classTag[Status.Done.type].nestedRuntimeClassName)
```

### Do not use for `@newtype`

:::caution Do not use it for `newtype`.
If you use [newtype](https://github.com/estatico/scala-newtype) and want to get the `newtype` name, `ClassTag` syntax is not the one you should use since you can get only the actual type not `newtype`. For `@newtype`, please use '[`reflects` syntax for `WeakTypeTag`](#works-for-newtype)'.
:::

An example showing that it does not work with `@newtype`:
```scala mdoc:reset-object
import io.estatico.newtype.macros.newtype

object Types {
  @newtype case class Id(value: Long)
  @newtype case class Username(value: String)
}
```
```scala mdoc
import extras.reflects.syntax.tags._

def infoWithClassTag[A](a: A): Unit =
  println(
    s"""value: $a
       | type: ${a.nestedRuntimeClassName}
       |""".stripMargin)

import Types._

infoWithClassTag(Id(1L))
infoWithClassTag(Username("someuser"))

println(Id(1L).nestedRuntimeClassName)
println(Username("someuser").nestedRuntimeClassName)
```

## `reflects` syntax for `Class`

### `value.nestedClassName`
```scala mdoc:reset-object
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
import extras.reflects.syntax.classes._

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
```scala mdoc
println(Status.inProgress(Instant.now()).nestedClassName)
println(Status.InProgress(Instant.now()).nestedClassName)

println(Status.done.nestedClassName)
println(Status.Done.nestedClassName)
```

### `Class[A].nestedClassName`
```scala mdoc:reset-object
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
import extras.reflects.syntax.classes._

def infoWithClass[A](aClass: Class[A]): Unit =
  println(
    s"""type: ${aClass.nestedClassName}
       |""".stripMargin)

infoWithClass(Status.InProgress.getClass)
infoWithClass(Status.Done.getClass)
```

### Do not use for `@newtype`

:::caution Do not use it for `newtype`.
If you use [newtype](https://github.com/estatico/scala-newtype) and want to get the `newtype` name, `Class` syntax is not the one you should use since you can get only the actual type not `newtype`. For `@newtype`, please use '[`reflects` syntax for `WeakTypeTag`](#works-for-newtype)'.
:::

An example showing that it does not work with `@newtype`:
```scala mdoc:reset-object
import io.estatico.newtype.macros.newtype

object Types {
  @newtype case class Id(value: Long)
  @newtype case class Username(value: String)
}
```
```scala mdoc
import extras.reflects.syntax.classes._

def infoWithClassTag[A](a: A): Unit =
  println(
    s"""value: $a
       | type: ${a.nestedClassName}
       |""".stripMargin)

import Types._

infoWithClassTag(Id(1L))
infoWithClassTag(Username("someuser"))

println(Id(1L).nestedClassName)
println(Username("someuser").nestedClassName)
```
