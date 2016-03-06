package messaging.app

import akka.actor.{Props, ActorRef, Actor, ActorLogging}
import akka.cluster.routing.{ClusterRouterPoolSettings, ClusterRouterPool}
import akka.routing.BroadcastPool
import akka.util.Timeout

import scala.concurrent.duration._

import spray.routing.{Route, HttpService, HttpServiceActor}

import scala.concurrent.{Future, ExecutionContext}
import spray.httpx.SprayJsonSupport._

class RequestReceiver extends HttpServiceActor
                         with ReceiverRoute
                         with ActorLogging {

  implicit def executeContext = context.dispatcher

  def receive: Receive = runRoute(sendMessageRoute)

}


trait ReceiverRoute extends HttpService with CreateUserRouter { this: Actor =>

  implicit def executeContext: ExecutionContext

//  /val router = createUserRouter

  def sendMessageRoute: Route = path("sendMessage") {

    post {

      entity(as[Message]) { request =>

      implicit val timeout = Timeout(5 seconds)

      context.


      val response = Future.successful(request)
      complete(response)

      }
    }
  }
}


trait CreateUserRouter { this: Actor =>
  def createUserRouter(actorName: String): ActorRef = {
    context.actorOf(
      ClusterRouterPool(BroadcastPool(10), ClusterRouterPoolSettings(
        totalInstances = 100, maxInstancesPerNode = 100,
        allowLocalRoutees = false, useRole = Some("user"))).props(Props[User]),
      name = actorName)
  }
}
