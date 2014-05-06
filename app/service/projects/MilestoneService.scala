package service.projects

import model.projectMaster.ProjectMaster
import model.milestones.MilestoneMaster
import dao.projects.MilestoneDao
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/10/14
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
object MilestoneService extends BaseService{
  def addMilestone(milestone: MilestoneMaster): Int =  transactional{
    MilestoneDao.addMilestone(milestone)
  }
  def getAllMilestones(id : Int):Option[List[MilestoneMaster]] = transactional{
    val milestoneList = MilestoneDao.getAllMilestones(id)
    milestoneList
  }
  def getMilestoneById(id : Int):Option[MilestoneMaster] = transactional {
    MilestoneDao.getMilestoneById(id)

  }
  def editMilestone(milestone: MilestoneMaster): Int =  transactional{
    MilestoneDao.editMilestone(milestone)
  }
  def getAllMilestonesByDate(id : Int,start_date : Date,end_date : Date):Option[List[MilestoneMaster]] = transactional{
    val milestoneList = MilestoneDao.getAllMilestonesByDate(id,start_date,end_date)
    milestoneList
  }
}
