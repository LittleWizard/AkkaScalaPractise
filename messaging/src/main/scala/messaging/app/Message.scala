package messaging.app

import spray.json.DefaultJsonProtocol


case class Message(userId: String, value: String)

object Message extends DefaultJsonProtocol {

  implicit val format = jsonFormat2(Message.apply)

}


case class MessageRequest(userId: String, value: String)