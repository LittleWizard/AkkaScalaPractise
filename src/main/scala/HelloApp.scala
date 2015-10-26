import actor.{PingActor, PongActor, AskActor, HelloActor}
import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import protocols.{StartMessage, AskNameMessage}
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.Future
import  example.RandomFirst

object HelloApp extends  App{

  //akka is a platform inspired by Erlang, promising easier
  //development of scalable, multi-threaded and safe applications.
  //while in most of the popular languages concurrency is based on memory
  //shared between several threads, guarded by various synchronization methods, Akka
  //offers concurrency model based on actors.
  //Actor is lightweight object which you can interact with barely by sending messages to it. Each actor
  //can process at most one message at a time and obviously can send messages to other actors
  //Within one java virtual machine millions of actors can exist at the same time, building a hierarchical parent(supervisor) - children structure,
  //where parent monitors the behavior of children.
  //If that's not enough, we can easily split our actors between several nodes in a cluster - without modifying a single line of code.
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

  //random example
  val randomObj = new RandomFirst().execute
  println("random number " + randomObj)






}