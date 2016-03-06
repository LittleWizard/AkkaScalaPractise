package messaging.run

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory


object RunSeed extends App {

  val seedConfig = ConfigFactory.load("seed")
  val seedSystem = ActorSystem("messaging-cluster", seedConfig)

}
