package zjp.wyk_13_03.ask_with_payload

import org.apache.pekko
import pekko.actor.typed.{ActorRef, Behavior}
import pekko.actor.typed.scaladsl.Behaviors

object Guardian {

  sealed trait Command
  case object Start extends Command

  def apply(): Behavior[Command] =
    Behaviors.setup { context =>
      val manager: ActorRef[Manager.Command] = context.spawn(Manager(), "manager")
      Behaviors.receiveMessage { message =>
        manager ! Manager.Delegate(List("tekst-a", "tekst-b", "tekst-c"))
        Behaviors.same
      }
    }

}
