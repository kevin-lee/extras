---
sidebar_position: 2
id: 'core-syntax'
title: 'Syntax'
---

## Module

* The `core` module:
  ```scala
  "io.kevinlee" %% "extras-core" % "@VERSION@"
  ```

or for `Scala.js`:

* The `core` module:
  ```scala
  "io.kevinlee" %%% "extras-core" % "@VERSION@"
  ```
  
## Import
Scala 2
```scala
import extras.core.syntax.all._
```

Scala 3
```scala 3
import extras.core.syntax.all.*
```


## `core` Syntax
There is `core` syntax provided for convenience.

### `String.encodeToUnicode`
```scala
String.encodeToUnicode
```

```scala mdoc:reset-object
import extras.core.syntax.all._

"A".encodeToUnicode
"a".encodeToUnicode

"Z".encodeToUnicode
"z".encodeToUnicode

"Hello".encodeToUnicode

" ".encodeToUnicode

"\n".encodeToUnicode
```

### Elvis Operator (`?:`) for Scala 3

```scala
a ?: fallback
```

![Elvis Operator](https://github.com/user-attachments/assets/dace5e5a-a542-465f-bbb2-9423c66dfa60)


```scala 3
import extras.core.syntax.all.*

val a = "Some value"
// a: String = "Some value"

a ?: "default"
// String = "Some value"

val b: String = null
// b: String = null

b ?: "default"
// String = "default"
```

### Crying Elvis Operator (`?:=`) for Scala 2

```scala
a ?:= fallback
```

![Crying Elvis Operator](https://github.com/user-attachments/assets/fbf021d0-c6b0-40dd-b756-22b7f3d4f9a2)

```scala mdoc:reset-object
import extras.core.syntax.all._

val a = "Some value"

a ?:= "default"

val b: String = null

b ?:= "default"
```