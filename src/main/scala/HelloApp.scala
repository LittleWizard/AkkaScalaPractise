import actor.{PingActor, PongActor, AskActor, HelloActor}
import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import protocols.{StartMessage, AskNameMessage}
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.Future

object HelloApp extends  App{

  //ways of sending messages to an actor

  val helloSystem = ActorSystem("HelloSystem")
  val helloActor = helloSystem.actorOf(Props[HelloActor], name = "HelloActor")
  helloActor ! "hello"
  helloActor ! "hello world"
  helloActor ! "stop"
  Thread.sleep(2000)
  helloSystem.terminate(); // stopping  the actor system

  //ways of asking to an actor
  val askSystem = ActorSystem("AskActorSystem")
  val askActor = askSystem.actorOf(Props[AskActor], "AskActor")
  //one way of asking
  implicit val timeout = Timeout(5 seconds)
  val future = askActor ? AskNameMessage
  val result = Await.result(future, timeout.duration).asInstanceOf[String]
  println(result)
  //another way of asking
  val anotherFuture: Future[String] = ask(askActor, AskNameMessage).mapTo[String]
  val anotherResult = Await.result(anotherFuture, 1 second)
  println(anotherResult)
  askSystem.terminate()

  //ping pong examples

  val pingPongSystem = ActorSystem("PingPongSystem")
  val pongActor = pingPongSystem.actorOf(Props[PongActor], name = "PongActor")
  val pingActor = pingPongSystem.actorOf(Props(new PingActor(pongActor)), name = "PingActor")
  pingActor ! StartMessage

 //

}