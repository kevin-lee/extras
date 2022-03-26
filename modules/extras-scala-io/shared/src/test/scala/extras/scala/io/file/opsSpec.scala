package extras.scala.io.file

import hedgehog._
import hedgehog.runner._

import java.io.File
import java.nio.file.Files
import scala.util.Try

/** @author Kevin Lee
  * @since 2022-03-24
  */
object opsSpec extends Properties {
  override def tests: List[Test] = List(
    property("testGetAllFiles", testGetAllFiles),
    property("testDeleteFileRecursively", testDeleteFileRecursively)
  )

  def testGetAllFiles: Property = for {
    rootFilename <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("rootFilename")
    filename1    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1").log("filename1")
    filename1a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1a").log("filenames1a")
    filename1b   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1b").log("filenames1b")
    filename2    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_2").log("filename2")
    filename3    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3").log("filename3")
    filename3a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3a").log("filename3a")
  } yield {
    val tempDir = Files.createTempDirectory("tmp").toFile
    try {

      val rootFile = new File(tempDir, rootFilename)
      rootFile.mkdir()

      val file1  = new File(rootFile, filename1)
      file1.mkdir()
      val file1a = new File(file1, filename1a)
      file1a.createNewFile()
      val file1b = new File(file1, filename1b)
      file1b.createNewFile()

      val file2 = new File(rootFile, filename2)
      file2.mkdir()

      val file3  = new File(rootFile, filename3)
      file3.mkdir()
      val file3a = new File(file1, filename3a)
      file3a.createNewFile()

      import ops._

      val expected = List(file3a, file3, file2, file1b, file1a, file1, rootFile)
      val actual   = getAllFiles(rootFile)

      actual.sorted ==== expected.sorted
    } finally {
      Try(FileUtils.cleanUpFilesInside(tempDir)).foreach(_ => ())
      Try(tempDir).foreach(_ => ())
    }
  }

  def testDeleteFileRecursively: Property = for {
    rootFilename <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("rootFilename")
    filename1    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1").log("filename1")
    filename1a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1a").log("filenames1a")
    filename1b   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1b").log("filenames1b")
    filename2    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_2").log("filename2")
    filename3    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3").log("filename3")
    filename3a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3a").log("filename3a")
  } yield {
    val tempDir = Files.createTempDirectory("tmp").toFile
    try {

      val rootFile = new File(tempDir, rootFilename)
      rootFile.mkdir()

      val file1  = new File(rootFile, filename1)
      file1.mkdir()
      val file1a = new File(file1, filename1a)
      file1a.createNewFile()
      val file1b = new File(file1, filename1b)
      file1b.createNewFile()

      val file2 = new File(rootFile, filename2)
      file2.mkdir()

      val file3  = new File(rootFile, filename3)
      file3.mkdir()
      val file3a = new File(file1, filename3a)
      file3a.createNewFile()

      import ops._

      val allFiles = getAllFiles(rootFile)

      val allExist = Result.assert(allFiles.forall(_.exists))

      deleteFileRecursively(rootFile)

      val noFileExists = Result.assert(allFiles.forall(!_.exists))

      Result.all(
        List(
          allExist.log("Not all files exist"),
          noFileExists.log("Not all files were deleted"),
          Result.assert(!rootFile.exists).log("rootFile still exists")
        )
      )
    } finally {
      Try(FileUtils.cleanUpFilesInside(tempDir)).foreach(_ => ())
      Try(tempDir).foreach(_ => ())
    }
  }

}
