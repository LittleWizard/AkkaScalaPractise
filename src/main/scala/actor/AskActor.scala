package actor

import akka.actor.Actor
import protocols.AskNameMessage


class AskActor extends Actor {
 def receive = {
   case AskNameMessage => sender ! "Ohh yaa!!"
   case _ => println("i'm not expecting this")
 }
}
