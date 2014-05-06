import model.projectMaster.{Status, ProjectMaster}
import java.util.Calendar
import net.liftweb.json.{NoTypeHints, Serialization}
import org.specs2.execute.AsResult
import org.specs2.matcher.MatchResult
import play.api.libs.json.Json
import common.{EnumerationSerializer}
import service.projects.ProjectsService
import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/7/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
class ProjectsControllerSpec  extends RunWithDatabaseSpec{

   val currentDate:Date=new Date(System.currentTimeMillis())

    "saveProject" should {
      "return the json object of inserted project " in {
        runWithTestDatabase {
          val project = ProjectMaster(1,"abc",Some("sdh"),Status.ACTIVE.id,currentDate,
            currentDate,120,"anshu",currentDate,
            "anshu",currentDate)
          val insertedProjectId = ProjectsService.addProjects(project)
          insertedProjectId must be equalTo 1
        }
      }
    }



  }


