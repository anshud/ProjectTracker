package controllers

import play.mvc.Controller
import play.api.data.Form
import model.projectMaster.{MiscCost, MiscCostList}
import play.api.data.Forms._
import java.sql.Date
import play.api.mvc.{Request, Action}
import play.api.mvc.Results._
import model.projectMaster.MiscCostList
import model.projectMaster.MiscCost
import scala.Some
import service.projects.{UserService, AllocationStatusService, ResourceService, ProjectsService}
import common.DateUtils

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
object MiscCostController extends Controller{

  val miscCostForm: Form[MiscCostList] = Form(
    mapping(
      "miscCostList" -> list(mapping(
        "costTitle" -> text,
        "costValue" -> bigDecimal
      )
        (apply = (costTitle,costValue)   => MiscCost(0, 0, costTitle, costValue, new Date(System.currentTimeMillis()),"System", new Date(System.currentTimeMillis()),"System",new Date(System.currentTimeMillis())))
        (unapply = miscCost => Some(miscCost.costTitle, miscCost.costValue))
      )
    )(MiscCostList.apply)(MiscCostList.unapply)
  )

  def saveMiscCost(projectId :Int) = Action{

    implicit request =>
      miscCostForm.bindFromRequest().fold(
      errors => BadRequest(views.html.errors(Some(miscCostForm.bindFromRequest().errorsAsJson))),
      miscCostList =>  {

        val resourceAllocationList = ProjectsService.getAllMiscCosts(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          if(!resourceAllocationList.isEmpty ){
            ProjectsService.deleteMiscCostProjectId(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          }
        // update cost
          for(i <- 0 until miscCostList.miscCostList.size){
            miscCostList.miscCostList(i).projectId = projectId
            ProjectsService.addMiscCost(miscCostList.miscCostList(i))
          }

          Redirect("/projectList")

        }
      )

  }
  def getAllMiscCostList(projectId :Int) = Action {

    val list = ProjectsService.getAllMiscCosts(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
    val project = ProjectsService.getProjectById(projectId)
    var startInd = 0
    var lastInd = 5
    if(list != None && list.get.size > 0){
      startInd = list.get.size
      lastInd = startInd
    }
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.miscCost_screen(miscCostForm,list,project,startInd,lastInd,user,"PROJECTS"))
    }


}
