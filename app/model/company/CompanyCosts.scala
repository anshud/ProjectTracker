package model.company

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
case class CompanyCosts (
  company : CompanyMaster,
  monthlyCosts : Option[List[MonthlyCost]]

)


