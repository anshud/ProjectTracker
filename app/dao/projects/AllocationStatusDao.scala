package dao.projects

import scala.slick.driver.MySQLDriver.simple._
import slick.component.{AllocationStatusComponent, DBContext}
import model.projectMaster.AllocationStatus
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/22/14
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
object AllocationStatusDao extends DBContext{

  def saveAllocationStatus(allocationStatus : AllocationStatus) = {
    db.withSession{
      implicit session :Session =>{
        AllocationStatusComponent.saveAllocateStatusResource(allocationStatus)
      }
    }
  }
  def getAllocationStatusByProjectId(projectId :Int,firstDate :Date,lastDate :Date):Option[AllocationStatus] = {
    db.withSession{
      implicit session :Session => {
        AllocationStatusComponent.getAllocationStatusByProjectId(projectId,firstDate,lastDate)
      }

    }
  }
  def deleteStatusEntity(projectId :Int,firstDate :Date,lastDate :Date) = {
    db.withSession{
     implicit session :Session =>{
       AllocationStatusComponent.deleteStatusEntity(projectId,firstDate,lastDate)
     }
    }
  }
}
