package messaging.run

import akka.actor.{ActorPath, ActorSystem}
import com.typesafe.config.ConfigFactory
import messaging.config.ShardJournal


object SeedRunner extends App {

  val port = 2551
  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
    withFallback(ConfigFactory.load())
  val system = ActorSystem("messaging-cluster", config)

  ShardJournal.startupSharedJournal(system, true)

}
