package messaging.app

import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster
import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http
import spray.can.Http.Bind


object Boot extends App {

  val config = ConfigFactory.load("master")
  implicit val system = ActorSystem("messaging-cluster", config)
  println(s"Starting node with roles: ${Cluster(system).selfRoles}")

  Cluster(system).registerOnMemberUp {
    val receiver = system.actorOf(Props[RequestReceiver], "receiver")
    println("Master node is ready.")
    IO(Http) ! Bind(listener = receiver, interface = "0.0.0.0", port = 8080)

  }

}
