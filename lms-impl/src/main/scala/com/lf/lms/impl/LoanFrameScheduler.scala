package com.loanframe.lms.impl

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.google.gson.Gson
import com.loanframe.lms.api.LMSService
import com.loanframe.lms.models.LMSModels.{BorrowerProfile, BorrowerProfileBody}
import play.api.Configuration

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

case class LMSMessage(msg: String, message: String)

class LoanFrameScheduler(lmsService: LMSService, configuration: Configuration)(implicit ec: ExecutionContext) {
  val borrowerProfile = BorrowerProfile("John", "", "Becker", "+919686880498", "john.becker+29c0e4eec5614ef4a85d20320497c06a@gmail.com",
    "29c0e4eec5614ef4a85d20320497c06a", "29c0e4eec5614ef4a85d20320497c06a")
  val gson = new Gson()
  val concurrency = Runtime.getRuntime.availableProcessors() * 10

  implicit val timeout: Timeout = 3.minute
  implicit val system: ActorSystem = ActorSystem("LMSActorSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def hitLMSAPI = {
    //1. Consume from SQS
    val sqsMessages = sqsSubsciber.fetchMsgFromSQS().getMessages.asScala

    //2. Match Message
    sqsMessages.map { message =>

      val borrowerProfileBody = gson.fromJson(message.getBody, classOf[BorrowerProfileBody])
      println(borrowerProfileBody)

      //3. fetch data from DB
      message.getAttributes.getOrDefault("key", "value") match {
        case x => println(s">=====>$x")
      }

      val borrower = SchedulerImplDao.fetchBorrowerProfile(borrowerProfileBody.borrowerId)
      println(s"================> $borrower")


      lmsService.createBorrowerProfile.invoke(borrowerProfile).map(res => println(s">>>>>>>>>>>>>>>>>>>>>>: ${res}"))
    }
  }

  def sqsSubsciber = new SQSSubscriber(configuration)

  system.scheduler.schedule(2.seconds, 2.seconds)(hitLMSAPI)

}
