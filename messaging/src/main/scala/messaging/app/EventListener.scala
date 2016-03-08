package messaging.app

import akka.actor.{Address, ActorLogging, Actor}
import akka.cluster.ClusterEvent._
import akka.cluster.{Member, Cluster, MemberStatus}

import scala.collection.mutable


class EventListener extends Actor with ActorLogging {

  val cluster = Cluster(context.system)


 // var nodes = address

  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberEvent], classOf[ReachabilityEvent])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }


  def receive = {

    case state: CurrentClusterState =>


     state.members.collect {
       case m if m.hasRole("worker") && m.status == MemberStatus.Up => Boot.nodes += m.address
     }
      println(">>>>>>>>>>>>>>>>>>>> " + Boot.nodes.size)
    case MemberUp(m) if m.hasRole("worker")        => {
      Boot.nodes += m.address
      println(">>>>>>>>>>>>>>>>>>>> " + Boot.nodes.size)
    }
    case other: MemberEvent                         => {
      println(">>>>>>>>>>>>>>>>>>>>33333333333333333333")
      Boot.nodes -= other.member.address
    }
    case UnreachableMember(m)                       => {
      println(">>>>>>>>>>>>>>>>>>>>44444444444444444444444444444")
      Boot.nodes -= m.address
    }
    case ReachableMember(m) if m.hasRole("worker") => {
      Boot.nodes += m.address
      println(">>>>>>>>>>>>>>>>>>>> " + Boot.nodes.size)
    }

  }


}
