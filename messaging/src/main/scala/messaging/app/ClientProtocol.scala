package messaging.app

import spray.json.DefaultJsonProtocol


object ClientProtocol {
  case class Message(userId: String, value: String)

  object Message extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Message.apply)
  }

}


