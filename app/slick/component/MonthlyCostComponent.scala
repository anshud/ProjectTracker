package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import scala.slick.driver.MySQLDriver.simple._
import model.company.MonthlyCost
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
object MonthlyCostComponent {
   this:Profile =>

  class MonthlyCosts(tag : Tag) extends Table[MonthlyCost](tag , "monthly_cost") {

    def monthlyCostId = column[Int]("id",O.PrimaryKey, O.AutoInc)

    def companyId = column[Int]("company_id",O.NotNull)

    def startDate = column[Date]("start_date", O.Nullable)

    def fixedCost = column[BigDecimal]("fixed_cost",O.NotNull)

    def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

    def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def * = (monthlyCostId , companyId , startDate , fixedCost , updatedBy , updatedDate) <> (MonthlyCost.tupled, MonthlyCost.unapply _)
  }
  val monthlyCosts = TableQuery[MonthlyCosts]


  def getMonthCostsByCompanyId(companyId : Int)(implicit session : Session) = {

    val query  = for {

    cost <- monthlyCosts if cost.companyId === companyId

  }yield(cost.monthlyCostId, cost.companyId, cost.startDate, cost.fixedCost, cost.updatedBy, cost.updatedDate)

  val list = query.list

  if(list.isEmpty)None

  else Some(list.map(x => new MonthlyCost(x._1,x._2,x._3,x._4,x._5,x._6)))
}

}