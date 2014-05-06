package model.company

import java.sql.Date

/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
case class CompanyMaster (
       companyId :Int,
       companyName : String,
       website : String,
       contactPhone : String,
       address : String,
       created_by : String,
       created_at : Date,
       updated_by : String,
       updated_at : Date

)
