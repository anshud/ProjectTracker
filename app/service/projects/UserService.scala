package service.projects

import dao.projects.UserDao
import model.user.UserMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/23/14
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
object UserService extends BaseService{

  def checkUser(userName : String , password : String): Option[UserMaster] = transactional {
       UserDao.checkUser(userName : String , password : String)
  }
  def getUserById(userId :Int):Option[UserMaster] = transactional {
    UserDao.getUserById(userId)
  }
}
