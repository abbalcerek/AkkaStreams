package scala.my.akkastreams

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future

/**
 * Hello world!
 *
 */
object App extends App {


  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)
  source.filter(_ > 98).runForeach(println)(materializer)

  val factorials = source.scan(BigInt(1))(_*_)

  val result: Future[IOResult] =
    factorials
      .map(num => ByteString(s"$num\n"))
      .runWith(FileIO.toFile(new File("factorials.txt")))
}
