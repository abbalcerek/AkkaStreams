package my.akkastreams.pi

import java.text.NumberFormat

import akka.actor.{Actor, ActorLogging}

/**
  * Created by adam on 19.03.16.
  */
class Listener extends Actor with ActorLogging {

  val formatter = NumberFormat.getIntegerInstance()

  override def receive: Receive = {
    case PiApproximation(pi, duration, nrOfWorkers, precision) =>
      log.info(
        s"\n\tPi exact*:\t${Math.PI}"
        + s"\n\tPi approximation:\t$pi"
          + s"\n\t Difference\t${Math.abs(pi - Math.PI)}"
        + s"\n\tCalculation time:\t${formatter.format(duration.toMillis)}"
        + s"\n\tNumber of workers:\t${formatter.format(nrOfWorkers)}"
        + s"\n\tSum elements number:\t${formatter.format(precision)}")
      context.system.terminate
  }
}