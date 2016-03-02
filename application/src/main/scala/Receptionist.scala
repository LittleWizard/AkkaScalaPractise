import akka.actor.ActorLogging
import akka.util.Timeout


import scala.concurrent.duration._
import scala.concurrent.{Future, ExecutionContext}

import spray.routing.{Route, HttpService, HttpServiceActor}
import spray.httpx.SprayJsonSupport._

class Receptionist extends HttpServiceActor
                      with ReverseRoute with ActorLogging {
  implicit def executeContext = context.dispatcher
  def receive: Receive = runRoute(reverseRoute)
}

trait ReverseRoute extends HttpService {
  //we need this so we can use Future and Timeout

  implicit def executeContext: ExecutionContext

  def reverseRoute: Route = path("reverse") {
    post {
      entity(as[ReverseRequest]) { request =>
        implicit val timeout = Timeout(20 seconds)
        import akka.pattern.ask

        //replace the next line by asking the actor to Reverse
        //and converting (hint: mapping) the resulting Future[ReverseResult] to a Future[ReverseResponse]
        val futureResponse = Future.successful(ReverseResponse(request.value.reverse))
        complete(futureResponse)
      }
    }
  }
}
