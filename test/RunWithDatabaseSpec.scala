import com.typesafe.config.{ ConfigFactory, Config }
import net.liftweb.json.{ NoTypeHints, Serialization }
import org.specs2.execute.AsResult
import org.specs2.mutable.Specification
import play.api.mvc.{ Security, Result, Request }
import dao.projects.common.AcceptanceDao
import play.api.test._
import scala.slick.jdbc.JdbcBackend._



/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 *
 * Date: 4/7/14
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
  /**
   * A trait that allows tests to run with the test database.
   */
  trait RunWithDatabaseSpec extends Specification with PlayRunners{

    val DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z"
    val HMAC_SHA1_ALGORITHM = "HmacSHA1"

    val SECRET = "secret"

    val config: Config = ConfigFactory.load(this.getClass.getClassLoader, "test.conf")

    val settings = Map("db.default.url" -> config.getString("db.default.url"), "db.default.profile.driver" -> config.getString("db.default.profile.driver"), "db.default.driver" -> config.getString("db.default.driver"))
    //val settings = Map("db.default.url" -> "jdbc:h2:mem:jscTest;DB_CLOSE_DELAY=-1;USER=jsc;PASSWORD=jsc1234", "db.default.profile.driver" -> "scala.slick.driver.H2Driver", "db.default.driver" -> "org.h2.Driver")
    //val settings = Map("db.default.url" -> "jdbc:mysql://localhost/jscTest?user=jsc&password=jsc1234", "db.default.profile.driver" -> "scala.slick.driver.MySQLDriver", "db.default.driver" -> "com.mysql.jdbc.Driver")
    /**
     * A test fixture. Place to wrap your calls with need functionality.
     * This provides the fake play application as well as the database session that is to be  used.
     * the block is the code block in the test case that is executed.
     *
     * @param block the code bock from test case that is executed.
     * @tparam T
     */
    def runWithTestDatabase[T](block: => T) {
      running(FakeApplication(additionalConfiguration = settings)) {
        val dao = new AcceptanceDao()
        dao.db.withNestedTransaction {
          try {
            block
          } finally {
            Database.dynamicSession.rollback
          }
        }
      }
    }






  }
