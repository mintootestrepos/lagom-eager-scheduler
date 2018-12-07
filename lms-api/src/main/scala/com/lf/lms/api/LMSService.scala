package com.lf.lms.api

import akka.Done
import com.lf.lms.models.LMSModels.UserProfile
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait LMSService extends Service {

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("lms-service")
      .withCalls(
        restCall(Method.POST, "/v1/borrower/stepOne", createBorrowerProfile)
      ).withAutoAcl(true)
    // @formatter:on
  }

  def createBorrowerProfile: ServiceCall[UserProfile, Done]
}
