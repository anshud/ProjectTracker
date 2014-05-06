package dao.projects

import dao.projects.common.BaseDao
import model.company.{MonthlyCost, CompanyMaster, CompanyCosts}
import scala.slick.driver.MySQLDriver.simple._
import slick.component.{MonthlyCostComponent, CompanyComponent}

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
object CompanyDao extends BaseDao {

  def getMonthlyCostsForCompany() = {
    db.withSession {
      implicit session : Session =>
        var costList : Option[List[MonthlyCost]] = None
       val company : Option[CompanyMaster] = CompanyComponent.getCompany()
      if(company.size > 0){
       costList = MonthlyCostComponent.getMonthCostsByCompanyId(company.get.companyId)
      }
       val tup = (company,costList)
        tup
    }
  }
}
