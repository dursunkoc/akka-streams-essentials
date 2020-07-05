package primer

import java.util.Date

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object OperatorFusion extends App {
  implicit val system = ActorSystem("operatorFusion")
  implicit val materializer = ActorMaterializer

  val source = Source(1 to 1000)
  val f1 = Flow[Int].map{ v=>
    Thread.sleep(1000)
    v+1
  }
  val f2 = Flow[Int].map{v=>
  Thread.sleep(1000)
  v*10
}
  val sink = Sink.foreach[Int] { v=>
    val n = new Date()
    println(s"$v ==> $n")
  }
  source.via(f1).async
    .via(f2)
    .runWith(sink)

}
