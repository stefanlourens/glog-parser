import java.io.File
import java.util.Scanner
import glog.Entry

import scala.io.Source

object GlogParser {

  def main(args: Array[String]): Unit = {
    val input = new File("GLogData_001.txt")

    Source.fromFile(input).getLines().drop(1).foreach{ line =>
      println(line)
      val entry = Entry(line)
      println(entry)
      println()
    }



  }
}
