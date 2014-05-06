package model.company

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
case class MonthlyCost (
  id : Int,
  companyId :Int,
  startDate : Date,
  fixedCost :BigDecimal,
  updatedBy :String,
  updatedDate : Date

)
