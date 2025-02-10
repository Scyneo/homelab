import org.apache.pekko
import pekko.actor._

case class Wstaw(n: Int)
case class Znajdz(n: Int)

class Wezel extends Actor with ActorLogging {
  def receive: Receive = {
    case Wstaw(n) => context.become(lisc(n))
  }

  def lisc(wartosc: Int): Receive = { 
    case Wstaw(n) => {
      var actor = context.actorOf(Props[Wezel](), s"wezel-${n}")
      n match {
        case n if n < wartosc => context.become(zLewymPoddrzewem(actor, wartosc)); actor ! Wstaw(n)
        case n if n > wartosc => context.become(zPrawymPoddrzewem(wartosc, actor)); actor ! Wstaw(n)
        case n if n == wartosc => context.become(lisc(wartosc))
      }
    }
    // this is a leaf
    case Znajdz(n) => {
      if (n == wartosc) {
        log.info("Znaleziono, lisc")
      } else {
        log.info("Nie znaleziono, lisc")
      }
    }
  }

  def zLewymPoddrzewem(lewe: ActorRef, wartosc: Int): Receive = {
    case Wstaw(n) => {
      var actor = context.actorOf(Props[Wezel](), s"wezel-${n}")
      n match {
        case n if n < wartosc => lewe ! Wstaw(n)
        case n if n > wartosc => context.become(zPoddrzewami(lewe, wartosc, actor)); actor ! Wstaw(n)
        case n if n == wartosc => context.become(zLewymPoddrzewem(lewe, wartosc))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => lewe ! Znajdz(n)
        case n if n > wartosc => log.info("not found")
        case n if n == wartosc => log.info("Znaleziono, mam lewe poddrzewo")
      }
    }
   }

  def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(n) => {
      var actor = context.actorOf(Props[Wezel](), s"wezel-${n}")
      n match {
        case n if n < wartosc => context.become(zPoddrzewami(actor, wartosc, prawe)); actor ! Wstaw(n)
        case n if n > wartosc => prawe ! Wstaw(n)
        case n if n == wartosc => context.become(zPrawymPoddrzewem(wartosc, prawe))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => log.info("not found")
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => log.info("Znaleziono, mam prawe poddrzewo")
      }
    }
  }

  def zPoddrzewami(lewe: ActorRef, wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(n) => {
      n match {
        case n if n < wartosc => lewe ! Wstaw(n)
        case n if n > wartosc => prawe ! Wstaw(n)
        case n if n == wartosc => context.become(zPoddrzewami(lewe, wartosc, prawe))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => lewe ! Znajdz(n)
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => log.info("Znaleziono, poddrzewa")
      }
    }
  }
}

@main
def main: Unit = {
  val system = ActorSystem("Symulacja")
  val root = system.actorOf(Props[Wezel](), "boss")
  root ! Wstaw(10)
  root ! Wstaw(7)
  root ! Wstaw(16)
  root ! Wstaw(14)
  root ! Wstaw(15)
  root ! Wstaw(4)
  root ! Wstaw(5)
  root ! Wstaw(3)
  root ! Wstaw(1)
  root ! Wstaw(2)
  root ! Wstaw(8)
  root ! Znajdz(0)
}