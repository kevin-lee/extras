---
sidebar_position: 3
id: 'file'
title: 'File'
---

# File

## syntax

### `listAllFilesRecursively`

`listAllFilesRecursively` in `import extras.scala.io.file.syntax`
* `listAllFilesRecursively` returns all files and directories in the given `File` as well as the given file itself if it is a directory.
* If it's a file, it returns a `List` containing the given `File`.

:::note
The result of `listAllFilesRecursively` is not sorted, so you need to sort it yourself.<br/>
e.g.)
```scala
listAllFilesRecursively("path/to/file").sorted
```
:::

e.g.) If the file structure looks like this
```
 /tmp/a
├──  b
│  ├──  b-1
│  │  ├──  b-1-1.txt
│  │  ├──  b-1-2.txt
│  │  └──  b-1-3.txt
│  └──  b-2
│     ├──  b-2-1
│     │  ├──  b-2-1-1.txt
│     │  └──  b-2-1-2.txt
│     └──  b-2-2
├──  c
│  └──  c-1.txt
└──  d
   ├──  d-1
   │  ├──  d-1-1.txt
   │  ├──  d-1-2.txt
   │  ├──  d-1-3.txt
   │  ├──  d-1-4.txt
   │  └──  d-1-5.txt
   ├──  d-2.txt
   └──  d-3.txt
```

the result of `listAllFilesRecursively` looks like this.
```scala
import extras.scala.io.file.syntax._
import java.io.File

listAllFilesRecursively(new File("/tmp/a")).sorted

// List(
//   /tmp/a,
//   /tmp/a/b,
//   /tmp/a/b/b-1,
//   /tmp/a/b/b-1/b-1-1.txt,
//   /tmp/a/b/b-1/b-1-2.txt,
//   /tmp/a/b/b-1/b-1-3.txt,
//   /tmp/a/b/b-2,
//   /tmp/a/b/b-2/b-2-1,
//   /tmp/a/b/b-2/b-2-1/b-2-1-1.txt,
//   /tmp/a/b/b-2/b-2-1/b-2-1-2.txt,
//   /tmp/a/b/b-2/b-2-2,
//   /tmp/a/c,
//   /tmp/a/c/c-1.txt,
//   /tmp/a/d,
//   /tmp/a/d/d-1,
//   /tmp/a/d/d-1/d-1-1.txt,
//   /tmp/a/d/d-1/d-1-2.txt,
//   /tmp/a/d/d-1/d-1-3.txt,
//   /tmp/a/d/d-1/d-1-4.txt,
//   /tmp/a/d/d-1/d-1-5.txt,
//   /tmp/a/d/d-2.txt,
//   /tmp/a/d/d-3.txt
// )
```
or
```scala
new File("/tmp/a").listAllFilesRecursively.sorted
```

### `deleteAllRecursively`

`deleteAllRecursively` in `import extras.scala.io.file.syntax` removes the given file and all the files and directories in it if the given file is a directory.

e.g.) If the file structure looks like this
```
 /tmp/a
├──  b
│  ├──  b-1
│  │  ├──  b-1-1.txt
│  │  ├──  b-1-2.txt
│  │  └──  b-1-3.txt
│  └──  b-2
│     ├──  b-2-1
│     │  ├──  b-2-1-1.txt
│     │  └──  b-2-1-2.txt
│     └──  b-2-2
├──  c
│  └──  c-1.txt
└──  d
   ├──  d-1
   │  ├──  d-1-1.txt
   │  ├──  d-1-2.txt
   │  ├──  d-1-3.txt
   │  ├──  d-1-4.txt
   │  └──  d-1-5.txt
   ├──  d-2.txt
   └──  d-3.txt
```

`deleteAllRecursively` deletes the given file and everything in it.
```scala
import extras.scala.io.file.syntax._
import java.io.File

deleteAllRecursively(new File("/tmp/a"))
// The `/tmp/a` and everything inside is removed.
```
or
```scala
new File("/tmp/a").deleteAllRecursively()
```

## `TempFiles`

### `runWithTempDir`

If you want to do something any temporary folder which should be deleted once it's done, you can use `extras.scala.io.file.TempFiles.runWithTempDir`.

```scala mdoc:reset-object
import extras.scala.io.file.TempFiles
import java.io.File

def foo(file: File): Unit =
  if (file.exists)
    println(s"${file.getParentFile.getName}/${file.getName} exists")
  else
    println(s"${file.getParentFile.getName}/${file.getName} does not exist.")

var tmp: Option[File] = None

TempFiles.runWithTempDir("temporary-dir-prefix") { tempDir =>

  tmp = Some(tempDir.value) // To check if the directory exists outside this block.
  
  tmp.foreach(foo)

  val tmpDir = tempDir.value
  val someFile = new File(tmpDir, "myfile.txt")
  someFile.createNewFile()
  foo(someFile)
  
  val someFile2 = new File(tmpDir, "myfile2.txt")
  foo(someFile2)
  
  "Done"
}

tmp.foreach{ file => 
  println(s"${file.getParentFile.getName}/${file.getName}")
  foo(file)
}

/* the someFile (File(tmpDir, "myfile.txt") is deleted before TempFiles.runWithTempDir returns the result */ 
```
`tempDir` is of type `TempDir` which is just this value class.
```scala
final case class TempDir(value: java.io.File) extends AnyVal
```

`TempFiles.runWithTempDir` is useful when you test with files and need to remove them once the test is done.


#### Example: test with hedgehog

```scala
import hedgehog._
import hedgehog.runner._

import extras.scala.io.file.TempFiles
import java.io._
import scala.util.Using

object MyTest extends Properties {
  def tests: List[Test] = List(
    property("test something with files", testSomethingWithFiles)
  )
  
  def testSomethingWithFiles: Property = for {
    filename <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("filename")
    content1 <- Gen.string(Gen.alphaNum, Range.linear(10, 100)).log("content1")
    content2 <- Gen.string(Gen.alphaNum, Range.linear(10, 100)).log("content2")
  } yield {
    val content = s"$content1\n$content2"
    TempFiles.runWithTempDir("my-temp-dir") { tempDir =>
      val rootDir = tempDir.value
      val file = new File(rootDir, filename)
      
      (for { 
        _      <- Using(new PrintWriter(file))(_.write(content))
        result <- Using(scala.io.Source.fromFile(file))(_.mkString)
      } yield result).toEither
      
    }.joinRight match {
      case Right(actual) => actual ==== content
      case Left(err) => Result.failure.log(s"Failed: ${err.getMessage}")
    }
  }
  
}
```
