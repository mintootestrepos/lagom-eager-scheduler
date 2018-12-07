package com.lf.lms.impl

import com.lf.lms.api.LFLMSService
import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class LFLMSServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  val client = server.serviceClient.implement[LFLMSService]
  private val server = ServiceTest.startServer(ServiceTest.defaultSetup
    .withCassandra(true)) { ctx =>
    new LFApp(ctx) with LocalServiceLocator
  }

  override protected def afterAll() = server.stop()

  "LFLMS service" should {

    "should greet with custom message" in {
      client.checkHealth.invoke().map(res => assert(res.contains("200")))
    }
  }
}
