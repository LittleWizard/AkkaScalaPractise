import akka.actor.{Props, ActorSystem}

object Main1 extends App {

  implicit val system = ActorSystem("test-system")

  val actor = system.actorOf(Props[SwaperActor], "actor")

  actor ! "foo"
  actor ! "foo"
  actor ! "foo"
  actor ! "foo"
  actor ! "foo"
  actor ! "foo"

}
