import akka.actor._

object ReverseActor {

  case class Reverse(value: String)

  case class ReverseResult(value: String)

  private implicit val system = Main.system

  private val reverse = system.actorOf(Props[ReverseActor], "reverse-actor")

  def  getReverseActor: ActorRef = {
    return reverse
  }

}


class ReverseActor extends Actor with ActorLogging {
  import ReverseActor._
  def receive = {
    case Reverse(value) => {
      log.info("message received {}", value)
      sender ! ReverseResult(value = value.reverse)
    }
  }
}