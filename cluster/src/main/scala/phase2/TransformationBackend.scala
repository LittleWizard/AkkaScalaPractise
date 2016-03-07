package phase2

import akka.actor.{Props, ActorSystem, RootActorPath, Actor}
import akka.cluster.{Member, MemberStatus, Cluster}
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberUp}
import com.typesafe.config.ConfigFactory


class TransformationBackend extends Actor {


  val cluster = Cluster(context.system)

  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case TransformationJob(text) => sender() ! TransformationResult(text.toUpperCase())
    case state: CurrentClusterState =>
      state.members.filter(_.status == MemberStatus.Up) foreach register
    case MemberUp(m) => register(m)
  }

  def register(member: Member): Unit = {
    if(member.hasRole("frontend"))
      context.actorSelection(RootActorPath(member.address) / "user" / "frontend") !
      BackendRegistration
  }

}

object TransformationBackend {
  def main(args: Array[String]): Unit = {
    val port = if (args.isEmpty) "0" else args(0)
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
      withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)
    system.actorOf(Props[TransformationBackend], name = "backend")
  }
}
