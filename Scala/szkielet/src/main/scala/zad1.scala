import org.apache.pekko
import pekko.actor.*
import scala.util.Random

case class Utworz(lMagazynierow: Int)
case object Dostawa
case class Dostarcz(firma: ActorRef)
case class Przyjmujacy(magazynier: ActorRef)
case object Brak
case class Przyjmij(list: List[Int])
case class Towar(list: List[Int])

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad1(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

class Firma extends Actor with ActorLogging {
  def receive: Receive = {
    case Utworz(n) => {
      val workers = for {i <- (1 to n) } yield {context.actorOf(Props[Magazynier](), s"magazynier-${i}")}
      context.become(receiveDostawa(workers.toList, List(), List()))
    }
  }

  def receiveDostawa(free: List[ActorRef], busy: List[ActorRef], towary: List[Int]): Receive = {
    case Dostawa => {
      val worker = free.head
      sender() ! Przyjmujacy(worker)
      context.become(receiveDostawa(free.drop(1), busy :+ worker, towary))
    }
    case Towar(list) => {
      val worker = sender()
      log.info(s"Suma towarow: ${(towary ++ list).size}")
      log.info(s"Moje towary: ${towary ++ list}")
      context.become(receiveDostawa(free :+ worker, busy.filter(w => w != worker), towary ++ list))
    }
  }
}

class Dostawca extends Actor with ActorLogging {
  def receive: Receive = {
    case Dostarcz(to) => {
      to ! Dostawa
    }
    case Przyjmujacy(actor) => {
      val randInt = losuj_zad1(1, 10)
      val randList = for {i <- (1 to randInt) } yield {losuj_zad1(0, 100)}
      actor ! Przyjmij(randList.toList)
      self ! PoisonPill
    }
    case Brak => {
      self ! PoisonPill
    }
  }
}

class Magazynier extends Actor with ActorLogging {
  def receive: Receive = {
    case Przyjmij(list) => {
      context.parent ! Towar(list)
    }
  }
}

@main 
def zad1: Unit = {
  val system = ActorSystem("Zadanie1")
  val firma = system.actorOf(Props[Firma](), "Firma")
  firma ! Utworz(10)
  val dostawcy = for {i <- (1 to 10) } yield {system.actorOf(Props[Dostawca](), s"Dostawca-${i}")}
  dostawcy.foreach(d => d ! Dostarcz(firma))
}