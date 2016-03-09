package messaging.app

import akka.actor._
import akka.cluster.sharding.ShardRegion
import akka.cluster.sharding.ShardRegion.Passivate
import akka.persistence.PersistentActor
import scala.concurrent.duration._

object User {

  def props(): Props = Props(new User)

  object MessageContent {
    val empty = MessageContent("")
  }
  case class MessageContent(value: String)

  sealed trait Command {
    def userId: String
  }
  case class AddMessage(userId: String, content: MessageContent) extends Command
  case class GetInboxMessage(userId: String) extends Command


  sealed trait Event
  case class MessageAdded(content: MessageContent)

  val idExtractor: ShardRegion.ExtractEntityId = {
    case cmd: Command => (cmd.userId, cmd)
  }

  val shardResolver: ShardRegion.ExtractShardId = {
    case cmd: Command => math.abs(cmd.userId.hashCode % 10).toString
  }

  val shardName = "User"

}


class User extends PersistentActor with ActorLogging {

  override def persistenceId: String = self.path.parent.name + "-" + self.path.name

  context.setReceiveTimeout(30.minutes)
  import User._
  private var messages = Vector.empty[MessageContent]

  override def receiveRecover: Receive = {
    case evt: MessageAdded =>
      messages :+= evt.content
  }

  override def receiveCommand: Receive = initial

  def initial: Receive = {
    case AddMessage(_, content) =>
      log.info("Adding message to user inbox")
      persist(MessageAdded(content)) { evt =>
        messages :+= evt.content
        sender() ! "Data saved successfully"
      }
    case GetInboxMessage(userId) =>
      log.info("getting messages for user with id :" + userId)
      sender() ! messages.toList
  }

  override def unhandled(msg: Any): Unit = msg match {
    case ReceiveTimeout => context.parent ! Passivate(stopMessage = PoisonPill)
    case _              => super.unhandled(msg)
  }
}

