package model.projectMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
object Status extends Enumeration {
  type Status = Value
  val ACTIVE, COMPLETED, INACTIVE, PROPOSED = Value
}
