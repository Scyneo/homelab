import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props, PoisonPill}

case object Pilka
case class GrajZ(actor: ActorRef)
case class Kolejny(actor: ActorRef, count: Int)
case class ZacznijZCount(actor: ActorRef, count: Int)
case class Przejmij(actor: ActorRef)

class Player extends Actor with ActorLogging {
    def receive: Receive = {
        case Kolejny(a, count) => {
            context.become(withPlayer(a, count))
            log.info(s"${self.path.name} - odebral aktora ${a.path.name}")
        }
        case ZacznijZCount(actor, count) => {
            actor ! Pilka
            context.become(withPlayer(actor, count))
            log.info(s"${self.path.name} - zaczynam gre")
        }
    }

    def withPlayer(passTo: ActorRef, count: Int): Receive = {
        case Przejmij(actor) => {
            if (actor == self) {
                log.info(s"${self.path.name} - KONIEC GRY")
                context.system.terminate()
            } else {
                actor ! Pilka
                context.become(withPlayer(actor, count))
            }
        }
        case Pilka => {
            if (count-1 == 0) {
                Thread.sleep(100)
                sender() ! Przejmij(passTo)
                log.info(s"${self.path.name} - Koncze gre")
                self ! PoisonPill
            } else {
                Thread.sleep(100)
                passTo ! Pilka
                log.info(s"${self.path.name} - odbijam pilke, zostalo ${count-1} odbic")
                context.become(withPlayer(passTo, count-1))
            }
    }
}
}

@main
def Zad3: Unit = {
  val rand = new scala.util.Random
  val system = ActorSystem("Players")
  val players = for {i <- (1 to 10) } yield (system.actorOf(Props[Player](), s"Player${i}"), rand.between(1, 10))
  players.sliding(2).foreach{ case Seq(a, b) => a(0) ! Kolejny(b(0), a(1)) case _ => }
  
  players.last(0) !  ZacznijZCount(players.head(0), players.last(1))
}