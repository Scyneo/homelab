package zjp.wyk_13_02.simple_ask

import org.apache.pekko
import pekko.actor.typed.{ActorRef, Behavior}
import pekko.actor.typed.scaladsl.Behaviors

object Guardian {

  sealed trait Command
  final case class Start(data: List[String]) extends Command

  def apply(): Behavior[Command] =
    Behaviors.setup { context =>
      val manager: ActorRef[Manager.Command] = context.spawn(Manager(), "manager")
      Behaviors.receiveMessage {
        case Start(data) =>
          manager ! Manager.Delegate(data)
          Behaviors.same
      }
    }
}
