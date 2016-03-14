package my.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

/**
  * Created by Adam on 14/03/2016.
  */

object TweetApp extends App {

  import scala.reflect.ClassTag
  def getType[T](v: T)(implicit ev: ClassTag[T]) = ev.toString


  implicit val system = ActorSystem("reactive-tweets")
  implicit val materializer = ActorMaterializer()

  val akka = Hashtag("#akka")
  var tweets: Source[Tweet, Unit] = _

  val authors =
    tweets
      .filter(_.hashtags.contains(akka))
      .map(_.author)

  print(getType(authors))
}
