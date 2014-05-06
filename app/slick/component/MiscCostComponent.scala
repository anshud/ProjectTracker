package slick.component

import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date
import scala.slick.lifted.{TableQuery, Tag}
import model.projectMaster.MiscCost

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
object MiscCostComponent {
   this :Profile =>

  class MiscCosts(tag : Tag) extends Table[MiscCost](tag , "misc_cost") {

      def miscCostId = column[Int]("id",O.PrimaryKey,O.NotNull,O.AutoInc)

      def projectId = column[Int]("project_id",O.NotNull)

      def costTitle = column[String]("cost_title",O.NotNull)

      def costValue = column[BigDecimal]("cost_value", O.Nullable)

      def startDate = column[Date]("start_date",O.Nullable)

      def createdBy = column[String]("created_by",O.NotNull,O.Default("Anshu"))

      def createdDate = column[Date]("created_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

      def updatedBy = column[String]("updated_by",O.NotNull,O.Default("Anshu"))

      def updatedDate = column[Date]("updated_at",O.NotNull,O.Default(new Date(System.currentTimeMillis())))

      def * = (miscCostId, projectId , costTitle , costValue , startDate , createdBy , createdDate , updatedBy , updatedDate) <> (MiscCost.tupled, MiscCost.unapply _)
    }
    val miscCosts = TableQuery[MiscCosts]


    def addMiscCost(miscCostEntity : MiscCost)(implicit session : Session) = {

      miscCosts.insert(miscCostEntity)

    }
    def getAllMiscCosts(projectId :Int,firstDate :Date,lastDate :Date)(implicit session : Session): Option[List[MiscCost]] = {
      val query = for {
        cost <- miscCosts  if {cost.projectId === projectId &&
        (cost.startDate >= firstDate &&  cost.startDate <= lastDate)}
      }yield(cost.miscCostId ,cost.projectId, cost.costTitle, cost.costValue , cost.startDate,  cost.createdBy, cost.createdDate, cost.updatedBy, cost.updatedDate)

      val costList = query.list

      if(costList.isEmpty) None
      else
        Some(costList.map(x => new MiscCost(x._1, x._2, x._3, x._4, x._5,x._6,x._7, x._8, x._9)))

    }

    def deleteMiscCostProjectId(projectId :Int,firstDate :Date,lastDate :Date)(implicit session :Session) = {

      (miscCosts.filter( x => (x.projectId === projectId && x.startDate >= firstDate &&  x.startDate <= lastDate))).delete
    }


}
