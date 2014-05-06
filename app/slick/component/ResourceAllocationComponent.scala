package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import model.projectMaster.{ProjectMaster, ResourceAllocations, ResourceAllocation}
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date
import scala.slick.lifted.TableQuery
import model.reports.ReportMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/17/14
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
object ResourceAllocationComponent {

  this: Profile =>

  class Allocations(tag : Tag) extends Table[ResourceAllocation](tag , "project_resources") {

    def allocationId = column[Int]("id",O.PrimaryKey,O.NotNull,O.AutoInc)

    def projectId = column[Int]("project_id",O.NotNull)

    def resourceId = column[Int]("resource_id",O.NotNull)

    def expectedPercent = column[Int]("expected_percent", O.Nullable)

    def actualPercent = column[Option[Int]]("actual_percent",O.NotNull)

    def startDate = column[Date]("start_date",O.Nullable)

    def cost = column[BigDecimal]("cost",O.Nullable)

    def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

    def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

    def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def * = (allocationId, projectId , resourceId , expectedPercent , actualPercent , startDate , cost , createdBy , createdDate , updatedBy , updatedDate) <> (ResourceAllocation.tupled, ResourceAllocation.unapply _)
  }
  val resourceAllocations = TableQuery[Allocations]


  def allocateResource(allocation : ResourceAllocation)(implicit session : Session) = {
    /*val allocations1: List[projectMaster.ResourceAllocation] = allocations.allocations
    allocations1.map(x => resourceAllocations.insert(x)) */
    //for(i <- 0 until allocations.size)

    resourceAllocations.insert(allocation)

  }
  def getAllResourceAllocationsByProjectId(projectId :Int,firstDate :Date,lastDate :Date)(implicit session : Session): Option[List[ResourceAllocation]] = {
    val query = for {
      allocation <- resourceAllocations  if {allocation.projectId === projectId &&
      (allocation.startDate >= firstDate &&  allocation.startDate <= lastDate)}
    }yield(allocation.allocationId ,allocation.projectId, allocation.resourceId ,allocation.expectedPercent, allocation.actualPercent, allocation.startDate, allocation.cost, allocation.createdBy, allocation.createdDate, allocation.updatedBy, allocation.updatedDate)
    val allocationList = query.list
    if(allocationList.isEmpty) None
    else
     Some(allocationList.map(x => new ResourceAllocation(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11)))

  }

  def deleteResourcesByProjectId(projectId :Int,firstDate :Date,lastDate :Date)(implicit session :Session) = {

    (resourceAllocations.filter( x => (x.projectId === projectId && x.startDate >= firstDate &&  x.startDate <= lastDate))).delete
  }

  def getAllResourceAllocations()(implicit session :Session) = {
    val query = for {
      p   <- ProjectMasterComponent.projects
      pr <- resourceAllocations if (pr.projectId === p.projectId)

    }yield (p.projectId, p.title, pr.resourceId, pr.expectedPercent, pr.actualPercent, pr.cost, pr.startDate)

    val list = query.list
    if(list.isEmpty) None
    else
      Some(list.map(x => new ReportMaster(x._1,x._2,x._3,x._4,x._5,x._6,x._7,0,0,0,0)))
  }
  def getAllProjectAllocations(projectId :Int)(implicit session :Session) = {
    val query = for {
      p   <- ProjectMasterComponent.projects
      pr <- resourceAllocations if (pr.projectId === p.projectId && p.projectId === projectId)

    }yield (p.projectId, p.title, pr.resourceId, pr.expectedPercent, pr.actualPercent, pr.cost, pr.startDate)

    val list = query.list
    if(list.isEmpty) None
    else
      Some(list.map(x => new ReportMaster(x._1,x._2,x._3,x._4,x._5,x._6,x._7,0,0,0,0)))
      val map : Map[Date,List[(Int, String, Int, Int, Option[Int], scala.math.BigDecimal, java.sql.Date)]]  = list.groupBy(x => x._7)
    map

  }
}
