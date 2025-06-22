package extras.scala.io.file

import java.io.File
import scala.annotation.tailrec
import scala.util.control.NonFatal

/** @author Kevin Lee
  * @since 2022-03-24
  */
trait syntax {

  def listAllFilesRecursively(file: File): List[File] = {
    @tailrec
    def getAllFilesRecursively(files: List[File], acc: List[File]): List[File] = files match {
      case file :: rest =>
        if (file.isDirectory) {
          getAllFilesRecursively(file.listFiles.toList ++ rest, file :: acc)
        } else {
          getAllFilesRecursively(rest, file :: acc)
        }

      case Nil =>
        acc
    }
    if (file.isDirectory) {
      getAllFilesRecursively(file.listFiles().toList, List.empty)
    } else {
      List.empty
    }
  }

  private val deleteIfExists: File => Unit = { each =>
    if (each.exists) {
      try {
        val _ = each.delete()
        ()
      } catch {
        case NonFatal(_) =>
          ()
      }
    } else ()
  }

  def deleteAllRecursively(file: File): Unit = {
    listAllFilesRecursively(file).foreach(deleteIfExists)
    deleteIfExists(file)
  }

}

object syntax extends syntax { self =>

  implicit class ExtrasFileOps(private val file: File) extends AnyVal {
    def listAllFilesRecursively: List[File] =
      self.listAllFilesRecursively(file)

    def deleteAllRecursively(): Unit =
      self.deleteAllRecursively(file)
  }

}
