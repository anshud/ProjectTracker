package slick.component

import scala.slick.lifted.{TableQuery, Tag}
import scala.slick.driver.MySQLDriver.simple._
import model.resources.ResourceMaster
import model.user.UserMaster

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/23/14
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
object UserMasterComponent {
    this: Profile =>

  class Users (tag : Tag) extends Table[UserMaster](tag ,"users") {

    def userId = column [Int] ("id",O.PrimaryKey,O.AutoInc)

    def firstName = column[String] ("first_name",O.NotNull)

    def middleName = column[String] ("middle_name",O.Nullable)

    def lastName = column[String] ("last_name",O.NotNull)

    def emailId = column[String] ("email_id",O.NotNull)

    def userName = column[String]("userName",O.NotNull)

    def password = column[String]("password", O.NotNull)

   def * = (userId ,firstName, middleName, lastName, emailId, userName , password) <> (UserMaster.tupled, UserMaster.unapply _)
  }
  val users = TableQuery[Users]

  def checkUser(userName : String,password : String)(implicit session : Session) = {

    val query = for {
      user <- users if (user.userName === userName && user.password === password)
    }  yield(user.userId ,user.firstName, user.middleName, user.lastName, user.emailId, user.userName, user.password)
    val userList = query.list
    if(userList.isEmpty) None
    else
      Some(UserMaster(userList.head._1, userList.head._2, userList.head._3, userList.head._4, userList.head._5, userList.head._6, userList.head._7))
  }
  def getUserById(userId : Int)(implicit session : Session) = {

    val query = for {
      user <- users if (user.userId === userId)
    }  yield(user.userId ,user.firstName, user.middleName, user.lastName, user.emailId, user.userName, user.password)
    val userList = query.list
    if(userList.isEmpty) None
    else
      Some(UserMaster(userList.head._1, userList.head._2, userList.head._3, userList.head._4, userList.head._5, userList.head._6, userList.head._7))
  }
}
