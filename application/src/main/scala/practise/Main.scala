package practise

import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinPool
import akka.util.Timeout


object Main extends App {

  import scala.concurrent.duration._
  val duration = 3.seconds
  implicit val timeout = Timeout(duration)

  val sys = ActorSystem("Routing")

  val single = sys.actorOf(Props[Receiver](new Receiver(2.seconds.toMillis)), "single")
  val router = sys.actorOf(Props[Receiver].withRouter(RoundRobinPool(nrOfInstances = 10)), "router")

  sys.actorSelection("user/single") ! Message("Hello You, by path! [fast]") // fast really?

  // Sending a message by using ActorRef
  single ! Message("Hello You! [slow]") // slow really?
  single ! Message("Hello You! [fast]") // fast really?

  // Sending a message by using Router
  router ! Message("Hello Anybody! [fast]")   // route message to next Receiver actor
//  router ! Broadcast(Message("Hello World! [1xslow, 9xfast]")) // route message to all Receiver actors




}
