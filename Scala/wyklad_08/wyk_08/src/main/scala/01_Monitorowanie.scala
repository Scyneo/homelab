package zjp.wyk_08_01.monitoring

import org.apache.pekko.actor
import actor.Actor
import actor.ActorLogging
import actor.ActorRef
import actor.ActorSystem
import actor.Props
import actor.Terminated

object Boss {
  case class Work(w: Int)
  def props(pracownik: ActorRef): Props = {
    Props(classOf[Boss], pracownik)
  }
}
class Boss(pracownik: ActorRef) extends Actor with ActorLogging {
  import Boss.*

  // monitorujemy pracownika
  override def preStart(): Unit = {
    context.watch(pracownik)
    log.info(s"Zaczynamy monitorowanie ${pracownik.path}")
  }

  override def postStop(): Unit = {
    log.info(s"${self.path} skończył działanie")
  }
  def receive: Receive = {
    case Work(n) =>
      pracownik ! n
  }
}

class Worker extends Actor with ActorLogging {
  def receive: Receive = {
    case komunikat =>
      log.info(s"dostałęm wiadomość: $komunikat")
      context.stop(self)
  }
}

@main 
def demo: Unit = {
  val system = ActorSystem("monitorowanie")
  val worker = system.actorOf(Props[Worker](), "worker")

  val boss = system.actorOf(Boss.props(worker), "boss")
  boss ! Boss.Work(123)

  system.terminate()
}
