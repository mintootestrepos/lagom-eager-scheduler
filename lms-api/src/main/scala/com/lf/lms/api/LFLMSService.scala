package com.lf.lms.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait LFLMSService extends Service {

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("lf-lms-service")
      .withCalls(
        pathCall("/lf-lms/health", checkHealth)
      )
      .withAutoAcl(true)
    // @formatter:on
  }

  def checkHealth: ServiceCall[NotUsed, String]
}