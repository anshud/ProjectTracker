package model.projectMaster

import model.resources.ResourceMaster
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/15/14
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
case class ProjectResource (
     var projectId : Int,
     var resource : Int,
     var expectedPercentage : Int,
     var actualPercentage : Int,
     var start_date : Date
)
