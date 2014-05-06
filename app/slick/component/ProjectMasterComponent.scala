package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import model.projectMaster.{Status, ProjectMaster}
import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
object ProjectMasterComponent {

  this: Profile =>

class Projects(tag : Tag) extends Table[ProjectMaster](tag , "projects") {


  def projectId = column[Int]("id",O.PrimaryKey, O.AutoInc)

  def title = column[String]("title",O.NotNull)

  def description = column[Option[String]]("desc", O.Nullable)

  def status = column[Int]("status",O.NotNull)

  def startDate = column[Date]("start_date",O.Nullable)

  def endDate = column[Date]("end_date",O.Nullable)

  def cost = column[Int]("cost",O.Nullable)

  def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

  def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

  def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

  def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

  def * = (projectId , title , description , status , startDate , endDate , cost , createdBy , createdDate , updatedBy , updatedDate) <> (ProjectMaster.tupled, ProjectMaster.unapply _)
}
  val projects = TableQuery[Projects]
  /**
   * Finds projects by project id.
   */

    def getProjectById(Id: Int)(implicit session : Session) = {
      val q2 = for {
        project <- projects if project.projectId === Id

      } yield (project.projectId ,project.title, project.description,project.status, project.startDate, project.endDate, project.cost, project.createdBy, project.createdDate, project.updatedBy, project.updatedDate)
      val list = q2.list
      if (list.isEmpty) None
      else Some(ProjectMaster(list.head._1,list.head._2,list.head._3,list.head._4,list.head._5,list.head._6,list.head._7, list.head._8, list.head._9, list.head._10, list.head._11))
    }

    def addProjects(project :ProjectMaster)(implicit session : Session): Int = {

      val pId  = projects.insert(project)
      pId
    }
    def getProjectList()(implicit session : Session) : Option[ List [ProjectMaster] ] = {
      val query = for {
        project <- projects.sortBy(_.projectId.desc)

      }yield(project.projectId ,project.title, project.description,project.status, project.startDate, project.endDate, project.cost, project.createdBy, project.createdDate, project.updatedBy, project.updatedDate)

      val projectList = query.list
      if(projectList.isEmpty) None
      else
      Some(projectList.map(x => new ProjectMaster(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9, x._10, x._11)))
    }
  def editProjects(project :ProjectMaster)(implicit session : Session): Int = {

    val pId  = projects.where(_.projectId === project.id).update(project)
    pId
  }
  def getProjectByTitle(title: String)(implicit session : Session) = {
    val q2 = for {
      project <- projects if project.title === title

    } yield (project.projectId ,project.title, project.description,project.status, project.startDate, project.endDate, project.cost, project.createdBy, project.createdDate, project.updatedBy, project.updatedDate)
    val list = q2.list
    if (list.isEmpty) None
    else Some(ProjectMaster(list.head._1,list.head._2,list.head._3,list.head._4,list.head._5,list.head._6,list.head._7, list.head._8, list.head._9, list.head._10, list.head._11))
  }
}
