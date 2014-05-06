package dao.projects

import dao.projects.common.BaseDao
import scala.slick.driver.MySQLDriver.simple._
import slick.component.UserMasterComponent
import model.user.UserMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/23/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
object UserDao extends BaseDao{
  def checkUser(userName : String , password : String): Option[UserMaster] = {
    db.withSession {
      implicit session :Session =>
      UserMasterComponent.checkUser(userName,password)
    }
  }
  def getUserById(userId :Int):Option[UserMaster] = {
    db.withSession {
      implicit session :Session =>
        UserMasterComponent.getUserById(userId)
    }
  }
}
