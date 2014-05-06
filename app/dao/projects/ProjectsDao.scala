package dao.projects

import model.projectMaster.{MiscCost, ResourceAllocations, ResourceAllocation, ProjectMaster}
import slick.component._
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date
import model.reports.ReportMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/7/14
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
object ProjectsDao extends DBContext{
  def addProject(project: ProjectMaster): Int = {
    db.withSession{
      implicit session:Session =>
        ProjectMasterComponent.addProjects(project)
    }
  }
  def getAllProjects():Option[List[ProjectMaster]] = {
    db.withSession{
      implicit session:Session =>
        val projectList = ProjectMasterComponent.getProjectList()
        projectList
    }
  }
    def getProjectById(id : Int):Option[ProjectMaster] = {
      db.withSession{
        implicit session:Session =>
          ProjectMasterComponent.getProjectById(id)
      }
    }
  def editProject(project: ProjectMaster): Int = {
    db.withSession{
      implicit session:Session =>
        ProjectMasterComponent.editProjects(project)
    }
  }
  def getProjectByTitle(projectTitle: String) :Option[ProjectMaster] = {
    db.withSession{
        implicit session :Session =>
        ProjectMasterComponent.getProjectByTitle(projectTitle)
    }
  }
  def allocateResource(allocation : ResourceAllocation) =  {
    db.withSession{
      implicit session : Session =>
      ResourceAllocationComponent.allocateResource(allocation)
    }
  }
  def getAllResourceAllocations(projectId :Int,firstDate :Date,lastDate :Date):  Option[List[ResourceAllocation]] = {
    db.withSession {
      implicit session :Session =>
        ResourceAllocationComponent.getAllResourceAllocationsByProjectId(projectId,firstDate,lastDate)
    }
  }
  def deleteAllocationsByProjectId(projectId :Int,firstDate :Date,lastDate :Date)= {
    db.withSession {
      implicit session :Session =>
       ResourceAllocationComponent.deleteResourcesByProjectId(projectId,firstDate,lastDate)
    }
  }
  def addMiscCost(miscCost : MiscCost) =  {
    db.withSession {
      implicit session : Session =>

      MiscCostComponent.addMiscCost(miscCost)
    }

  }
  def getAllMiscCosts(projectId :Int,firstDate :Date,lastDate :Date):  Option[List[MiscCost]] = {
    db.withSession {
      implicit session : Session =>

        MiscCostComponent.getAllMiscCosts(projectId,firstDate,lastDate)
    }
  }
  def deleteMiscCostProjectId(projectId :Int,firstDate :Date,lastDate :Date) =  {
    db.withSession {
      implicit session : Session =>

        MiscCostComponent.deleteMiscCostProjectId(projectId,firstDate,lastDate)
    }
  }
  def getAllProjectAllocations() : Option[List[ReportMaster]] = {
    db.withSession {
      implicit session :Session =>
        ResourceAllocationComponent.getAllResourceAllocations()
    }
  }
  def getAllProjectAllocations(projectId :Int)  = {
    db.withSession {
      implicit session :Session =>
        ResourceAllocationComponent.getAllProjectAllocations(projectId)
    }
  }
}


