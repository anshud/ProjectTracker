package controllers

import model.projectMaster.{ProjectMaster, Status}
import java.util.Calendar
import service.projects.{UserService, MilestoneService, ProjectsService}
import play.mvc.Controller
import net.liftweb.json.{NoTypeHints, Serialization}
import play.api.libs.json.Json
import common.{DateUtils, EnumerationSerializer}
import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Forms._
import play.api.data.Forms.{mapping, text}
import play.api.data.{validation, Form}
import model.projectMaster.ProjectMaster
import java.sql.Date
import model.user.UserMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/7/14
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
object ProjectsController extends Controller{

  /*val project = ProjectMaster(1,"abc",Some("sdh"),Status.ACTIVE,java.sql.Date(Calendar.getInstance().getTime()),
    java.sql.Date(Calendar.getInstance().getTime()),120,"anshu",java.sql.Date(Calendar.getInstance().getTime()),
    "anshu",java.sql.Date(Calendar.getInstance().getTime()))
*/
  val projectForm: Form[ProjectMaster] = Form(

    mapping = mapping(
      "title" -> text(minLength = 4),
      "description" -> text,
      "status" -> number,
      "start_date" -> text,
      "end_date" -> text
    )(
      apply = (title, description, status, start_date, end_date) => ProjectMaster(0, title, Some(description), status, DateUtils.formatDateIntoTimestamp(start_date), DateUtils.formatDateIntoTimestamp(end_date), 0, "System",new Date(System.currentTimeMillis()), "System", new Date(System.currentTimeMillis()) )
    )(
      unapply = projectMaster => Some(projectMaster.title, projectMaster.desc.get, projectMaster.status, DateUtils.formatDateIntoString(projectMaster.start_date), DateUtils.formatDateIntoString(projectMaster.end_date))
    )
  )
  def saveProject(id :Int) = Action{

    implicit request =>
      projectForm.bindFromRequest().fold(
        errors => BadRequest(views.html.errors(Some(projectForm.bindFromRequest().errorsAsJson))),
        projectMaster =>  {
         // val project = ProjectsService.getProjectByTitle(projectMaster.title)
          //if(project == None){
          val userId = request.session.get("userId")
            val user  = UserService.getUserById((userId.get).toInt)
              id match {
                case 0 =>  {
                  if(projectMaster.start_date.after(projectMaster.end_date))
                  {
                    var errorMsg : String = projectMaster.start_date +" can not be after." + projectMaster.start_date
                    Ok(views.html.create_project(projectForm,errorMsg,user,"PROJECTS"))
                  }
                  else{
                  ProjectsService.addProjects(projectMaster)
                  }

                }

                case _ =>  {
                  var project = ProjectsService.getProjectById(id)
                  project.get.title = projectMaster.title

                  project.get.desc = projectMaster.desc
                  project.get.status = projectMaster.status
                  project.get.start_date = projectMaster.start_date
                  project.get.end_date = projectMaster.end_date
                  project.get.updated_at = new Date(System.currentTimeMillis())
                  ProjectsService.editProjects(project.get)
                }
              }
              Redirect("/projectList")
            /*}
            else {
              var errorMsg : String = projectMaster.title +" already exists."
              Ok(views.html.create_project(projectForm,errorMsg))
            }*/


        }
      )

  }
   def getAllProjects() = Action{
     val projectList = ProjectsService.getAllProjects()
     implicit request : Request[Any] => val userId = request.session.get("userId")
     val user  = UserService.getUserById((userId.get).toInt)
     Ok(views.html.projectsList(projectList,user,"PROJECTS"))

   }
  def getProject(id :Int) = Action{
    val project = ProjectsService.getProjectById(id)
    val milestoneList = MilestoneService.getAllMilestones(id)
    implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.edit_project(project,milestoneList,user,"PROJECTS"))

  }
  def getProjectReadOnly(id :Int) = Action{
    val project = ProjectsService.getProjectById(id)
    val milestoneList = MilestoneService.getAllMilestones(id)
    implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.edit_project_readonly(project,milestoneList,user,"PROJECTS"))

  }
  def newProject() = Action{
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.create_project(projectForm,"",user,"PROJECTS"))

  }
  def errors() = Action{
    Ok(views.html.errors(None))

  }
}
