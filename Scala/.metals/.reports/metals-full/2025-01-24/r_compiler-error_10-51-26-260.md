error id: 7B24F233620AC68017BC6B6C3469744D
file://<WORKSPACE>/laby15/src/main/scala/Main.scala
### scala.reflect.internal.Types$TypeError: illegal cyclic inheritance involving class AnyVal

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:



action parameters:
uri: file://<WORKSPACE>/laby15/src/main/scala/Main.scala
text:
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
        case n if n < wartosc => lewe !
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



#### Error stacktrace:

```

```
#### Short summary: 

scala.reflect.internal.Types$TypeError: illegal cyclic inheritance involving class AnyVal