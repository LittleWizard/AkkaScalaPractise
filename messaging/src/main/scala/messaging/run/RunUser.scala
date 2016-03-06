package messaging.run

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory


object RunUser extends App {

  val config = ConfigFactory.load("user")
  val seedSystem = ActorSystem("messaging-cluster", config)

}
