package model.reports

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/29/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
case class ReportMaster (

  var projectId : Int,
  var projectName : String ,
  var resourceId : Int ,
  var estimatedPercent :Int,
  var actualPercent :Option[Int],
  var resourceCost : BigDecimal,
  var startDate : Date,
  var totalEstimatedCost : BigDecimal,
  var totalActualCost :BigDecimal,
  var totalReceivedCost : BigDecimal,
  var delta : BigDecimal
 )
