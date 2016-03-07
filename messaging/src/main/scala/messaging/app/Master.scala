package messaging.app

import akka.actor._
import akka.cluster.routing.{ClusterRouterPoolSettings, ClusterRouterPool}
import akka.routing.BroadcastPool



class Master extends Actor with ActorLogging with  CreateWorkerRouter{

  val router = createWorkerRouter

  override def supervisorStrategy: SupervisorStrategy =
    SupervisorStrategy.stoppingStrategy


  def receive = {

    case MessageRequest(userId, value) => {

      println("...........................")


      log.debug("Ohhhh yaaa >>>>>>>>>>>>>>>>>>>")
      router ! MessageRequest(userId = "12", value = "hello")
    }

    case _ => {
      println("...........................>>>>>")
      log.debug("Ohhhh nooooooo >>>>>>>>>>>>>>>>>>>")
    }

  }

}

trait CreateWorkerRouter { this: Actor =>
  def createWorkerRouter: ActorRef = {
    context.actorOf(
      ClusterRouterPool(BroadcastPool(10), ClusterRouterPoolSettings(
        totalInstances = 100, maxInstancesPerNode = 20,
        allowLocalRoutees = false, useRole = Some("worker"))).props(Props[User]),
      name = "user-router")
  }
}
