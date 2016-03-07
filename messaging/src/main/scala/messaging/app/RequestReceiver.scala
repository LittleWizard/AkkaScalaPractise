package messaging.app

import akka.actor._
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


trait ReceiverRoute extends HttpService with CreateUserRouter  { this: Actor =>

  implicit def executeContext: ExecutionContext

  var users = Map[String, ActorRef]()


 override def supervisorStrategy: SupervisorStrategy =
  SupervisorStrategy.stoppingStrategy

  def sendMessageRoute: Route = path("sendMessage") {

    post {

      entity(as[Message]) { request =>

      implicit val timeout = Timeout(5 seconds)

        if(users.getOrElse(request.userId, None) == None) {
          println("first user")
          val user = createUserRouter(request.userId)
          users = users + Tuple2(request.userId, user)
          user ! MessageRequest(userId = request.userId, value = request.value)
        }

        else {

          println("user already presents")

          val user = users.get(request.userId).get

          user ! MessageRequest(userId = request.userId, value = request.value)

        }

       /* val user = createUserRouter(request.userId)

        println("actor path >>>>>>>>>>>>>>>>>>>> " + user.path)*/
/*
        val master = context.actorOf(Props[Master], "master")

        master ! MessageRequest(userId = request.userId, value = request.value)*/





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
        totalInstances = 100, maxInstancesPerNode = 20,
        allowLocalRoutees = false, useRole = Some("worker"))).props(Props[User]),
      name = actorName)
  }
}
