package my.akkastreams.pi

import akka.actor.ActorRef

import scala.concurrent.duration.Duration

/**
  * Created by adam on 19.03.16.
  */
sealed trait PiMessage

case object Calculate extends PiMessage
case class Work(start: Int, nrOfElements: Int, slf: ActorRef) extends PiMessage
case class Result(value: Double) extends PiMessage
case class PiApproximation(pi: Double, duration: Duration, nrOfWorkers: Int, precision: Long)
