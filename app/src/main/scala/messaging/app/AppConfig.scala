package messaging.app

import messaging.service.MessageService


object AppConfig {

//  val appContext = ApplicationLoader.loadWebApplicationContext(classOf[AppConfiguration]);

//  implicit val string = null

  implicit var messageService:MessageService = null

}
