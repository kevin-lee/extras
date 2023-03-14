---
sidebar_position: 2
sidebar_label: 'Core'
id: 'core'
title: 'Extras - Testing Tools Core'
---

## `StubTools.missing`
`StubTools.missing` is a tool for a stub (a simple function for testing) so that you don't need to use mock frameworks.

:::info NOTE
In most cases, you probably don't want to use `StubTools.missing` directly. It is recommended to use [StubToolsCats](cats) or [StubToolsFx](effectie) instead.
:::

e.g.)

```scala mdoc:reset-object:height=4
import cats.syntax.all._
import eu.timepit.refined.types.all._
import eu.timepit.refined.cats._
import eu.timepit.refined.auto._
import io.estatico.newtype.macros.newtype
import extras.testing.StubTools

object types {
  @newtype case class Id(value: PosInt)
  @newtype case class Name(value: NonEmptyString)
}
import types._

trait MyService {
  def findName(id: Id): Option[Name]
}

object MyServiceStub {
  def apply(f: => Option[Id => Option[Name]]): MyService = new MyService {
    override def findName(id: Id): Option[Name] = f.fold[Option[Name]](throw StubTools.missing)(_(id))
  }
}

class Hello(myService: MyService) {
  def hello(id: Id): String = {
    myService.findName(id)
    .fold(s"No name found for id ${id.value}") { name =>
      s"Hello ${name.value}"
    }
  }
}

val expectedId = Id(1)
val expectedName = Name("Kevin")

val myService: MyService = MyServiceStub(((id: Id) =>
  if (id.value === expectedId.value)
    expectedName.some
  else
    none
).some)
```
```scala mdoc:nest
val hello = new Hello(myService)
println(hello.hello(Id(1)))
println(hello.hello(Id(2)))
```

```scala mdoc:nest:crash:height=6
/* If you don't expect Hello to use MyService.findName,
 * you can simply remove feeding the function for that operation
 * and StubTools let you know where it fails if Hello uses MySerivce.findName.
 */
val myService2: MyService = MyServiceStub(none)
val hello2 = new Hello(myService2)
println(hello2.hello(Id(1)))
```

:::caution NOTE

Why not just use mock framework for convenience? To answer that, please read [Pitfalls of Mocking in tests](https://www.47deg.com/blog/mocking-and-how-to-avoid-it) from [Xebia Functional](https://www.47deg.com) (formerly known as 47 Degrees)

Besides what the blog tells you, mock frameworks often make you do bad practice like testing the implementation details with `verify`.

There is also an issue when your mock is not correctly set. You may get a `NullPointerException` for that, but it doesn't tell you where it's from and why you get it.

:::


