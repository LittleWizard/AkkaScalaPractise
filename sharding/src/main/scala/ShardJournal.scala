import akka.actor._
import akka.persistence.journal.leveldb.{SharedLeveldbStore, SharedLeveldbJournal}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._



object ShardJournal {

  def main(args: Array[String]): Unit = {
    val port = 2552
    val path = ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:" + port + "/user/store")
    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
      withFallback(ConfigFactory.load("persistence"))
    val system = ActorSystem("ClusterSystem", config)
    startupSharedJournal(system, true, path)
  }


  def startupSharedJournal(system: ActorSystem, startStore: Boolean, path: ActorPath): Unit = {

    if(startStore)
      system.actorOf(Props[SharedLeveldbStore], "store")

    import system.dispatcher
    implicit val timeout = Timeout(15.seconds)
    import akka.pattern.ask
    val f = (system.actorSelection(path) ? Identify(None))
    f.onSuccess {
      case ActorIdentity(_, Some(ref)) => SharedLeveldbJournal.setStore(ref, system)
      case _ =>
        system.log.error("Shared journal not started at {}", path)
        system.terminate()
    }
    f.onFailure {
      case _ =>
        system.log.error("Lookup of shared journal at {} timed out", path)
        system.terminate()
    }
  }
}
