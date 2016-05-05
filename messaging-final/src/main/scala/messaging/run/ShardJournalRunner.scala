package messaging.run

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
//import messaging.config.ShardJournalSetup


object ShardJournalRunner extends App {

  val port = 2559
  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
    withFallback(ConfigFactory.load())
  val system = ActorSystem("messaging-cluster", config)

//  ShardJournalSetup.setup(system, true)

}
