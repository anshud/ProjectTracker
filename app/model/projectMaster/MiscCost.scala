package model.projectMaster

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
case class MiscCost (

    var id: Int,
    var projectId : Int,
    var costTitle : String,
    var costValue : BigDecimal,
    var startDate : Date,
    var createdBy :String,
    var createdDate : Date,
    var updatedBy : String,
    var updatedDate  : Date

)
