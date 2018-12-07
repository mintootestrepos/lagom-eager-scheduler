package com.loanframe.lms.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.loanframe.lms.api.{LMSService, LoanFrameLMSService}
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class LoanFrameAppLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication = new LoanFrameApp(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = new LoanFrameApp(context) with LagomDevModeComponents {

  }

  override def describeService = Some(readDescriptor[LoanFrameLMSService])
}

abstract class LoanFrameApp(context: LagomApplicationContext) extends LagomApplication(context) with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LoanFrameLMSService](wire[LoanFrameLMSServiceImpl])

  //Bind the external service in ServiceModule.
  lazy val externalService = serviceClient.implement[LMSService]

  wire[LoanFrameScheduler]

}
