package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import model.resources.{Status, ResourceMaster}
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date
import model.projectMaster.ProjectMaster


/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/14/14
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
object ResourceMasterComponent {

  this: Profile =>

  class Resources(tag : Tag) extends Table[ResourceMaster](tag , "resources") {



    def resourceId = column[Int]("id",O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name",O.NotNull)

    def middleName = column[Option[String]]("middle_name", O.Nullable)

    def lastName = column[String]("last_name",O.NotNull)

    def emailId = column[String]("email_id",O.NotNull)

    def skills = column[String]("skills",O.NotNull)

    def yrExp = column[Int]("exp_year",O.NotNull)

    def yrMonth = column[Int]("exp_month",O.NotNull)

    def cost = column[BigDecimal]("cost",O.NotNull)

    def status = column[Int]("status",O.NotNull)

    def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

    def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

    def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def * = (resourceId , firstName , middleName , lastName , emailId , skills , yrExp ,yrMonth,cost,status, createdBy , createdDate , updatedBy , updatedDate) <> (ResourceMaster.tupled, ResourceMaster.unapply _)
  }
  val resources = TableQuery[Resources]

  def getResourceList()(implicit session : Session) : Option[ List [ResourceMaster] ] = {
    val query = for {
      resource <- resources

    }yield(resource.resourceId ,resource.firstName, resource.middleName,resource.lastName, resource.emailId, resource.skills
        , resource.yrExp, resource.yrMonth, resource.cost, resource.status, resource.createdBy, resource.createdDate, resource.updatedBy, resource.updatedDate)
    val resourceList = query.list
    if(resourceList.isEmpty) None
    else
      Some(resourceList.map(x => new ResourceMaster(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14)))
  }
  def addResource(resourceMaster : ResourceMaster) (implicit session : Session):Int ={
    val rId = resources.insert(resourceMaster)
    rId
  }

  def getResourceById(id :Int)(implicit session :Session):Option[ResourceMaster] ={
     val query = for {
       resource <- resources if resource.resourceId === id
     }  yield(resource.resourceId ,resource.firstName, resource.middleName,resource.lastName, resource.emailId, resource.skills
         , resource.yrExp, resource.yrMonth, resource.cost, resource.status, resource.createdBy, resource.createdDate, resource.updatedBy, resource.updatedDate)
    val resourceList = query.list
    if(resourceList.isEmpty) None
    else
      Some(ResourceMaster(resourceList.head._1, resourceList.head._2, resourceList.head._3, resourceList.head._4, resourceList.head._5,
        resourceList.head._6,resourceList.head._7,resourceList.head._8, resourceList.head._9, resourceList.head._10,
        resourceList.head._11, resourceList.head._12, resourceList.head._13, resourceList.head._14))

  }
  def editResource(resource :ResourceMaster)(implicit session : Session) = {

    val pId  = resources.where(_.resourceId === resource.id).update(resource)
    pId
  }
  def getResourceListByStatus(status : Int)(implicit session : Session) : Option[ List [ResourceMaster] ] = {
    val query = for {
      resource <- resources if resource.status === status

    }yield(resource.resourceId ,resource.firstName, resource.middleName,resource.lastName, resource.emailId, resource.skills
        , resource.yrExp, resource.yrMonth, resource.cost, resource.status, resource.createdBy, resource.createdDate, resource.updatedBy, resource.updatedDate)
    val resourceList = query.list
    if(resourceList.isEmpty) None
    else
      Some(resourceList.map(x => new ResourceMaster(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14)))
  }
}
