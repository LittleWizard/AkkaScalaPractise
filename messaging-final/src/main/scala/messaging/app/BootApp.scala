package messaging.app

import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster
import akka.io.IO
import com.typesafe.config.ConfigFactory
import messaging.config.{AppConfiguration, ApplicationLoader}
import messaging.config.{ShardJournalSetup, ShardRegionSetup}
import messaging.service.MessageService
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spray.can.Http
import spray.can.Http.Bind


object BootApp extends App {

  var appContext: AnnotationConfigApplicationContext = null;

  val port = 2552
  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
    withFallback(ConfigFactory.load())
  implicit val system = ActorSystem("messaging-cluster", config)

  //ShardJournalSetup.setup(system, false)
  ShardRegionSetup.setup(system)

  Cluster(system).registerOnMemberUp {

    /*appContext = ApplicationLoader.loadWebApplicationContext(classOf[AppConfiguration]);

    AppConfig.messageService = appContext.getBean(classOf[MessageService])*/

    val receiver = system.actorOf(Props[RequestReceiver], "receiver")
    println("Http server node is ready.")
    IO(Http) ! Bind(listener = receiver, interface = "0.0.0.0", port = 8080)

  }

  Cluster(system).registerOnMemberRemoved {
    if(appContext != null) appContext.destroy()
  }

}
