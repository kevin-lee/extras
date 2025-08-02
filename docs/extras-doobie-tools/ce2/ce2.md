---
sidebar_position: 1
sidebar_label: 'Cats Effect 2'
id: 'ce2'
title: 'Extras - Doobie Tools for Cats Effect 2'
---

## Docs WIP

## Module
```scala
"io.kevinlee" %% "extras-doobie-tools-ce2" % "@VERSION@"
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-doobie-tools-ce2" % "@VERSION@"
```


## DbTools.fetchSingleRow

```scala
val fetch: F[Option[Example]] = DbTools.fetchSingleRow[F][Example](
  sql"""
  SELECT id, name, note
    FROM db_tools_test.example
"""
)(transactor)
```

## DbTools.updateSingle

```scala
val insert: F[Int] = DbTools.updateSingle[F](
  sql"""
   INSERT INTO db_tools_test.example (id, name, note) VALUES (${example.id}, ${example.name}, ${example.note})
 """
)(transactor)
```
