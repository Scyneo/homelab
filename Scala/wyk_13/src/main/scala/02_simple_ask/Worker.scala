package zjp.wyk_13_02.simple_ask

import org.apache.pekko
import pekko.actor.typed.{ActorRef, Behavior}
import pekko.actor.typed.scaladsl.Behaviors

import scala.util.Random

object Worker {

  sealed trait Command
  final case class Parse(replyTo: ActorRef[Worker.Response]) extends Command

  sealed trait Response
  case object Done extends Response

  def apply(text: String): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      message match {
        case Parse(replyTo) =>
          fakeLengthyParsing(text)
          context.log.info(s"${context.self.path.name} – zrobione!")
          replyTo ! Worker.Done
          Behaviors.same
      }
    }

  private def fakeLengthyParsing(text: String): Unit = {
    val endTime = System.currentTimeMillis + Random.between(2000, 4000)
    while (endTime > System.currentTimeMillis) {}
  }
}
