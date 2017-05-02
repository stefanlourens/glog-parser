import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import glog.Entry

import scala.collection.immutable.Iterable
import scala.concurrent.duration._
import scala.io.Source

object GlogParser {

  def main(args: Array[String]): Unit = {
    val input = new File("GLogData_001.txt")

    val entries = Source.fromFile(input).getLines().drop(1).map(Entry.apply).toList
    val employees: Map[Int, String] = entries.map(e => (e.employeeNumber, e.name)).toMap
    val employeeEntries: Map[Int, List[Entry]] = entries.groupBy(_.employeeNumber)

    val employeeByMonth: Map[Int, Map[String, List[Entry]]] = employeeEntries.map { case (id, entries) =>
      val monthFormat = new SimpleDateFormat("MMMM yyyy")
      val byMonth: Map[String, List[Entry]] = entries.groupBy(e => monthFormat.format(e.date))

      id -> byMonth
    }

    val hoursPerEmployeePerMonth: Map[Int, Map[String, Double]] = employeeByMonth.map { case (id, monthEntries: Map[String, List[Entry]]) =>
      val dayFormat = new SimpleDateFormat("yyyy/MM/dd")
      val hoursPerMonth: Map[String, Double] = monthEntries.map { case (month, entries) =>
        val dayEntries: Map[String, List[Entry]] = entries.groupBy(e => dayFormat.format(e.date))
        val hoursPerDay: Map[String, Double] = dayEntries.map { case (day, entries) =>
          val hoursWorked = entries.take(2).map(_.date) match {
            case in :: Nil =>
              val cal = Calendar.getInstance()
              cal.setTime(in)
              cal.set(Calendar.HOUR, 5)
              cal.set(Calendar.MINUTE, 0)
              cal.set(Calendar.SECOND, 0)
              (cal.getTimeInMillis - in.getTime) / 1.hour.toMillis.toDouble
            case in :: out :: Nil => (out.getTime - in.getTime) / 1.hour.toMillis.toDouble
            case _ => sys.error(s"Expected max 2 entries, got ${entries.size} entries for employee $id on $day: $entries")
          }

          day -> hoursWorked
        }
        month -> hoursPerDay.values.sum
      }

      id -> hoursPerMonth
    }


    val output: Iterable[(Int, String, String, Double)] = hoursPerEmployeePerMonth.flatMap { case (id, monthHours) =>
      val name = employees(id)
      monthHours.map { case (month, hours) =>
        (id, name, month, hours)
      }
    }

    output.toList.sortBy { case (id, name, month, _) =>
      val monthFormat = new SimpleDateFormat("MMMM yyyy")
      (monthFormat.parse(month), id, name)
    }.foreach{ case (id, name, month, hours) =>
        println(List(month, s"${name.capitalize} ($id)" , f"$hours%2.2f hours").map(_.padTo(20, ' ')).mkString)

    }

  }
}
