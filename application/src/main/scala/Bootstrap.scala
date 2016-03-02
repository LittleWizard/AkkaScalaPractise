

object Bootstrap {

  val appContext = ApplicationLoader.loadWebApplicationContext(classOf[WebConfiguration]);

  implicit val string = appContext.getBean("getString")


  def print = {
    println("Testing spring integration " + string)
  }




}
