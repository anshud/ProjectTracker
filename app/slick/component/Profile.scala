package slick.component

import scala.slick
import scala.slick.driver.{MySQLDriver, JdbcProfile}
import scala.slick.jdbc.JdbcBackend.{Database, Session}

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */


  /**
   * defines method to load the required Database driver.
   */
  trait Profile {

    val profile: JdbcProfile

    protected def load(driver: String) = {

      def newInstance[T](name: String)(implicit m: Manifest[T]): T =
        Class.forName(name+"$").getField("MODULE$").get(m.runtimeClass).asInstanceOf[T]
      newInstance[JdbcProfile](driver)
    }


}
