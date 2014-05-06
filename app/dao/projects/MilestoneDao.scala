package dao.projects

import slick.component._
import scala.slick.driver.MySQLDriver.simple._
import model.milestones.MilestoneMaster
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/10/14
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
object MilestoneDao  extends DBContext{

  def addMilestone(milestone: MilestoneMaster): Int = {
    db.withSession{
      implicit session:Session =>
        MilestoneComponent.addMilestone(milestone)
    }
  }
  def getAllMilestones(id :Int):Option[List[MilestoneMaster]] = {
    db.withSession{
      implicit session:Session =>
        val milestoneList = MilestoneComponent.getMilestoneList(id)
        milestoneList
    }
  }
  def getMilestoneById(id : Int):Option[MilestoneMaster] = {
    db.withSession{
      implicit session:Session =>
        MilestoneComponent.getMilestoneById(id)
    }
  }
  def editMilestone(milestone: MilestoneMaster): Int = {
    db.withSession{
      implicit session:Session =>
        MilestoneComponent.editMilestone(milestone)
    }
  }
  def getAllMilestonesByDate(id :Int,start_date : Date,end_date : Date):Option[List[MilestoneMaster]] = {
    db.withSession{
      implicit session:Session =>
        val milestoneList = MilestoneComponent.getMilestoneListByDate(id,start_date,end_date)
        milestoneList
    }
  }
}
