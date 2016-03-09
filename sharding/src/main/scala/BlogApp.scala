
import akka.actor.{Props, ActorPath, ActorSystem}
import akka.cluster.sharding.{ClusterShardingSettings, ClusterSharding}
import com.typesafe.config.ConfigFactory

object BlogApp {

  def main(args: Array[String]): Unit = {

    if(args.isEmpty)
      startup(Seq("2553"))
    else startup(args)

  }


  def startup(ports: Seq[String]): Unit = {

    ports foreach { port =>

      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
      withFallback(ConfigFactory.load())

      val system = ActorSystem("ClusterSystem", config)
      ShardJournal.startupSharedJournal(system, false, ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2552/user/store"))

      val authorListingRegion = ClusterSharding(system).start(
      typeName = AuthorListing.shardName,
      entityProps = AuthorListing.props(),
      settings = ClusterShardingSettings(system),
      extractEntityId = AuthorListing.idExtractor,
      extractShardId = AuthorListing.shardResolver
      )

      ClusterSharding(system).start(
      typeName = Post.shardName,
      entityProps = Post.props(authorListingRegion),
      settings = ClusterShardingSettings(system),
      extractEntityId = Post.idExtractor,
      extractShardId = Post.shardResolver
      )

      if (port != "2551" && port != "2552")
        system.actorOf(Props[Bot], "bot")

    }


  }


}
