package my.akkastreams.twitter.listeners

import akka.actor.ActorRef
import akka.stream.{Materializer, StreamSubscriptionTimeoutSettings, OverflowStrategy}
import akka.stream.scaladsl.{Keep, Sink, Source}
import org.reactivestreams.Publisher
import twitter4j.{Status, UserStreamAdapter}

/**
  * Created by Adam on 14/03/2016.
  */
class TwitterListener() extends UserStreamAdapter

case class PrintSampleListener() extends TwitterListener {

  override def onStatus(status: Status): Unit = {
    print(status.getText)
  }
}

case class ActorListener(implicit val materializer: Materializer) extends TwitterListener {
  private val (actorRef, publisher): (ActorRef, Publisher[String])
    = Source.actorRef[String](1000, OverflowStrategy.fail)
      .toMat(Sink.asPublisher(true))(Keep.both).run()
  val source = Source.fromPublisher(publisher)

  override def onStatus(status: Status): Unit = {
    actorRef ! status.getText
  }
}
