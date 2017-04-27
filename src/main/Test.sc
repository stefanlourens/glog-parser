import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.{Date, Scanner}


val s = "2016/05/10  14:45:02"
val datePattern = "20[0-9]{2}".r.pattern
val scanner = new Scanner(new StringReader(s))
 scanner.nextLine()