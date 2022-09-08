---
sidebar_position: 2
id: 'string'
title: 'string Syntax'
---
## `EmptyString ++ EmptyString`
### Problem without `string` syntax
To concatenate two or more `NonEmptyString`s, you need to do something like this.
```scala mdoc:reset-object
import eu.timepit.refined.types.string.NonEmptyString

val a = NonEmptyString("aaa")
val b = NonEmptyString("bbb")
NonEmptyString.unsafeFrom(a.value + b.value)

val hello = NonEmptyString("Hello")
val space = NonEmptyString(" ")
val world = NonEmptyString("world")
NonEmptyString.unsafeFrom(hello.value + space.value + world.value)

import eu.timepit.refined.auto._
NonEmptyString("A") + NonEmptyString("B") // This works but it returns String not NonEmptyString
```

### With `string` syntax
If you use `string` syntax from extras, you can just do
```scala mdoc:reset-object
import eu.timepit.refined.types.string.NonEmptyString
import extras.refinement.syntax.string._

val a = NonEmptyString("aaa")
val b = NonEmptyString("bbb")
a ++ b

val hello = NonEmptyString("Hello")
val space = NonEmptyString(" ")
val world = NonEmptyString("world")
hello ++ space ++ world
```

More examples
```scala mdoc:reset-object
import eu.timepit.refined.types.string.NonEmptyString
import extras.refinement.syntax.string._

NonEmptyString("abc") ++ NonEmptyString("def")

val givenName = NonEmptyString("Kevin")
val surname = NonEmptyString("Lee")
val fullName = givenName ++ NonEmptyString(" ") ++ surname
```
