package messaging.app

import akka.actor.{Address, Props, ActorSystem}
import akka.cluster.Cluster
import akka.cluster.routing.{ClusterRouterPoolSettings, ClusterRouterPool}
import akka.io.IO
import akka.routing.BroadcastPool
import com.typesafe.config.ConfigFactory
import spray.can.Http
import spray.can.Http.Bind

import scala.collection.mutable


object Boot extends App {

  val config = ConfigFactory.load("master")
  var nodes = new mutable.ListBuffer[Address]()
  implicit val system = ActorSystem("messaging-cluster", config)
  println(s"Starting node with roles: ${Cluster(system).selfRoles}")

  Cluster(system).registerOnMemberUp {
    val receiver = system.actorOf(Props[RequestReceiver], "receiver")
    println("Master node is ready.")
    IO(Http) ! Bind(listener = receiver, interface = "0.0.0.0", port = 8080)


  //  system.actorOf(Props(new ClusterEventListener), "cluster-listener")



    system.actorOf(Props(new EventListener), "cluster-listener")


  }

}
