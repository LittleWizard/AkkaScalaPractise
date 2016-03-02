import ReverseActor.{ReverseResult, Reverse}
import akka.actor.{Props, ActorLogging}
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

        implicit val timeout = Timeout(5 seconds)
        import akka.pattern.ask

        println("http request received " + request.value)

        val result: Future[ReverseResponse] = ask(ReverseActor.getReverseActor, Reverse(value =  request.value))
          .map {

            value => {
              ReverseResponse(value.asInstanceOf[ReverseResult].value)
            }
          }
      //  val futureResponse = Future.successful(ReverseResponse(request.value.reverse))
        complete(result)
      }
    }
  }
}
