package my.akkastreams.pi

import akka.actor.{Actor, ActorLogging}

/**
  * Created by adam on 19.03.16.
  */
class Worker extends Actor with ActorLogging {

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (i ← start until (start + nrOfElements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }

  def receive = {
    case Work(start, nrOfElements, master) ⇒
      master ! Result(calculatePiFor(start, nrOfElements))
  }
}


