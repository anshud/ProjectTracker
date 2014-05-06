package service.projects

import dao.projects.{AllocationStatusDao}
import model.projectMaster.AllocationStatus
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/22/14
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
object AllocationStatusService extends BaseService{

  def addAllocationStatus(allocationStatus: AllocationStatus):Int =  transactional{
    AllocationStatusDao.saveAllocationStatus(allocationStatus)
  }
  def getAllocationStatusByProjectId(projectId :Int,firstDate :Date,lastDate :Date): Option[AllocationStatus] = transactional{
    AllocationStatusDao.getAllocationStatusByProjectId(projectId,firstDate,lastDate)
  }
  def deleteStatusEntity(projectId :Int,firstDate :Date,lastDate :Date) = transactional {
    AllocationStatusDao.deleteStatusEntity(projectId,firstDate,lastDate)
  }
}
