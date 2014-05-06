package controllers

import play.mvc.Controller
import common.{DateUtils, EnumerationSerializer}
import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Forms._
import play.api.data.Forms.{mapping, text}
import play.api.data.Form
import model.milestones.MilestoneMaster
import java.sql.Date
import service.projects.{UserService, MilestoneService}

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/10/14
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
object MilestoneController  extends Controller{
  val milestoneForm: Form[MilestoneMaster] = Form(

    mapping = mapping(
      "title" -> text(minLength = 4),
      "description" -> text,
      "status" -> number,
      "cost" -> bigDecimal,
      "estimated_completion_date" -> text ,
      "actual_completion_date" -> text ,
      "projectId" -> number
    )(
      apply = (title, description, status, cost, estimated_completion_date,actual_completion_date,projectId) => MilestoneMaster(0, title, Some(description), status, cost, DateUtils.formatDateIntoTimestamp(estimated_completion_date), DateUtils.formatDateIntoTimestamp(actual_completion_date), projectId, "System",new Date(System.currentTimeMillis()), "System", new Date(System.currentTimeMillis()) )
    )(
      unapply = milestone => Some(milestone.title, milestone.desc.get, milestone.status, milestone.cost, DateUtils.formatDateIntoString(milestone.estimated_completion_date), DateUtils.formatDateIntoString(milestone.actual_completion_date),milestone.projectId)
    )
  )
  def saveMilestone(id :Int) = Action{

    implicit request =>
      milestoneForm.bindFromRequest().fold(
        errors => BadRequest(views.html.errors(Some(milestoneForm.bindFromRequest().errorsAsJson))),
        milestone =>  {
          id match {
            case 0 =>
              MilestoneService.addMilestone(milestone)
            case _ =>
              var savedMilestone = MilestoneService.getMilestoneById(id)
              savedMilestone.get.title = milestone.title
              savedMilestone.get.desc = milestone.desc
              savedMilestone.get.status = milestone.status
              savedMilestone.get.cost = milestone.cost
              savedMilestone.get.estimated_completion_date = milestone.estimated_completion_date
              savedMilestone.get.actual_completion_date = milestone.actual_completion_date
              savedMilestone.get.updated_at = new Date(System.currentTimeMillis())
              MilestoneService.editMilestone(savedMilestone.get)
          }

          Redirect("/editProject/"+milestone.projectId)

        }
      )
    /*implicit val formats = Serialization.formats(NoTypeHints) + new EnumerationSerializer(Status)

    val project = net.liftweb.json.parse(request.body.asJson.get.toString()).extract[ProjectMaster]

    val insertedProjectId: Int = ProjectsService.addProjects(project)
     Ok(Json.parse(Serialization.write(insertedProjectId+"")))*/
  }
   def getMilestone(id :Int,projectId :Int) = Action{
    val milestone = MilestoneService.getMilestoneById(id)
     implicit request : Request[Any] => val userId = request.session.get("userId")
       val user  = UserService.getUserById((userId.get).toInt)
     Ok(views.html.edit_milestone(milestone,projectId,user,"PROJECTS"))

    }
     def getMilestoneReadOnly(id :Int,projectId :Int) = Action{
       val milestone = MilestoneService.getMilestoneById(id)
       implicit request : Request[Any] => val userId = request.session.get("userId")
       val user  = UserService.getUserById((userId.get).toInt)
       Ok(views.html.edit_milestone_readonly(milestone,projectId,user,"PROJECTS"))

  }
  def newMilestone(id : Int) = Action{
    implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.create_milestone(milestoneForm,id,user,"PROJECTS"))

  }
  def errors() = Action{
    Ok(views.html.errors(None))

  }
}
