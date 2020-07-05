package primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object FirstPrinciples extends App {

  implicit val system = ActorSystem("firstPrinciples")
  implicit val materializer = ActorMaterializer
  val nameSource = Source(List("Dursun", "Elif", "Beyza", "Yasemin", "Demir"))
  nameSource.filter(_.length>=5).take(2).runForeach(println)
  val lengthFilter = Flow[String].filter(_.length>=5)
  val firstTwoFlow = Flow[String].take(2)
  val printSink = Sink.foreach(println)
  nameSource.via(firstTwoFlow).via(lengthFilter).to(printSink).run

}
