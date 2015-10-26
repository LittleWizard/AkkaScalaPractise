import akka.actor.{Actor, Props, ActorSystem}

object RemoteApp extends App{
  val system = ActorSystem("RemoteActorSystem")
  val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
  remoteActor ! "The RemoteActor is alive"
}

class RemoteActor extends Actor{
  def receive = {
    case msg: String => {
      println(s"RemoteActor has received message '$msg'")
      sender ! "Hello from the RemoteActor"
    }
  }
}
