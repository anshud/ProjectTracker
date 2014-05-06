package controllers

import play.mvc.Controller
import play.api.mvc.{Request, Action}
import play.api.mvc.Results._
import model.reports.ReportMaster
import service.projects._
import java.sql.Date
import common.DateUtils


/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/28/14
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
object ReportsController extends Controller{

  def showReport(projectId : Int) = Action {

    implicit request : Request[Any] => val userId = request.session.get("userId")
    val user  = UserService.getUserById((userId.get).toInt)
    val dataMap = ReportMasterService.getReportData(projectId);

   // get estimated cost for all resource for each project for each month
   // get total actual cost  for all resource for each project for each month
   //  get amount received for milestones of all projects for a particular month
    // calculate the profit   ==
    /*Resource Cost for the month for the project + Misc. Costs for the Project for that month + (
      (no. of resources on the project/total number of resources in company active during that month)* fixed Cost)*/
    Ok(views.html.report(dataMap,user,"PROJECTS"))
    //Ok(views.html.login())

  }

}
