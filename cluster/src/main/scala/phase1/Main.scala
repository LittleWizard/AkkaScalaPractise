package phase1

import akka.actor.{Actor, ActorSystem}
import com.typesafe.config.ConfigFactory


case class test(text: String)

object Main extends App {

  val seedConfig = ConfigFactory.load("seed")


 /* val t = test("Hello World")

  t match {
    case ts @ test(value) => {

      println(value)

      println(ts.text)


    }
  }



  import scala.concurrent.duration._
  import akka.actor.Actor
  import akka.actor.Props
  import scala.concurrent.ExecutionContext.Implicits.global

  case class Message()
  class MyActor extends Actor {
    def receive = { case Message() => println("Do something in actor") }
  }

  val system = ActorSystem("MySystem")
  val actor = system.actorOf(Props(new MyActor), name = "actor")
  system.scheduler.schedule(0 millis, 5000 millis, actor, Message())
*/

  val seedSystem = ActorSystem("words", seedConfig)

}
