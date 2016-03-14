package my.akkastreams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import my.akkastreams.twitter.listeners.{ActorListener, PrintSampleListener}
import org.scalatest.FunSuite
import twitter4j.{Status, UserStreamAdapter, Query, TwitterStreamFactory}
import collection.JavaConversions._
import scala.concurrent.Await
import twitter4j.StreamListener

/**
  * Created by Adam on 14/03/2016.
  */
class TwitterSourceFactoryTest extends FunSuite {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  test("jTweeter test") {
    val query = new Query("akka")
    val queryResult = TwitterSourceFactory.jTwitter().search().search(query)
    val tweets = queryResult.getTweets
    val list = tweets.iterator().take(10).toList
    assert(list.nonEmpty)
  }

  test("jStream test 1") {
    val stream = TwitterSourceFactory.jTwitterStream()
    stream.addListener(PrintSampleListener())
    stream.sample()
    Thread.sleep(5000)
  }

  test("actor listener test") {
    val stream = TwitterSourceFactory.jTwitterStream()
    val listener = new ActorListener()
  }

  test("case class const test") {
    case class cclas() {
      println("hello world")
    }
    cclas()
  }

  test("jStream actor test") {
    val stream = TwitterSourceFactory.jTwitterStream()
    val listener = ActorListener()
    stream.addListener(listener)
    stream.sample("en")
    listener.source
      .mapConcat(tweet => tweet.split("\\s+").filter(_.startsWith("#")).toList)
      .map(_.substring(1))
      .runForeach(println)
    Thread.sleep(15000)
  }

}
