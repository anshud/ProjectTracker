package model.projectMaster

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
case class ProjectMaster (
  var id : Int,
  var title : String,
  var desc : Option[String],
  var status : Int,
  var start_date: Date,
  var end_date :Date,
  var cost : Int,
  var created_by : String,
  var created_at : Date ,
  var updated_by : String,
  var updated_at : Date

)


