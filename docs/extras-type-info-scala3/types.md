---
sidebar_position: 1
sidebar_label: 'Types'
id: 'scala3'
title: 'extras-type-info - Scala 3'
---

## types

```scala
import extras.typeinfo.syntax.types.*
```

### `nestedTypeName`

```scala mdoc:reset-object
import extras.typeinfo.syntax.types.*

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
```

```scala mdoc:reset-object
import extras.typeinfo.syntax.types.*

enum Foo {
  case Bar
  case Baz(n: Int)
}

Foo.Bar.nestedTypeName
Foo.Baz(1).nestedTypeName
```

```scala mdoc:reset-object
import extras.typeinfo.syntax.types.*

object data {
  final case class Person(firstName: Person.FirstName, lastName: Person.LastName)

  object Person {
    type FirstName = FirstName.FirstName

    object FirstName {
      opaque
      type FirstName = String

      def apply(firstName: String): FirstName = firstName

      given firstNameCanEqual: CanEqual[FirstName, FirstName]
      = CanEqual.derived

      extension(firstName: FirstName) {
        def value: String = firstName
      }
    }

    type LastName = LastName.LastName

    object LastName {
      opaque
      type LastName = String

      def apply(lastName: String): LastName = lastName

      given lastNameCanEqual: CanEqual[LastName, LastName]
      = CanEqual.derived

      extension(lastName: LastName) {
        def value: String = lastName
      }
    }

  }
}

import data.*

val firstName = Person.FirstName("Kevin")
val lastName = Person.LastName("Lee")

firstName.nestedTypeName
lastName.nestedTypeName
Person(firstName, lastName).nestedTypeName
```
