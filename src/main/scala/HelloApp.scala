import actor.{AskActor, HelloActor}
import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import protocols.AskNameMessage
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.Future

object HelloApp extends  App{

  println("Hello World")

  val helloSystem = ActorSystem("HelloSystem")

  val helloActor = helloSystem.actorOf(Props[HelloActor], name = "HelloActor")

  helloActor ! "hello"

  helloActor ! "hello world"

  helloActor ! "stop"

  //helloActor ! "after stopping actor"

  Thread.sleep(2000)

  helloSystem.terminate(); // stopping  the actor system



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
  println(anotherFuture)
  askSystem.terminate()

  

}