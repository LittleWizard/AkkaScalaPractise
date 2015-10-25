package actor

import akka.actor.Actor
import protocols.{StopMessage, PongMessage, PingMessage}

class PongActor extends Actor {
 def receive = {
   case PingMessage =>
     println("pong")
     sender ! PongMessage

   case StopMessage =>
     println("pong stopped")
     context.stop(self)
     context.system.terminate()
 }
}
