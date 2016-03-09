package messaging.app

import spray.json.DefaultJsonProtocol


object ClientProtocol {

  case class Message(userId: String, value: String)

  object Message extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Message.apply)
  }

  case class GetMessages(userId: String)
  object GetMessages extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(GetMessages.apply)
  }


  case class SendMessageResponse(success: Boolean, Data: String)
  object SendMessageResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(SendMessageResponse.apply)
  }

  case class GetMessageResponse(success: Boolean, Data: List[Message])
  object GetMessageResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(GetMessageResponse.apply)
  }


}


