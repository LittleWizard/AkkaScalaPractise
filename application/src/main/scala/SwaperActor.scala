import akka.actor.{ActorLogging, Actor}


class SwaperActor extends Actor with ActorLogging{

  import context._

  def angry: Receive = {
    case "foo" => {
      log.info("message received in angry......")
      become(happy, discardOld = false)
      sender() ! "I am already angry?"
    }
  }

  def happy: Receive = {
    case "foo" => {
      log.info("message received in happy......")
      become(sweet, discardOld = false)
      sender() ! "I am already happy :-)"
    }
  }

  def sweet: Receive = {
    case "foo" => {
      log.info("message received in sweet......")
      unbecome()
      sender() ! "I am already sweet :-)"

    }
  }

  def receive = {
    case "foo" => {
      log.info("message received in default......")
      become(angry,  discardOld = false)
    }
  }

}

