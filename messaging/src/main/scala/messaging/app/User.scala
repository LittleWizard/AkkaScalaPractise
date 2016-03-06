package messaging.app

import akka.actor.{ActorLogging, Actor}

class User extends Actor
              with ActorLogging {

  def receive = {
    case msg @ Message(_,_) => {
      log.debug("message has been received : " + msg.userId + " " + msg.value)
    }
    case _ => log.info("inside user actor")
  }
}

