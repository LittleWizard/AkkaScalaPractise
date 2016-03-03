import akka.actor.{Props, ActorSystem}
import akka.io.IO
import persistance.{Cmd, ExamplePersistentActor}
import spray.can.Http
import spray.can.Http.Bind

object Main extends App {

  implicit val system = ActorSystem("my-app-api")

  import Bootstrap._

  Bootstrap.print

  val receptionist = system.actorOf(Props[Receptionist], "receptionist")

  IO(Http) ! Bind(listener = receptionist, interface = "0.0.0.0", port = 8080)

}
