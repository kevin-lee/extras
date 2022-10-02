---
sidebar_position: 1
title: 'syntax'
---
import DocCardList from '@theme/DocCardList';

## `syntax`
Additional syntax for [newtype](https://github.com/estatico/scala-newtype) and [refined](https://github.com/fthomas/refined)
* [refinement](refinement.md) (for `newtype` and `refined`)
* [string](string.md) (for `String` from `refined`)

If you want to import all syntaxes, use
```scala
import extras.refinement.syntax.all._
```
which is equivalent to
```scala
import extras.refinement.syntax.refinement._
import extras.refinement.syntax.string._
```

<DocCardList />
