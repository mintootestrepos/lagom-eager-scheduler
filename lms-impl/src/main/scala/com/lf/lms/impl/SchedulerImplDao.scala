package com.loanframe.lms.impl

import com.loanframe.lfdb.contract.Borrowers
import com.loanframe.lfdb.models._


class SchedulerImplDao /* @Inject()(
                                  @NamedDatabase("orders") db: Database,
                                  val controllerComponents: ControllerComponents
                                ) extends BaseController*/ {

  def fetchBorrowerProfile(id: String): Option[Borrowers] = new LoginTable().findOneById(id)
}

object SchedulerImplDao extends SchedulerImplDao