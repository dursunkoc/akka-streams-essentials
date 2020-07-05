package primer

import java.util.Date

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source}

object BackPressureBasics extends App {
  implicit val system = ActorSystem("bpBasics")
  implicit val materializer = ActorMaterializer

  val source = Source(1 to 100)

  val f1 = Flow[Int].map { v =>
    Thread.sleep(10)
    val n = new Date()
    println(s"*1* $v ==> $n")
    v
  }.buffer(15, overflowStrategy = OverflowStrategy.dropNew)

  val f2 = Flow[Int].map { v =>
    Thread.sleep(1000)
    val n = new Date()
    println(s"-2- $v ==> $n")
    v
  }.buffer(5, overflowStrategy = OverflowStrategy.dropHead)

  val f3 = Flow[Int].map { v =>
    Thread.sleep(1000)
    val n = new Date()
    println(s"-3- $v ==> $n")
    v
  }

  val sink = Sink.foreach(println)

  source
    .via(f1).async
    .via(f2).async
    .via(f3).async
    .to(Sink.ignore)
    .run()

}
