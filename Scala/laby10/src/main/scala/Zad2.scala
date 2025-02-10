import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Zacznij
case class Kolejny(a: ActorRef)

class Player1 extends Actor with ActorLogging {
    def receive: Receive = {
        case Kolejny(a) => 
            context.become(withPlayer(a))
            log.info(s"${self.path.name} - odebral aktora")
    }

    def withPlayer(sender: ActorRef): Receive = {
        case Zacznij => 
            sender ! Pilka
            log.info(s"${self.path.name} - zaczynam gre")
        case Pilka => 
            sender ! Pilka
            log.info(s"${self.path.name} - odbijam pilke")
    }
}

@main
def Zad2: Unit = {
  val system = ActorSystem("Players")
  val player1 = system.actorOf(Props[Player1](), "Player1")
  val player2 = system.actorOf(Props[Player1](), "Player2")
  val player3 = system.actorOf(Props[Player1](), "Player3")
  player1 ! Kolejny(player2)
  player2 ! Kolejny(player3)
  player3 ! Kolejny(player1)
  player1 ! Zacznij
}
