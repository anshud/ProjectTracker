package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date
import model.milestones.MilestoneMaster
/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/10/14
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
object MilestoneComponent {
  this: Profile =>

  class Milestones(tag : Tag) extends Table[MilestoneMaster](tag , "milestones") {


    def milestoneId = column[Int]("id",O.PrimaryKey, O.AutoInc)

    def title = column[String]("title",O.NotNull)

    def description = column[Option[String]]("desc", O.Nullable)

    def status = column[Int]("status",O.NotNull)

    def cost = column[BigDecimal]("amount",O.NotNull)

    def estimatedCompletionDate = column[Date]("estimated_completion_date",O.NotNull)

    def actualCompletionDate = column[Date]("actual_completion_date",O.NotNull)

    def projectId = column[Int]("project_id",O.Nullable)

    def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

    def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

    def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

    def * = (milestoneId , title , description , status , cost , estimatedCompletionDate , actualCompletionDate , projectId , createdBy , createdDate , updatedBy , updatedDate) <> (MilestoneMaster.tupled, MilestoneMaster.unapply _)
  }
  val milestones = TableQuery[Milestones]
  /**
   * Finds projects by project id.
   */

  def getMilestoneById(Id: Int)(implicit session : Session) = {
    val q2 = for {
      milestone <- milestones if milestone.milestoneId === Id

    } yield (milestone.milestoneId ,milestone.title, milestone.description,milestone.status, milestone.cost, milestone.estimatedCompletionDate,milestone.actualCompletionDate, milestone.projectId, milestone.createdBy, milestone.createdDate, milestone.updatedBy, milestone.updatedDate)
    val list = q2.list
    if (list.isEmpty) None
    else Some(MilestoneMaster(list.head._1,list.head._2,list.head._3,list.head._4,list.head._5,list.head._6,list.head._7, list.head._8, list.head._9, list.head._10, list.head._11, list.head._12))
  }

  def addMilestone(milestone :MilestoneMaster)(implicit session : Session): Int = {

    val pId  = milestones.insert(milestone)
    pId
  }
  def getMilestoneList(id : Int)(implicit session : Session) : Option[ List [MilestoneMaster] ] = {
    val query = for {
        milestone <- milestones  if milestone.projectId === id

    }yield(milestone.milestoneId ,milestone.title, milestone.description,milestone.status, milestone.cost, milestone.estimatedCompletionDate,milestone.actualCompletionDate, milestone.projectId, milestone.createdBy, milestone.createdDate, milestone.updatedBy, milestone.updatedDate)
    val milestoneList = query.list
    if(milestoneList.isEmpty) None
    else
      Some(milestoneList.map(x => new MilestoneMaster(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11, x._12)))
  }
  def editMilestone(milestone :MilestoneMaster)(implicit session : Session): Int = {

    val pId  = milestones.where(_.milestoneId === milestone.id).update(milestone)
    pId
  }
  def getMilestoneListByDate(id : Int,firstDate : Date,lastDate : Date)(implicit session : Session) : Option[ List [MilestoneMaster] ] = {
    val query = for {
      milestone <- milestones  if{ milestone.projectId === id &&
      (milestone.actualCompletionDate >= firstDate &&  milestone.actualCompletionDate <= lastDate)}

    }yield(milestone.milestoneId ,milestone.title, milestone.description,milestone.status, milestone.cost, milestone.estimatedCompletionDate,milestone.actualCompletionDate, milestone.projectId, milestone.createdBy, milestone.createdDate, milestone.updatedBy, milestone.updatedDate)
    val milestoneList = query.list
    if(milestoneList.isEmpty) None
    else
      Some(milestoneList.map(x => new MilestoneMaster(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11, x._12)))
  }
}
