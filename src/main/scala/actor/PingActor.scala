package actor

import akka.actor.{ActorRef, Actor}
import protocols.{StopMessage, PongMessage, PingMessage, StartMessage}


class PingActor(pong: ActorRef) extends Actor {
  var count  = 0
  def incrementAndPrint: Unit = {
    count += 1
    println("ping")
  }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint
      if(count > 99){
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      }
      else {
        sender ! PingMessage
      }
  }
}
