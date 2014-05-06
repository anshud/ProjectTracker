package service.projects

import model.projectMaster.ProjectMaster
import dao.projects.{ResourceDao, ProjectsDao}
import model.resources.ResourceMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/14/14
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
object ResourceService extends BaseService{

  def getAllResources():Option[List[ResourceMaster]] = transactional{
    val resourceList = ResourceDao.getAllResources()
    resourceList
  }

  def addResource(resourceMaster : ResourceMaster):Int =transactional{
         ResourceDao.addResource(resourceMaster)
  }

  def getResourceById(id : Int):Option[ResourceMaster] = transactional {
    ResourceDao.getResourceById(id)
  }
  def editResource(resource : ResourceMaster) = transactional {
    ResourceDao.editResource(resource)
  }
  def getAllResourcesByStatus(status : Int):Option[List[ResourceMaster]] = transactional{
    val resourceList = ResourceDao.getAllResourcesByStatus(status : Int)
    resourceList
  }
}
