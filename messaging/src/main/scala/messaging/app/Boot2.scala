package messaging.app

import akka.actor.{ActorSystem, Props}


object Boot2 extends App {

 // val config = ConfigFactory.load("master")
  val system = ActorSystem("messaging")
 // println(s"Starting node with roles: ${Cluster(system).selfRoles}")


  val master = system.actorOf(Props[Master], "first-master")

  master ! MessageRequest(userId = "454545", value = "message")

  println("...........................MMMMMMMMMMMMMMMMMMMMMMMMMMMM")
 /* Cluster(system).registerOnMemberUp {
 //   val receiver = system.actorOf(Props[RequestReceiver], "receiver")
    println("Master node is ready.")
  //  IO(Http) ! Bind(listener = receiver, interface = "0.0.0.0", port = 8080)

   // actor ! Message(userId = "23232323", value = "dadadada")


    val master = system.actorOf(Props[Master], "first-master")

    master ! Message(userId = "454545", value = "message")

    val actor = system.actorOf(Props(new ClusterEventListener), "cluster-listener")
    actor ! "message"

  }*/

}
