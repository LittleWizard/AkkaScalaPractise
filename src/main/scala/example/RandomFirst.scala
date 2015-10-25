package example

import java.net.URL
import scala.collection.mutable
import scala.io.Source

//http://www.javacodegeeks.com/2012/10/your-first-message-discovering-akka.html

class RandomFirst {
  val buffer = new mutable.Queue[Int]

  def execute: Int = {
    nextRandom()
  }

  private def nextRandom(): Int = {
    this.synchronized {
      if(buffer.isEmpty){
        buffer ++= fetchRandomNumbers(50)
      }
      buffer.dequeue
    }
  }

  private def fetchRandomNumbers(count: Int) = {
    val url = new URL("https://www.random.org/integers/?num=" + count + "&min=0&max=65535&col=1&base=10&format=plain&rnd=new")
    val connection = url.openConnection()
    val stream = Source.fromInputStream(connection.getInputStream)
    val randomNumbers = stream.getLines().map(_.toInt).toList
    stream.close()
    randomNumbers
  }

}
