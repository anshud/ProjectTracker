package dao.projects

import slick.component._
import scala.slick.driver.MySQLDriver.simple._
import model.projectMaster.ProjectMaster
import model.resources.ResourceMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/14/14
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
object ResourceDao   extends DBContext{
  def getAllResources():Option[List[ResourceMaster]] = {
    db.withSession{
      implicit session:Session =>
        val resourceList = ResourceMasterComponent.getResourceList()
        resourceList
    }
  }
  def addResource(resourceMaster : ResourceMaster):Int ={
   db.withSession{
     implicit session :Session =>
       ResourceMasterComponent.addResource(resourceMaster)
  }
}
  def getResourceById(id :Int):Option[ResourceMaster] ={
    db.withSession{
      implicit session :Session =>
        ResourceMasterComponent.getResourceById(id)
    }
  }
  def editResource(resource :ResourceMaster) ={
    db.withSession{
      implicit session :Session =>
        ResourceMasterComponent.editResource(resource)
    }
  }
  def getAllResourcesByStatus(status :Int):Option[List[ResourceMaster]] = {
    db.withSession{
      implicit session:Session =>
        val resourceList = ResourceMasterComponent.getResourceListByStatus(status :Int)
        resourceList
    }
  }
}
