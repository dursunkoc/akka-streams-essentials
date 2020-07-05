package primer

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source, Flow}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object MaterializingStreams extends App {

  implicit val system = ActorSystem("mat")
  import system.dispatcher

  implicit val materialization = Materializer

  val last1 = Source[Int](1 to 10).runWith(Sink.last)
  last1 onComplete{
    case Success(v) => println(s"last number is $v")
    case Failure(ex) => println(s"exception $ex")
  }

  val sentencesSource = Source[String](List("Hello this is message1", "and this is second message", "and the final message is this one"))
  val splitter = Flow[String].map(_.split(" "))
  val counter = Flow[Array[String]].map(_.length)
  val agg = Sink.reduce[Int](_+_)

  val sumNumbers = sentencesSource.via(splitter).via(counter).runWith(agg)

  sumNumbers onComplete{
    case Success(v) => println(s"sum of all words is $v")
    case Failure(ex) => println(s"exception $ex")
  }

}
