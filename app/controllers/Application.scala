package controllers

import play.api._
import play.api.mvc._
import service.projects.ProjectsService
import play.api.mvc.Results._

object Application extends Controller {

  def index = Action {
    /*val projectList = ProjectsService.getAllProjects()
    Ok(views.html.projectsList(projectList))*/
    Ok(views.html.login(""))
  }

}