---
sidebar_position: 1
sidebar_label: 'Scala 2'
id: 'scala2'
title: 'extras-type-info - Scala 2'
---

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
