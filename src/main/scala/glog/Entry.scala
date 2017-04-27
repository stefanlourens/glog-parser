package glog

import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.{Date, Scanner}

//No	Mchn	EnNo     Name        Mode    IOMd    DateTime
//000001    1  000000001    john  1   0   2016/05/10  14:45:02


case class Entry(
  entryNumber: Int,
  machineNumber: Int,
  employeeNumber: Int,
  name: String,
  mode: Int,
  ioMd: Int,
  date: Date)


object Entry {

  def apply(logLine: String): Entry = {
    val dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss")
    val scanner = new Scanner(new StringReader(logLine))


    Entry(
      entryNumber = scanner.nextInt(),
      machineNumber = scanner.nextInt(),
      employeeNumber = scanner.nextInt(),
      name = scanner.next(),
      mode = scanner.nextInt(),
      ioMd = scanner.nextInt(),
      date = dateFormat.parse{
        val s = scanner.nextLine().trim
        println(s"date = $s = " + dateFormat.format(dateFormat.parse(s)))
        s
      }
    )
  }
}