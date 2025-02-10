error id: 
file://<WORKSPACE>/laby15/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem

case class Wstaw(n: Int)
case class Znajdz(n: Int)

class Wezel extends Actor with ActorLogigng {
  def apply: Receive = {
    case Wstaw(n) => context.become(lisc(n))
    case Znajdz(n) => 
  }
  def lisc(wartość: Int): Receive = { 
    case Wstaw(n) => {
      var actor = context.actorOf(Wezel())
      n match {
        case n if n < wartosc => context.become(zLewymPoddrzewem(actor, n))
        case n if n > wartosc => context.become(zPrawymPoddrzewem(n, actor))
        case n if n == wartosc => context.become(lisc(n))
      }
    }
    // this is a leaf
    case Znajdz(n) => {
      if (n == wartosc) then println("Znaleziono")
    }

  }
  def zLewymPoddrzewem(lewe: ActorRef, wartość: Int): Receive = {
    case Wstaw(n) => {
      var actor = context.actorOf(Wezel())
      n match {
        case n if n < wartosc => lewe ! Wstaw(n)
        case n if n > wartosc => context.become(zPrawymPoddrzewem(n, actor))
        case n if n == wartosc => context.become(lisc(n))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => lewe ! Znajdz(n)
        case n if n > wartosc => println("gowno")
        case n if n == wartosc => println("Znaleziono")
      }
    }
   }
  def zPrawymPoddrzewem(wartość: Int, prawe: ActorRef): Receive = { ... }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => println("gowno")
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => println("Znaleziono")
      }
    }
  def zPoddrzewami(lewe: ActorRef, wartość: Int, prawe: ActorRef): Receive = { ... } 
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => lewe ! Znajdz(n)
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => println("Znaleziono")
      }
}


@main
def main: Unit = {
  val system = ActorSystem(Main(), "main")
  system ! "start"
}
```

#### Short summary: 

empty definition using pc, found symbol in pc: 