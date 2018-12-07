package com.lf.lms.impl

import akka.actor.{Actor, Props}
import com.lf.lms.impl.LMSActor.LMSActorInvoke

class LMSActor extends Actor {
  override def receive: Receive = {
    case LMSActorInvoke => println("Proof of Invokation!")
  }
}

object LMSActor {
  def props: Props = Props(new LMSActor)

  case object LMSActorInvoke

}

