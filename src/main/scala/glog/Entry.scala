package glog

import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.{Date, Scanner}

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
      date = dateFormat.parse(scanner.nextLine().trim)
    )
  }
}