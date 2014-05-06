package controllers

import play.mvc.Controller
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Action
import play.api.mvc.Results._
import scala.Some
import service.projects.{ProjectsService, UserService}
import model.user.UserMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/23/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
object LoginController extends Controller{
  val loginForm: Form[UserMaster] = Form(

    mapping = mapping(
      "u_userName" -> text,
      "u_password" -> text
    )(
      apply = (userName, password) => UserMaster(0, "","","","",userName, password)
    )(
      unapply = user => Some(user.userName, user.password)
    )
  )

  def authenticateUser = Action{

    implicit request =>
     loginForm.bindFromRequest().fold(
       errors => BadRequest(views.html.errors(Some(loginForm.bindFromRequest().errorsAsJson))),
       user => {
        val savedUser = UserService.checkUser(user.userName,user.password)
        if(savedUser != None){
           val projectList = ProjectsService.getAllProjects()
           Ok(views.html.projectsList(projectList,savedUser,"PROJECTS")).withSession("userfullname" -> (savedUser.get.firstName + ""+ savedUser.get.lastName),
           "userId" -> (savedUser.get.id).toString(),"userEmail" -> savedUser.get.emailId )
          }
         else{
          val msg : String = "Invalid UserName/Password"
          Ok(views.html.login(msg))
        }
       }
     )
  }

  def loginScreen = Action {

    Ok(views.html.login(""))
  }
  def logout = Action {
    Ok(views.html.login("")).withNewSession
  }
}
