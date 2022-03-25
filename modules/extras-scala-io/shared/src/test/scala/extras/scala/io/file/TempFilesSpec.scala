package extras.scala.io.file

import hedgehog._
import hedgehog.runner._

import java.io.File

/** @author Kevin Lee
  * @since 2022-03-24
  */
object TempFilesSpec extends Properties {
  override def tests: List[Test] = List(
    property("testRunWithTempDir", testRunWithTempDir)
  )

  def testRunWithTempDir: Property = for {
    rootFilename <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).log("rootFilename")
    filename1    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1").log("filename1")
    filename1a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1a").log("filenames1a")
    filename1b   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_1b").log("filenames1b")
    filename2    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_2").log("filename2")
    filename3    <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3").log("filename3")
    filename3a   <- Gen.string(Gen.alphaNum, Range.linear(5, 10)).map(_ + "_3a").log("filename3a")
  } yield {

    var target: Option[File] = None // scalafix:ok DisableSyntax.var

    try {
      val result = TempFiles.runWithTempDir("testRunWithTempDir") { tempDir =>
        target = Some(tempDir.value)

        val rootFile = new File(tempDir.value, rootFilename)
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

        val allFiles = List(file3a, file3, file2, file1b, file1a, file1, rootFile)
        Result.assert(allFiles.forall(_.exists)).log("Failed: allFiles.forall(_.exists)")
      }

      result match {
        case Right(allFilesExist) =>
          Result.all(
            List(
              allFilesExist,
              target.fold(
                Result.failure.log("No target file found. It looks like there's some issue in the test")
              ) { targetFile =>
                Result.assert(!targetFile.exists()).log("Failed: !targetFile.exists()")
              }
            )
          )
        case Left(err) =>
          Result.failure.log(s"the target should have been removed but got an error instead. ${err.getMessage}")
      }
    } finally {
      target.foreach(FileUtils.cleanUpFilesInside)
      ()
    }
  }

}
