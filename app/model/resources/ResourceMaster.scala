package model.resources

import java.sql.Date
/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
case class ResourceMaster (
  var id : Int,
  var firstName : String,
  var middleName : Option[String],
  var lastName : String,
  var emailId : String,
  var skills : String,
  var yearExperience : Int,
  var monthExperience : Int,
  var cost : BigDecimal,
  var status : Int,
  var created_by : String,
  var created_at : Date,
  var updated_by : String,
  var updated_at : Date
)
