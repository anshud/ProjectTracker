package service.projects

import scala.slick.driver.JdbcProfile
import scala.slick.jdbc.JdbcBackend.{Database, Session}
import slick.component.DBContext

/**
 * Created with IntelliJ IDEA.
 * User: sameer
 * Date: 22/7/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
trait BaseService  extends DBContext{

  def transactional[T](block: => T): T = {
    db.withSession {
      implicit session:Session =>
      try {
        block
      } catch {
        case e: Throwable => {
          throw new IllegalStateException("Exception Occurred , Rolling Back Transaction !!", e)
        }
      }
    }
  }

}
