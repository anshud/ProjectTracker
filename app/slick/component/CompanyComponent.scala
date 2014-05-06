package slick.component

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.{TableQuery,Tag}
import model.company.{CompanyCosts, CompanyMaster}
import java.sql.Date
import model.milestones.MilestoneMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
object CompanyComponent {

  this:Profile =>

  class Company (tag : Tag) extends Table[CompanyMaster](tag,"company") {

    def companyId = column[Int]("id",O.PrimaryKey,O.NotNull)

    def companyName = column[String]("company_name",O.NotNull)

    def website = column[String]("website",O.NotNull)

    def contactPhone = column[String]("contactPhone",O.NotNull)

    def address = column[String]("address",O.NotNull)

    def createdBy = column[String]("created_by",O.NotNull)

    def createdDate = column[Date]("created_at",O.NotNull)

    def updatedBy = column[String]("updated_by",O.NotNull)

    def updatedDate = column[Date]("updated_at",O.NotNull)

    def * = (companyId , companyName , website , contactPhone , address , createdBy , createdDate , updatedBy , updatedDate) <> (CompanyMaster.tupled, CompanyMaster.unapply _)
  }
  val companies = TableQuery[Company]

  def getCompany()(implicit session : Session) = {
     val query = for{
       company <- companies
     }yield (company.companyId, company.companyName, company.website, company.contactPhone, company.address, company.createdBy, company.createdDate, company.updatedBy, company.updatedDate)

    val list = query.list
    if(list.isEmpty) None

    else Some(CompanyMaster(list.head._1, list.head._2, list.head._3, list.head._4, list.head._5, list.head._6, list.head._7, list.head._8, list.head._9))
  }
}
