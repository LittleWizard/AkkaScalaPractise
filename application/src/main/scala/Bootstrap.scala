

object Bootstrap {

  val appContext = ApplicationLoader.loadWebApplicationContext(classOf[AppConfiguration]);

  implicit val string = appContext.getBean("getString")


  def print = {
    println("Testing spring integration " + string)
  }




}
