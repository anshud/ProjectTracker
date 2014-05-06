package dao.projects.common

import slick.component.DBContext


/**
 * Base Dao trait that holds all the connection related objects and ddl's for all tables.
 */
trait BaseDao extends DBContext



class AcceptanceDao extends BaseDao
