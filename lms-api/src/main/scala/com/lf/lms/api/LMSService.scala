package com.lf.lms.api

import akka.Done
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.lf.lms.models.LMSModels.UserProfile

trait LMSService extends Service {

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("lms-service")
      .withCalls(
        pathCall("/v1/borrower/stepOne", createUserProfile)
      ).withAutoAcl(true)
    // @formatter:on
  }

  def createUserProfile: ServiceCall[UserProfile, Done]
}
