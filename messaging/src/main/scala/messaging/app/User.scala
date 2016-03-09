package messaging.app

import akka.actor.{Props, ActorRef, ActorLogging, Actor}

object MessagingUser {

  def props(messagingUser: ActorRef): Props =
  Props[MessagingUser]

  case class MessageContent()

}


class MessagingUser extends Actor
              with ActorLogging {

  def receive = {
    case MessageRequest(a,b) => {
      println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>message has been received : " + a + " " + b )
    }
    case _ => println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inside user actor")
  }
}

