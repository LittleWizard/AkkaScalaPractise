import akka.actor.{ActorPath, ActorSystem}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import com.typesafe.config.ConfigFactory

object RunSeed extends App {

  val seedConfig = ConfigFactory.load("seed")
  val system = ActorSystem("ClusterSystem", seedConfig)

  val path = ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2552/user/store")

  ShardJournal.startupSharedJournal(system, false, path)

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



}
