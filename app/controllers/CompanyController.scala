package controllers

import play.mvc.Controller
import play.api.mvc._
import service.projects.{UserService, MilestoneService, CompanyService}
import play.api.mvc.Results._

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
object CompanyController extends Controller{

  def showCompanyCosts = Action {

    val companyCosts = CompanyService.getMonthlyCostForCompany()
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.companyCosts(companyCosts._1,companyCosts._2,user,"COMPANY"))
  }

}
