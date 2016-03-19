package my.akkastreams.pi

import akka.actor.{ActorSystem, Props}

/**
  * Created by adam on 19.03.16.
  */
object Pi extends App {
  calculate(nrOfWorkers = 200, nrOfElements = 10000, nrOfMessages = 100000)

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    val system = ActorSystem("PiSystem")
    val listener = system.actorOf(Props[Listener], name = "listener")

    val master = system.actorOf(
      Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)),
      name = "master"
    )

    master ! Calculate
  }
}
