package common

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.sql.Date
import java.util.Calendar

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/9/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
object DateUtils {
  def formatDateIntoTimestamp(dateStr: String): Date = {
    val format: SimpleDateFormat = new SimpleDateFormat("MM/dd/yy")
    val date = format.parse(dateStr)
    new java.sql.Date(date.getTime());
    //new Timestamp(date.getTime)
  }
  def formatDateIntoString(date: Date): String = {
    val format: SimpleDateFormat = new SimpleDateFormat("MM/dd/yy")
    val strDate = format.format(date)
    strDate
  }
  def getStrMonth(month :Int):String ={
    month match {
      case 1 => {
        "January"
      }
      case 2 => {
        "February"
      }
      case 3 => {
        "March"
      }
      case 4 => {
        "April"
      }
      case 5 => {
        "May"
      }
      case 6 => {
        "June"
      }
      case 7 => {
        "July"
      }
      case 8 => {
        "August"
      }
      case 9 => {
        "September"
      }
      case 10 => {
        "October"
      }
      case 11 => {
        "November"
      }
      case 12 => {
        "December"
      }
    }
  }
  def getIntMonth(month :String):Int ={
    month match {
      case "January" => {
        1
      }
      case "February" => {
         2
      }
      case "March" => {
       3
      }
      case "April" => {
         4
      }
      case "May" => {
         5
      }
      case "June" => {
        6
      }
      case "July" => {
         7
      }
      case "August" => {
         8
      }
      case "September" => {
         9
      }
      case "October" => {
         10
      }
      case "November" => {
         11
      }
      case "December" => {
         12
      }
    }
  }
  def getDateRange() = {

    val firstDate = (Calendar.getInstance().getTime.getMonth()+1)+"/1/"+Calendar.getInstance().get(Calendar.YEAR)
    val lastDate = (Calendar.getInstance().getTime.getMonth()+1)+"/"+getLastDayByMonth()+"/"+Calendar.getInstance().get(Calendar.YEAR)
    val tup =  (formatDateIntoTimestamp(firstDate),formatDateIntoTimestamp(lastDate))
    tup

  }
  def getDateRange(period :String) = {
    var month : Int = getIntMonth(period.split("-")(0))
    var year : String = period.split("-")(1)

    val firstDate = month+"/1/"+year
    val lastDate = month+"/"+getLastDayByMonth(month)+"/"+year
    val tup =  (formatDateIntoTimestamp(firstDate),formatDateIntoTimestamp(lastDate))
    tup

  }
   def getLastDayByMonth():Int ={
    val month = Calendar.getInstance().getTime.getMonth()+1
    month match {
      case 1 => {
        31
      }
      case 2 => {
        if((Calendar.getInstance().get(Calendar.YEAR) % 4 == 0))
            28
        else
          29
      }
      case 3 => {
        31
      }
      case 4 => {
        30
      }
      case 5 => {
        31
      }
      case 6 => {
        30
      }
      case 7 => {
        31
      }
      case 8 => {
        31
      }
      case 9 => {
        30
      }
      case 10 => {
        31
      }case 11 => {
        30
      } case 12 => {
        31
      }
  }
    }
  def getLastDayByMonth(month :Int):Int ={
   // val month = Calendar.getInstance().getTime.getMonth()+1
    month match {
      case 1 => {
        31
      }
      case 2 => {
        if((Calendar.getInstance().get(Calendar.YEAR) % 4 == 0))
          28
        else
          29
      }
      case 3 => {
        31
      }
      case 4 => {
        30
      }
      case 5 => {
        31
      }
      case 6 => {
        30
      }
      case 7 => {
        31
      }
      case 8 => {
        31
      }
      case 9 => {
        30
      }
      case 10 => {
        31
      }case 11 => {
        30
      } case 12 => {
        31
      }
    }
  }
  def getMonthFromDate(date:Date)  ={
    val month = new SimpleDateFormat("MMMM").format(date);
     month
  }
  def getYearFromDate(date :Date) = {
    val year =  new SimpleDateFormat("YYYY").format(date);
    year
  }
}
