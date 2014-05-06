package controllers

import play.mvc.Controller
import play.api.data.Form
import model.projectMaster.ProjectMaster
import play.api.data.Forms._
import model.projectMaster.ProjectMaster
import scala.Some
import common.DateUtils
import java.sql.Date
import model.resources.ResourceMaster
import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Forms._
import play.api.data.Forms.{mapping, text}
import play.api.data.{validation, Form}
import service.projects.{UserService, MilestoneService, ResourceService, ProjectsService}

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/14/14
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
object ResourceController extends Controller{

  val resourceForm: Form[ResourceMaster] = Form(

    mapping = mapping(
      "fName" -> text(minLength = 4),
      "mName" -> text,
      "lName" -> text,
      "emailId" -> email,
      "skills" -> text,
      "yrExp" -> number,
      "mnthExp" -> number,
      "cost" -> bigDecimal,
      "status" -> number
    )(
      apply = (fName, mName, lName, emailId, skills ,yrExp , mnthExp ,cost , status ) => ResourceMaster(0, fName, Some(mName), lName, emailId, skills, yrExp , mnthExp, cost, status, "System",new Date(System.currentTimeMillis()), "System", new Date(System.currentTimeMillis()) )
    )(
      unapply = resourceMaster => Some(resourceMaster.firstName, resourceMaster.middleName.get, resourceMaster.lastName, resourceMaster.emailId, resourceMaster.skills, resourceMaster.yearExperience, resourceMaster.monthExperience,resourceMaster.cost,resourceMaster.status)
    )
  )

   def newResource() = Action {
     implicit request : Request[Any] => val userId = request.session.get("userId")
       val user  = UserService.getUserById((userId.get).toInt)
     Ok(views.html.create_resource(resourceForm,user,"RESOURCE"))
   }/* implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)*/
  def getAllResources() = Action{
    val resourceList = ResourceService.getAllResources()
      implicit request : Request[Any] => val userId = request.session.get("userId")
        val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.resourceList(resourceList,user,"RESOURCE"))

  }
  def saveResource(id :Int) = Action{

    implicit request =>
      resourceForm.bindFromRequest().fold(
        errors => BadRequest(views.html.errors(Some(resourceForm.bindFromRequest().errorsAsJson))),
        resourceMaster =>  {
          id match {
            case 0 =>  {
             // var project = ProjectsService.getProjectByTitle(projectMaster.title)


              ResourceService.addResource(resourceMaster)
              /* }
               case _ => {
                    var errorMsg : String = projectMaster.title +" already exists."c
               }*/
            }

            case _ =>  {
              var resource = ResourceService.getResourceById(id)
              resource.get.firstName = resourceMaster.firstName
              resource.get.middleName = resourceMaster.middleName
              resource.get.lastName = resourceMaster.lastName
              resource.get.emailId = resourceMaster.emailId
              resource.get.skills = resourceMaster.skills
              resource.get.yearExperience = resourceMaster.yearExperience
              resource.get.monthExperience = resourceMaster.monthExperience
              resource.get.cost = resourceMaster.cost
              resource.get.status = resourceMaster.status
              resource.get.updated_at = new Date(System.currentTimeMillis())
              ResourceService.editResource(resource.get)
            }
          }
          Redirect("/resourceList")

        }
      )

  }
  def getResource(id : Int) = Action{
    val resourceMaster = ResourceService.getResourceById(id)
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.edit_resource(resourceMaster,user,"RESOURCE"))
  }
  def getResourceReadOnly(id :Int) = Action{
    val resourceMaster = ResourceService.getResourceById(id)
    implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.edit_resource_readonly(resourceMaster,user,"RESOURCE"))

  }
}
