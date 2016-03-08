package messaging.app

import akka.actor.{ActorLogging, Actor}

class User extends Actor
              with ActorLogging {

  def receive = {
    case MessageRequest(a,b) => {
      println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>message has been received : " + a + " " + b )
    }
    case _ => println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inside user actor")
  }
}

