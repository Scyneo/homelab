package zjp.wyk_13_02.simple_ask

import org.apache.pekko
import pekko.actor.typed.{ActorRef, Behavior}
import pekko.actor.typed.scaladsl.Behaviors
import pekko.util.Timeout

import scala.concurrent.duration.SECONDS
import scala.util.Failure
import scala.util.Success

object Manager {

  sealed trait Command
  final case class Delegate(data: List[String]) extends Command
  private final case class Report(description: String) extends Command

  def apply(): Behavior[Command] =
    Behaviors.setup { context =>
      implicit val timeout: Timeout = Timeout(4, SECONDS)

      Behaviors.receiveMessage { message =>
        message match {
          case Delegate(data) =>
            data.map { text =>
              val worker: ActorRef[Worker.Command] = context.spawn(Worker(text), s"worker-$text")
              context.ask(worker, Worker.Parse.apply) {
                case Success(Worker.Done) =>
                  Report(s"$text został przetworzony przez „${worker.path.name}”")
                case Failure(ex) =>
                  Report(s"Przetwarzanie „$text” nie powiodło się: ${ex.getMessage()}")
              }
            }
            Behaviors.same
          case Report(description) =>
            context.log.info(description)
            Behaviors.same
        }
      }
    }
}
