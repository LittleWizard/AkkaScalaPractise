package messaging.config

import akka.actor.ActorSystem
import akka.cluster.sharding.{ClusterShardingSettings, ClusterSharding}
import messaging.app.User

object ShardRegionSetup {

  def setup(system: ActorSystem) = {

    ClusterSharding(system).start(
    typeName = User.shardName,
    entityProps = User.props(),
    settings = ClusterShardingSettings(system),
    extractEntityId = User.idExtractor,
    extractShardId = User.shardResolver
    )

  }

}
