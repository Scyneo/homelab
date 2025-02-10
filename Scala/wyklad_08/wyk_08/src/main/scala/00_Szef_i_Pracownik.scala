package wykład_07.d

import org.apache.pekko.actor.ActorLogging
import org.apache.pekko.actor.Actor
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.actor.Props
import org.apache.pekko.actor.ActorRef

object Wspólne {
  case class Wynik(pierwsze: List[Int])
}

object Szef {
  case class Zadanie(max: Int)
}

class Szef extends Actor with ActorLogging {
  import Szef.*

  def receive: Receive = {
    case Zadanie(m) =>
      // odfiltrowujemy wszystkie liczby „nie pierwsze” z [2..m]
      val pracownik = context.actorOf(Props[Pracownik](), "p0")
      (2 to m).foreach {
        element => pracownik ! Pracownik.Propozycja(element)
      }
      pracownik ! Pracownik.PodajWynik
      context.become(czekającNaWynik)
    }
  def czekającNaWynik: Receive = {
    case Wspólne.Wynik(l) =>
      log.info(s"Liczby pierwsze: ${l}")
  }
}

object Pracownik {
  case object PodajWynik
  case class Propozycja(n: Int)
}
class Pracownik extends Actor with ActorLogging {
  import Pracownik.*

  def receive: Receive = {
    case Propozycja(skarb) =>
        println(s"skarb: $skarb. Pierwsza liczba otrzymana przez pracownika: ${self.path}")
      context.become(zeSkarbem(skarb))
    case _ =>
  }

  def zeSkarbem(skarb: Int): Receive = {
    case Propozycja(n) =>
      if (n % skarb != 0) {
        println(s"n: $n, skarb: $skarb. Tworzymy nowego pracownika i przechodzimy w stan z potomkiem od: ${self.path}")
        val potomek = context.actorOf(Props[Pracownik]())
        potomek ! Propozycja(n)
        context.become(zeSkarbemIPotomkiem(skarb, potomek))
      }
    // tutaj jeszcze wrócimy!
    case PodajWynik =>
      println(s"Zaczynamy mergowanie: $skarb od ${self.path}")
      sender() ! Wspólne.Wynik(List(skarb))
    case _ =>
  }

  def zeSkarbemIPotomkiem(skarb: Int, potomek: ActorRef): Receive = {
    case Propozycja(n) =>
      if n % skarb != 0 then {
        println()
        println(s"n: $n, skarb: $skarb. Wysyłamy potomkowi propozycję od: ${self.path}")
        potomek ! Propozycja(n)
      }
    // tutaj jeszcze wrócimy!
    case PodajWynik =>
      potomek ! PodajWynik
      context.become(czekającNaWynik(skarb))
    case _ =>
  }

  def czekającNaWynik(skarb: Int): Receive = {
    case Wspólne.Wynik(listaPierwszych) =>
      println(s"Kontynuujemy mergowanie: $skarb, lista: ${listaPierwszych}, od ${self.path}")
      context.parent ! Wspólne.Wynik(skarb :: listaPierwszych)
  }
}

@main
def main: Unit = {
  val system = ActorSystem("sys")
  val szef = system.actorOf(Props[Szef](), "szef")
  szef ! Szef.Zadanie(10)
  // szef ! 123
}