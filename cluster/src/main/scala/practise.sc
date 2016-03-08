import akka.actor.RelativeActorPath

val text = List("this is a test", "of some very naive word counting", "but what can you say", "it is what it is")
val res = (1 to 3).flatMap(i => text ++ text).toList


res.foreach(b => {
  println(b)
})
Vector.fill(5){new Object}



val servicePath = "/user/statsService"

val servicePathElements = servicePath match {
  case RelativeActorPath(elements) => elements
  case _ => throw new IllegalArgumentException(
    "servicePath [%s] is not a valid relative actor path" format servicePath)
}