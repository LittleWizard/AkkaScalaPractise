import akka.actor.{Actor, Props, ActorSystem}

object LocalApp extends App{
  val system = ActorSystem("LocalActorSystem")
  val localActor = system.actorOf(Props[LocalActor])
  localActor ! "START"
}

class LocalActor extends Actor {
  val remote = context.actorSelection("akka.tcp://RemoteActorSystem@127.0.0.1:2552/user/RemoteActor")
  var counter = 0;
  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
    case msg: String =>
      println(s"LocalActor has received message : '$msg'")
      if(counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
      else {
        sender ! "TERMINATE"
        context.system.terminate()
      }
  }
}