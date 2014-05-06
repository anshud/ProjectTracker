package slick.component

import com.typesafe.config.ConfigFactory

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/4/14
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
object ApplicationConfig {

    val config = ConfigFactory.load

    def get(s: String) = {
      config.getString(s)
    }


}
