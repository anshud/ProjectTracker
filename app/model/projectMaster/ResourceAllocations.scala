package model.projectMaster

import model.projectMaster.{ProjectResource, ResourceAllocation}
import model.resources.{ResourceMaster}
import model.resources.ResourceMaster
import java.sql.Date
import play.api.data.Mapping

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/15/14
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
case class ResourceAllocations (
         var allocations : List[ResourceAllocation]

 )
