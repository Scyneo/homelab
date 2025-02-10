import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Pilka
case class GrajZ(a: ActorRef)

class Player extends Actor with ActorLogging {
    def receive: Receive = {
        case GrajZ(a) => 
            a ! Pilka
            log.info(s"${self.path.name} - odebral aktora")
        case Pilka => 
            sender() ! Pilka
            log.info(s"${self.path.name} - odbijam pilke")
    }
}

@main
def Zad1: Unit = {
  val system = ActorSystem("Players")
  val player1 = system.actorOf(Props[Player](), "Player1")
  val player2 = system.actorOf(Props[Player](), "Player2")
  player1 ! GrajZ(player2)
}