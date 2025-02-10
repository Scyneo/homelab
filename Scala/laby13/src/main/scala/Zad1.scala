import org.apache.pekko
import pekko.actor._
import scala.concurrent.duration._

// Przykład wykorzystania Planisty (Scheduler)

object TickActor {
  val Tick = "tick"
}

class TickActor extends Actor {
  import TickActor._
  def receive = {
    case Tick => println("Tick")
  }
}
  
@main 
def zad1: Unit = {

  val system = ActorSystem("system")
  import system.dispatcher

  val tickActor = system.actorOf(Props[TickActor](), "defender")

  val ticker = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,
    50.milliseconds,
    tickActor,
    TickActor.Tick
  )

 // system.terminate()

}
