package actor
import akka.actor.Actor

class HelloActor extends Actor{
  def receive = {
    case "hello" => println("hello back at you")
    case "stop" => context.stop(self) // stopping this actor
    case _ => println("huh?")
  }
}

