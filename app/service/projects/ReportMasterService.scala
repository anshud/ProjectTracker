package service.projects

import model.reports.ReportMaster
import common.DateUtils
import java.sql.Date
import java.text.SimpleDateFormat
import model.resources.ResourceMaster

//import scala.collection.mutable.Map
/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/29/14
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
object ReportMasterService extends BaseService {

  def getReportData(projectId: Int):Map[String,Map[String,ReportMaster]] = {
    val reportMasterMap = ProjectsService.getAllProjectAllocations(projectId);

    var projectMap : Map[String, ReportMaster]= Map()
    var monthMap : Map[String, Map[String, ReportMaster]]= Map()
    reportMasterMap.keys.foreach { key =>
      var totalEstimatedCost: BigDecimal = 0.0
      var totalActualCost: BigDecimal = 0.0
      var totalReceivedCost: BigDecimal = 0.0
      var totalMiscCost: BigDecimal = 0.0
      var totalResourceCost: BigDecimal = 0.0
      var projectResources: Int = 0
      var totalActiveResources: Int = 0
      var delta: BigDecimal = 0.0
      var fixedCost: BigDecimal = 0.0
      val mapValue = reportMasterMap(key)

      val costList = CompanyService.getMonthlyCostForCompany()._2
      var projectName :String =""
      if (costList != None) {
        for (cost <- costList.get)
          fixedCost = cost.fixedCost
      }
      val period : String  =  DateUtils.getMonthFromDate(key) + "-" + DateUtils.getYearFromDate(key)
      val milestones = MilestoneService.getAllMilestonesByDate(projectId, DateUtils.getDateRange(period)._1, DateUtils.getDateRange(period)._2)
      if (milestones != None) {
        for (eachMilestone <- milestones.get) {
          totalReceivedCost = totalReceivedCost + eachMilestone.cost
        }
      }
      val miscCostList = ProjectsService.getAllMiscCosts(projectId, DateUtils.getDateRange(period)._1, DateUtils.getDateRange(period)._2)
      if (miscCostList != None) {
        for (eachMiscCost <- miscCostList.get) {
          totalMiscCost = totalMiscCost + eachMiscCost.costValue
        }
      }
      for (value <- mapValue) {
        val period : String  =  DateUtils.getMonthFromDate(value._7) + "-" + DateUtils.getYearFromDate(value._7)
        val resource: Option[ResourceMaster] = ResourceService.getResourceById(value._3)
        totalEstimatedCost = totalEstimatedCost + ((value._4.toDouble / 100) * resource.get.cost).toDouble
        totalActualCost = totalActualCost + (((value._5.get).toDouble / 100) * resource.get.cost).toDouble


        val projectAllocationList = ProjectsService.getAllResourceAllocations(value._1, DateUtils.getDateRange(period)._1, DateUtils.getDateRange(period)._2)
        val resources = ResourceService.getAllResourcesByStatus(1)
        if (projectAllocationList != None) {
          projectResources = projectAllocationList.get.size

        }
        if (resources != None) {
          totalActiveResources = resources.get.size
        }
        totalResourceCost = totalActualCost.toDouble + totalMiscCost.toDouble + ((projectResources / totalActiveResources)) * fixedCost
        delta = totalReceivedCost - totalResourceCost
        val reportMaster: ReportMaster = ReportMaster(value._1,value._2,value._3,value._4,value._5,value._6,value._7,totalEstimatedCost,totalActualCost,totalReceivedCost,delta)
        projectName = value._2

        projectMap += (period -> reportMaster)
        monthMap += (projectName -> projectMap)
      }

      }
    monthMap
  }

}
