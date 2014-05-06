package service.projects

import model.company.CompanyCosts
import dao.projects.CompanyDao


/**
 * Created with IntelliJ IDEA.
 * User: anshu.dhamija
 * Date: 4/25/14
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
object CompanyService extends BaseService{

 def getMonthlyCostForCompany() = transactional {

    CompanyDao.getMonthlyCostsForCompany()

 }


}
