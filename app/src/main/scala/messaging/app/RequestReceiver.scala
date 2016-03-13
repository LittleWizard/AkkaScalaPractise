package messaging.app

import akka.actor._
import akka.cluster.sharding.ClusterSharding
import akka.util.Timeout
import messaging.app.ClientProtocol._
import messaging.app.User.MessageContent
import scala.concurrent.duration._
import spray.routing.{Route, HttpService, HttpServiceActor}
import scala.concurrent.{Future, ExecutionContext}
import spray.httpx.SprayJsonSupport._
import akka.pattern.ask


class RequestReceiver extends HttpServiceActor
                         with ReceiverRoute
                         with ActorLogging {
  implicit def executeContext = context.dispatcher

  def receive: Receive = runRoute(sendMessageRoute ~ getMessagesRoute)
}

trait ReceiverRoute extends HttpService {
  this: Actor with ActorLogging =>
  implicit def executeContext: ExecutionContext

  val userRegion = ClusterSharding(context.system).shardRegion(User.shardName)

  def sendMessageRoute: Route = path("sendMessage") {
    post {
      entity(as[Message]) { request =>
        implicit val timeout = Timeout(1 seconds)
        val response: Future[SendMessageResponse] = ask(userRegion, User.AddMessage(userId = request.userId, content = MessageContent(value = request.value)))
          .map { value =>
            SendMessageResponse(success = true, Data = value.toString)
          }
       // val response = Future.successful(request)
        complete(response)
      }
    }
  }


   def getMessagesRoute: Route = (get & path("getMessages" / RestPath)) { id =>
     implicit val timeout = Timeout(5 seconds)

     println("serving for user id : " + id.toString)

     val response  = ask(userRegion, User.GetInboxMessage(userId = id.toString()))
     .map { response =>
       GetMessageResponse(success = true, Data = response.asInstanceOf[List[MessageContent]].map(b => Message(userId = id.toString(), b.value)))
     }

     complete(response)
   }


}
