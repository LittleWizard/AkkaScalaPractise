package practise

import akka.actor.{ActorLogging, Actor}


case class Message(value: String)

class Receiver(timeout: Long) extends Actor with ActorLogging {

  def this() = this(1000)

  override def receive = fastReceive

  def fastReceive: Receive = {
    case Message(m)=> {
      log.info(m)

      context.become(slowReceive)
    }
  }

  def slowReceive: Receive = {
    case Message(m) => {
      Thread.sleep(timeout)

      log.info(s"Slow: $m")

      context.become(fastReceive)
    }
  }
}