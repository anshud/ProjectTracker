package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import model.projectMaster.{ResourceAllocation, AllocationStatus}
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/22/14
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
object AllocationStatusComponent {

  this: Profile =>

  class AllocationStatusClass(tag : Tag) extends Table[AllocationStatus](tag , "allocation_status") {

    def allocationStatusId = column[Int]("id",O.PrimaryKey,O.NotNull,O.AutoInc)

    def projectId = column[Int]("project_id",O.NotNull)

    def statusDate = column[Date]("status_date",O.NotNull)

    def status = column[String]("status",O.NotNull)

    def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

    def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

    def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def * = (allocationStatusId, projectId , statusDate , status , createdBy , createdDate , updatedBy , updatedDate) <> (AllocationStatus.tupled, AllocationStatus.unapply _)
  }
  val allocationStatuses = TableQuery[AllocationStatusClass]

  def saveAllocateStatusResource(statusEntity : AllocationStatus)(implicit session : Session) = {
    allocationStatuses.insert(statusEntity)

  }
  def getAllocationStatusByProjectId(projectId :Int,firstDate :Date,lastDate :Date)(implicit session :Session) = {
    val query = for{
      allocationStatus <- allocationStatuses  if {allocationStatus.projectId === projectId &&
      (allocationStatus.statusDate >= firstDate &&  allocationStatus.statusDate <= lastDate)}
    }yield(allocationStatus.allocationStatusId ,allocationStatus.projectId, allocationStatus.statusDate,allocationStatus.status, allocationStatus.createdBy, allocationStatus.createdDate, allocationStatus.updatedBy, allocationStatus.updatedDate)
    val list = query.list
    if(list.isEmpty) None
    else
      Some(AllocationStatus(list.head._1, list.head._2, list.head._3, list.head._4, list.head._5,list.head._6,list.head._7,list.head._8))
    }
  def deleteStatusEntity(projectId :Int,firstDate :Date,lastDate :Date)(implicit session :Session) = {

    (allocationStatuses.filter( x => (x.projectId === projectId && x.statusDate >= firstDate &&  x.statusDate <= lastDate ))).delete
  }
}
