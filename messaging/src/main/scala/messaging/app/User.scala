package messaging.app

import akka.actor.{ActorLogging, Actor}

class User extends Actor
              with ActorLogging {

  def receive = {
    case MessageRequest(_,_) => {
      println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>message has been received : " )
    }
    case _ => println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inside user actor")
  }
}

