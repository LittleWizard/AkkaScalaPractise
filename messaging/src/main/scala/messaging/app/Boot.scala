package messaging.app

import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster
import akka.io.IO
import com.typesafe.config.ConfigFactory
import messaging.config.{ShardJournalSetup, ShardRegionSetup}
import spray.can.Http
import spray.can.Http.Bind


object Boot extends App {

  val port = 2552
  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
    withFallback(ConfigFactory.load())
  implicit val system = ActorSystem("messaging-cluster", config)

  ShardJournalSetup.setup(system, false)
  ShardRegionSetup.setup(system)

  Cluster(system).registerOnMemberUp {
    val receiver = system.actorOf(Props[RequestReceiver], "receiver")
    println("Http server node is ready.")
    IO(Http) ! Bind(listener = receiver, interface = "0.0.0.0", port = 8080)

  }

}
