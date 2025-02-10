import org.apache.pekko
import pekko.actor._
import scala.concurrent.duration._

case class Init()
case class Ostrzal()
case class Sygnal(zamek1: ActorRef, zamek2: ActorRef)
case class CelPal(zamek: ActorRef)
case class Strzal(count: Int)

class SilaWyzsza extends Actor with ActorLogging {
    def receive: Receive = {
        case Sygnal(z1, z2) => {
            z1 ! CelPal(z2)
            z2 ! CelPal(z1)
        }
    }
}

class Zamek extends Actor with ActorLogging {
    val rand = new scala.util.Random
    def receive: Receive = {
        case Init => {
            val obroncy = for {i <- (1 to 100) } yield {
                val obronca = context.actorOf(Props[Obronca](), s"obronca${i}")
                context.watch(obronca)
                obronca
            }
            context.become(bitwa(obroncy.toList))
        }
    }

    def bitwa(obroncy: List[ActorRef]): Receive = {
        case CelPal(z) => {
            obroncy.foreach(o => o ! CelPal(z))
        }
        case Ostrzal => {
            val idx = rand.between(0, obroncy.size)
            obroncy(idx) ! Strzal(obroncy.size)
        }
        case Terminated(obronca) => {
            if (obroncy.size <= 1) {
                log.info("Przegralem!!!")
                context.system.terminate()
            }
            context.become(bitwa(obroncy.filter(o => o != obronca)))
        }
    }
}

class Obronca extends Actor with ActorLogging {
    val rand = new scala.util.Random
    def receive: Receive = {
        case CelPal(z) => z ! Ostrzal
        case Strzal(n) => {
            val pick = rand.between(0, 100)
            if (pick < Math.round(n.toFloat / 2)) {
                self ! PoisonPill
            }
        }
    }
}

@main 
def Symulacja: Unit = {
    val system = ActorSystem("Symulacja")
    val sw = system.actorOf(Props[SilaWyzsza](), "SilaWyzsza")
    val z1 = system.actorOf(Props[Zamek](), "Zamek1")
    val z2 = system.actorOf(Props[Zamek](), "Zamek2")
    z1 ! Init
    z2 ! Init
    
    import system.dispatcher
    val ticker = system.scheduler.scheduleWithFixedDelay(
        Duration.Zero,
        100.milliseconds,
        sw,
        Sygnal(z1, z2)
    )
}
