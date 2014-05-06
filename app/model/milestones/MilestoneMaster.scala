package model.milestones
import java.sql.Date
import model.projectMaster.ProjectMaster


/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
case class MilestoneMaster (
  var id : Int,
  var title : String,
  var desc : Option[String],
  var status : Int,
  var cost : BigDecimal,
  var estimated_completion_date : Date,
  var actual_completion_date : Date,
  var projectId : Int,
  var created_by : String,
  var created_at : Date,
  var updated_by : String,
  var updated_at : Date

)


