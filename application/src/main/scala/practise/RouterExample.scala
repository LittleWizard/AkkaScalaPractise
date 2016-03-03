package practise

import akka.actor.{ActorSystem, ActorRef, ActorLogging, Actor}
import practise.RouterExample.Work

object RouterExample {

  case class Work(text: String)

  case class Terminated(index: ActorRef)


}


class Worker(num: Int) extends Actor with ActorLogging {

  val state = num

  def receive = {
    case Work(value) => {
      log.info("message received in : {}", num)
      sender ! "sending response message"
    }
  }

}


import akka.actor.Actor
import akka.actor.Props
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }
import RouterExample._

class Master extends Actor {
  var router = {
    var index = 1;
    val routers = Vector.fill(5) {
      val r = context.actorOf(Props(classOf[Worker], index))
      context watch r
      index = index + 1
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routers)
  }

  def receive = {
    case w: Work =>
      router.route(w, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Worker])
      context watch r
      router = router.addRoutee(r)
  }
}



object Example extends App {

  val sys = ActorSystem("Routing")

  val master = sys.actorOf(Props[Master])

  master ! Work("foo")
  master ! Work("foo")
  master ! Work("foo")
  master ! Work("foo")
  master ! Work("foo")
  master ! Work("foo")


}
