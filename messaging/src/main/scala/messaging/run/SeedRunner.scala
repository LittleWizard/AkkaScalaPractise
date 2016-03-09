package messaging.run

import akka.actor.{ActorSystem}
import com.typesafe.config.ConfigFactory
import messaging.config.{ShardRegionSetup, ShardJournalSetup}


object SeedRunner extends App {

  val port = 2551
  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
    withFallback(ConfigFactory.load())
  val system = ActorSystem("messaging-cluster", config)

  ShardJournalSetup.setup(system, false)
  ShardRegionSetup.setup(system)

}
