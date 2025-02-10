import org.apache.pekko
import pekko.actor._

case class Init(numWorkers: Int)
case class Zlecenie(text: List[String])
case class Wykonaj(text: String)
case class Wynik(count: Int)

class Nadzorca extends Actor with ActorLogging {
  var workersDone = 0

  def receive: Receive = {
    case Init(numWorkers) => {
      val workers = for {i <- (1 to numWorkers) } yield {
        val worker = context.actorOf(Props[Pracownik](), s"worker${i}")
        context.watch(worker)
        worker
      }
      context.become(receiveZlecenie(workers.toList))
    }
  }

  def receiveZlecenie(workers: List[ActorRef]): Receive = {
    case Zlecenie(text) => {
      workers.zip(text).foreach{ case (worker, t) => worker ! Wykonaj(t) }
      context.become(waitForEnd(workers, text.drop(workers.length), 0))
    }
  }
  def waitForEnd(workers: List[ActorRef], text: List[String], count: Int): Receive = {
    case Wynik(c) => {
      if (text.isEmpty) {
        sender() ! PoisonPill
        context.become(waitForEnd(workers, text, count+c))
      } else {
        sender() ! Wykonaj(text.head)
        context.become(waitForEnd(workers, text.tail, count+c))
      }
    }
    case Terminated(worker) => {
      workersDone += 1
      if (workersDone == workers.length) {
        log.info(s"Koniec, liczba slow: ${count}")
        context.become(receiveZlecenie(workers))
      }
    }
  }
}

class Pracownik extends Actor with ActorLogging {
  def receive: Receive = {
    case Wykonaj(text) => {
      val count = text.split(" ").map(_.toLowerCase()).length
      log.info(s"Worker liczba slow: ${count}}")
      sender() ! Wynik(count)
    }
  }
}

@main 
def zad1: Unit = {
  def dane(): List[String] = {
   scala.io.Source.fromResource("ogniem_i_mieczem.txt").getLines.toList
  }
  val system = ActorSystem("WordCounter")
  val boss = system.actorOf(Props[Nadzorca](), "boss")
  boss ! Init(5)
  boss ! Zlecenie(dane())
}