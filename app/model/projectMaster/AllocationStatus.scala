package model.projectMaster

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/22/14
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
case class AllocationStatus (
  var id : Int,
  var project_id : Int,
  var status_date: Date,
  var status : String,
  var created_by : String,
  var created_at : Date,
  var updated_by : String,
  var updated_at : Date
)
