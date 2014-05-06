package slick.component


import scala.slick.jdbc.JdbcBackend.{Database, Session}
import scala.slick.driver.JdbcProfile

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
trait DBContext extends Profile{
  lazy val url = ApplicationConfig.get("db.default.url")

  lazy val driver = ApplicationConfig.get("db.default.profile.driver")

  lazy val profile = load(driver)

  val database = Database.forURL(url, driver = driver)
  def db = database

}

