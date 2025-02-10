package zjp.wyk_08_02.supervision

import org.apache.pekko.actor
import actor.Actor
import actor.ActorLogging
import actor.ActorRef
import actor.ActorSystem
import actor.Props

object Exceptions {
  case object Resume extends Exception("Resume")
  case object Stop extends Exception("Stop")
  case object Restart extends Exception("Restart")
  case object Other extends Exception("Other")
}

class Worker extends Actor, ActorLogging {
  def receive: Receive = ???
}
