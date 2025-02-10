import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props, PoisonPill}

case class Zacznij3(a: ActorRef, count: Int)

class Player3 extends Actor with ActorLogging {
    def receive: Receive = {
        case Kolejny(a) => 
            context.become(withPlayer(a))
            log.info(s"${self.path.name} - odebral aktora ${a.path.name}")
        case Zacznij3(actor, count) => 
            actor ! Pilka
            context.become(beginWithPlayer(actor, 0, count))
            log.info(s"${self.path.name} - zaczynam gre")
    }

    def withPlayer(sender: ActorRef): Receive = {
        case Pilka => 
            Thread.sleep(100)
            sender ! Pilka
            log.info(s"${self.path.name} - odbijam pilke")
    }

    def beginWithPlayer(sender: ActorRef, curr: Int, count: Int): Receive = {
        case Pilka => 
            if (curr == count) {
                log.info(s"${self.path.name} - koniec gry")
                context.system.terminate()
            }
            Thread.sleep(100)
            sender ! Pilka
            log.info(s"${self.path.name} - odbijam pilke, okrazenie: ${curr}")
            context.become(beginWithPlayer(sender, curr+1, count))
    }
}

@main
def Zad3: Unit = {
  val system = ActorSystem("Players")
  val players = for {i <- (1 to 10) } yield system.actorOf(Props[Player3](), s"Player${i}")
  players.sliding(2).foreach{ case Seq(a, b) => a ! Kolejny(b) case _ => }
  players.last ! Zacznij3(players.head, 3)
}