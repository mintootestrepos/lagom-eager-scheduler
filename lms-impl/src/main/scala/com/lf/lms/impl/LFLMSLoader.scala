package com.lf.lms.impl

import com.lf.lms.api.{LFLMSService, LMSService}
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.ReadSidePersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.loanframe.lfdb.connection.PSQLComponent
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class LFAppLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication = new LFApp(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = new LFApp(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LFLMSService])
}

abstract class LFApp(context: LagomApplicationContext) extends LagomApplication(context) with ReadSidePersistenceComponents with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LFLMSService](wire[LFLMSServiceImpl])


  //Bind the external service in ServiceModule.
  lazy val externalService = serviceClient.implement[LMSService]

  wire[LFScheduler]

}
