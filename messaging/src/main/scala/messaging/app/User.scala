package messaging.app

import akka.actor.{ActorLogging, Actor}



class User extends Actor
              with ActorLogging {

  def receive = {

    case _ => log.info("inside user actor")

  }

}

