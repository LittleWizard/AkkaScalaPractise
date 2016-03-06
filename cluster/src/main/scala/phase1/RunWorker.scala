package phase1

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory


object RunWorker extends App {

  val config = ConfigFactory.load("worker")


  val seedSystem = ActorSystem("words", config)



}
