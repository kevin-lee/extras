package extras.scala.io.file

import java.io.File
import scala.annotation.tailrec
import scala.util.Try

/** @author Kevin Lee
  * @since 2022-03-24
  */
object FileUtils {

  def cleanUpFilesInside(file: File): Unit = {
    @tailrec
    def cleanAll(files: Seq[File]): Unit = {
      if (files.isEmpty) ()
      else {
        val head = files.headOption
        val rest = files.drop(1)
        head match {
          case Some(file) =>
            if (file.isDirectory) {
              val list = file.listFiles
              if (list.isEmpty) {
                Try(file.delete()).foreach(_ => ())
                cleanAll(rest)
              } else {
                cleanAll((list.toVector ++ rest) :+ file)
              }
            } else {
              if (file.exists()) {
                Try(file.delete()).foreach(_ => ())
                cleanAll(rest)
              } else cleanAll(rest)
            }
          case None =>
            cleanAll(rest)
        }
      }
    }
    if (file.exists) {
      if (file.isDirectory) {
        cleanAll(file.listFiles.toVector)
      } else {
        ()
      }
    } else {
      ()
    }
  }
}
