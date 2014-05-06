package controllers

import play.mvc.Controller
import play.api.data.Forms._
import play.api.data.Forms.{mapping, text}
import play.api.data.{Form}
import model.projectMaster._
import java.sql.Date
import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Forms._
import service.projects.{UserService, AllocationStatusService, ProjectsService, ResourceService}
import model.projectMaster.ResourceAllocation
import model.projectMaster.ResourceAllocations
import scala.Some
import java.util.Calendar
import common.DateUtils

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/15/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */

object ResourceAllocationController extends Controller{
  val allocationForm: Form[ResourceAllocations] = Form(
    mapping(
      "allocations" -> list(mapping(
        "resource" -> number,
        "expectedPercent" -> number(min=1,max=100),
        "actualPercent" -> number(min=1,max=100),
        "cost" -> bigDecimal
      )
        (apply = (resource,expectedPercent,actualPercent,cost)   => ResourceAllocation(0, 0, resource, expectedPercent,Some(actualPercent), new Date(System.currentTimeMillis()),cost,"System", new Date(System.currentTimeMillis()),"System",new Date(System.currentTimeMillis())))
        (unapply = resourceAllocation => Some(resourceAllocation.resource, resourceAllocation.expectedPercent, resourceAllocation.actualPercent.get,resourceAllocation.cost))
      )
    )(ResourceAllocations.apply)(ResourceAllocations.unapply)
  )

  def allocationScreen(projectId :Int) = Action {

    val resourceList = ResourceService.getAllResources()
    val project = ProjectsService.getProjectById(projectId)

    val strMonth = DateUtils.getStrMonth(Calendar.getInstance().getTime.getMonth() +1)
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    Ok(views.html.allocation_screen(allocationForm,resourceList,project,None,0,10,user,"PROJECTS"))
  }
  def saveAllocation(projectId :Int)  = Action {
    implicit request =>
    val bodyy=  request.body
      allocationForm.bindFromRequest().fold(
        errors => BadRequest(views.html.errors(Some(allocationForm.bindFromRequest().errorsAsJson))),
        allocations =>  {
          val resourceAllocationList = ProjectsService.getAllResourceAllocations(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          if(!resourceAllocationList.isEmpty ){
            ProjectsService.deleteAllocationsByProjectId(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          }
          for(i <- 0 until allocations.allocations.size){
               allocations.allocations(i).projectId = projectId
               ProjectsService.allocateResource(allocations.allocations(i))
          }
          val statusEntity = AllocationStatusService.getAllocationStatusByProjectId(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          if(statusEntity != None){
            AllocationStatusService.deleteStatusEntity(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
          }
          val selectedButton = allocationForm.bindFromRequest().data.get("clickedButton")
          val allocationStatus : AllocationStatus = AllocationStatus.apply(0,projectId,new Date(System.currentTimeMillis()),selectedButton.get,"System", new Date(System.currentTimeMillis()),"System",new Date(System.currentTimeMillis()))
          AllocationStatusService.addAllocationStatus(allocationStatus)
          Redirect("/projectList")

      }
      )
  }
  def getAllResourceAllocations(projectId :Int) = Action {
    val list = ProjectsService.getAllResourceAllocations(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
    //val allocations : ResourceAllocations = ResourceAllocations(list)
    val resourceList = ResourceService.getAllResources()
    val project = ProjectsService.getProjectById(projectId)
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    var startInd = 0
    var lastInd = 10
    if(list != None && list.get.size > 0){
       startInd = list.get.size
       lastInd = startInd
    }
    val statusEntity = AllocationStatusService.getAllocationStatusByProjectId(projectId,DateUtils.getDateRange()._1,DateUtils.getDateRange()._2)
    if(statusEntity != None){
      statusEntity.get.status match {
        case "Save_In_Draft" =>{
          Ok(views.html.allocation_screen(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))
        }
        case "Estimation_Done" =>{
          Ok(views.html.allocation_screen_estimation(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))
        }
        case "Completed" =>{
          Ok(views.html.allocation_screen_complete(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))
        }
        case _ => {
          Ok(views.html.allocation_screen(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))
        }
      }
    }
      else{
        Ok(views.html.allocation_screen(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))
      }

  }
  def getMonthAllocations(projectId :Int,period : String) = Action {

    val list = ProjectsService.getAllResourceAllocations(projectId,DateUtils.getDateRange(period)._1,DateUtils.getDateRange(period)._2)
    val resourceList = ResourceService.getAllResources()
    val project = ProjectsService.getProjectById(projectId)
    implicit request : Request[Any] => val userId = request.session.get("userId")
      val user  = UserService.getUserById((userId.get).toInt)
    var startInd = 0
    var lastInd = 10
    if(list != None && list.get.size > 0){
      startInd = list.get.size
      lastInd = startInd
    }
  Ok(views.html.allocation_screen_complete(allocationForm,resourceList,project,list,startInd,lastInd,user,"PROJECTS"))

  }

}
