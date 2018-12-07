package com.lf.lms.impl

import com.lf.lms.api.LFLMSService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.ResponseHeader
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Future

class LFLMSServiceImpl extends LFLMSService {

  private final val log: Logger = LoggerFactory.getLogger(classOf[LFLMSServiceImpl])

  override def checkHealth = ServiceCall { _ =>
    println("The LMS integrator is up and running...")
    Future.successful(ResponseHeader.Ok.toString)
  }
}
