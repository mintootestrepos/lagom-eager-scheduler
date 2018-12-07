package com.lf.lms.impl

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.lf.lms.api.LMSService
import com.lf.lms.models.LMSModels.UserProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class LFScheduler(lmsService: LMSService)(implicit ec: ExecutionContext) {
  val borrowerProfile = UserProfile("John", "", "Becker", "+919686880498", "john.becker+29c0e4eec5614ef4a85d20320497c06a@gmail.com",
    "29c0e4eec5614ef4a85d20320497c06a", "29c0e4eec5614ef4a85d20320497c06a")

  val concurrency = Runtime.getRuntime.availableProcessors() * 10

  implicit val timeout: Timeout = 3.minute
  implicit val system: ActorSystem = ActorSystem("LMSActorSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // def fetchBorrowerProfile(id: String): Option[Borrowers] = borrowersTable.findOneById(id)

  def hitLMSAPI = {
    //1. Consume from SQS
    //2. Match Message
    //3. fetch data from DB
    lmsService.createBorrowerProfile.invoke(borrowerProfile).map(res => println(s">>>>>>>>>>>>>>>>>>>>>>: ${res}"))
  }

  system.scheduler.schedule(2.seconds, 2.seconds)(hitLMSAPI)

}