package service.projects

import model.projectMaster.{MiscCost, ResourceAllocations, ResourceAllocation, ProjectMaster}
import dao.projects.ProjectsDao
import java.util.Calendar
import java.sql.Date
import slick.component.ResourceAllocationComponent
import common.DateUtils
import model.reports.ReportMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/7/14
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
object ProjectsService extends BaseService{
  def addProjects(project: ProjectMaster): Int =  transactional{
     ProjectsDao.addProject(project)
  }
  def getAllProjects():Option[List[ProjectMaster]] = transactional{
    val projectList = ProjectsDao.getAllProjects()
    projectList
  }
  def getProjectById(id : Int):Option[ProjectMaster] = transactional {
    ProjectsDao.getProjectById(id)

  }
  def editProjects(project: ProjectMaster): Int =  transactional{
    ProjectsDao.editProject(project)
  }
  def getProjectByTitle(projectTitle: String):Option[ProjectMaster] = transactional{

    ProjectsDao.getProjectByTitle(projectTitle)
  }
  def allocateResource(allocation : ResourceAllocation) = transactional {
     ProjectsDao.allocateResource(allocation)
  }
  def getAllResourceAllocations(projectId :Int,firstDate :Date,lastDate :Date):  Option[List[ResourceAllocation]] = transactional {
    ProjectsDao.getAllResourceAllocations(projectId,firstDate,lastDate)
  }
  def deleteAllocationsByProjectId(projectId :Int,firstDate :Date,lastDate :Date) = transactional {
    ProjectsDao.deleteAllocationsByProjectId(projectId,firstDate,lastDate)
  }

  def addMiscCost(miscCost : MiscCost) = transactional {
    ProjectsDao.addMiscCost(miscCost)
  }
  def getAllMiscCosts(projectId :Int,firstDate :Date,lastDate :Date):  Option[List[MiscCost]] = transactional {
    ProjectsDao.getAllMiscCosts(projectId,firstDate,lastDate)
  }
  def deleteMiscCostProjectId(projectId :Int,firstDate :Date,lastDate :Date) = transactional {
    ProjectsDao.deleteMiscCostProjectId(projectId,firstDate,lastDate)
  }
  def getAllProjectAllocations() : Option[List[ReportMaster]] = transactional {
    ProjectsDao.getAllProjectAllocations()
  }
  def getAllProjectAllocations(projectId :Int) = transactional {
    ProjectsDao.getAllProjectAllocations(projectId)
  }
}
