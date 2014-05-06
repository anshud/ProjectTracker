package model.projectMaster

import java.sql.Date
import model.resources.ResourceMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/15/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
case class ResourceAllocation (
    var id : Int,
    var projectId : Int,
    var resource : Int,
    var expectedPercent :Int,
    var actualPercent : Option[Int],
    var start_date : Date,
    var cost : BigDecimal,
    var created_by : String,
    var created_at : Date ,
    var updated_by : String,
    var updated_at : Date

)

