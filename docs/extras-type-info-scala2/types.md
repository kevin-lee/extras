---
sidebar_position: 1
sidebar_label: 'Types'
id: 'types'
title: 'extras-type-info - Scala 2'
---

## Module

```scala
"io.kevinlee" %% "extras-type-info" % "@VERSION@"
```


## types

```scala
import extras.typeinfo.syntax.types._
```

### `nestedTypeName`

```scala mdoc:reset-object
import extras.typeinfo.syntax.types._

sealed trait Foo
object Foo {
  case object Bar extends Foo
  final case class Baz(n: Int) extends Foo
  
  def bar: Foo = Bar
  def baz(n: Int): Foo = Baz(n)
}

Foo.Bar.nestedTypeName
Foo.Baz(1).nestedTypeName

Foo.bar.nestedTypeName
Foo.baz(1).nestedTypeName

Foo.Bar.nestedRuntimeClassName
Foo.Baz(1).nestedRuntimeClassName

Foo.bar.nestedRuntimeClassName
Foo.baz(1).nestedRuntimeClassName
```

```scala mdoc:reset-object
import io.estatico.newtype.macros.newtype
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import extras.typeinfo.syntax.types._

object data {
  final case class Person(firstName: Person.FirstName, lastName: Person.LastName)

  object Person {
    
    @newtype case class FirstName(value: NonEmptyString)
    @newtype case class LastName(value: NonEmptyString)

  }
}

import data._

val firstName = Person.FirstName(NonEmptyString("Kevin"))
val lastName = Person.LastName(NonEmptyString("Lee"))
val person = Person(firstName, lastName)

firstName.nestedTypeName
lastName.nestedTypeName
person.nestedTypeName

firstName.nestedRuntimeClassName
lastName.nestedRuntimeClassName
person.nestedRuntimeClassName
```

## More about type-info

## syntax for `WeakTypeTag`

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
import extras.typeinfo.syntax.types._

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
import extras.typeinfo.syntax.types._

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
import extras.typeinfo.syntax.types._
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


## syntax for `ClassTag`

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
import extras.typeinfo.syntax.types._

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
import extras.typeinfo.syntax.types._

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
import extras.typeinfo.syntax.types._

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
