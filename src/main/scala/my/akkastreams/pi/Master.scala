package my.akkastreams.pi

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.RoundRobinPool

import scala.concurrent.duration.{Duration, _}
import scala.language.postfixOps

/**
  * Created by adam on 19.03.16.
  */
class Master(nrOfWorkers: Int,
             nrOfMessages: Int,
             nrOfElements: Int,
             listener: ActorRef) extends Actor with ActorLogging {

  var pi: Double = _
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis

  val workerRouter = context.actorOf(
    Props[Worker].withRouter(RoundRobinPool(nrOfWorkers)),
    name = "workerRouter"
  )

  def endTime: Duration = (System.currentTimeMillis() - start) millis

  override def receive: Receive = {
    case Calculate => {
      for (i <- 0 until nrOfMessages) {
        workerRouter ! Work(i * nrOfElements, nrOfElements, self)
      }
    }
    case Result(value) => {
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, endTime, nrOfWorkers, nrOfMessages * 1000)
        context stop self
      }
    }
  }

}
