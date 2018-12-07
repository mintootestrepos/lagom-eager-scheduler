package com.loanframe.lms.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.ResponseHeader
import com.loanframe.lms.api.LoanFrameLMSService
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Future

class LoanFrameLMSServiceImpl extends LoanFrameLMSService {

  private final val log: Logger = LoggerFactory.getLogger(classOf[LoanFrameLMSServiceImpl])

  override def checkHealth = ServiceCall { _ =>
    println("The LMS integrator is up and running...")
    Future.successful(ResponseHeader.Ok.toString)
  }
}
