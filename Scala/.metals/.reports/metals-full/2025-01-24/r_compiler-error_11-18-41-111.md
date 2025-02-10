error id: 7B24F233620AC68017BC6B6C3469744D
file://<WORKSPACE>/laby15/src/main/scala/Main.scala
### scala.reflect.internal.Types$TypeError: illegal cyclic reference involving object Predef

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:



action parameters:
offset: 514
uri: file://<WORKSPACE>/laby15/src/main/scala/Main.scala
text:
```scala
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
        case n if n < wartosc => context.become(zLewymPoddrzewem(actor, wartosc))
        case n if n > wartosc => context.become(zPrawymPoddrzewem(wa@@, actor))
        case n if n == wartosc => context.become(lisc(n))
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
        case n if n > wartosc => context.become(zPoddrzewami(lewe, wartosc, actor))
        case n if n == wartosc => context.become(zLewymPoddrzewem(lewe, wartosc))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => lewe ! Znajdz(n)
        case n if n > wartosc => log.info("gowno")
        case n if n == wartosc => log.info("Znaleziono, lewe")
      }
    }
   }

  def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(n) => {
      var actor = context.actorOf(Props[Wezel](), s"wezel-${n}")
      n match {
        case n if n < wartosc => context.become(zPoddrzewami(actor, n, prawe))
        case n if n > wartosc => prawe ! Wstaw(n)
        case n if n == wartosc => context.become(zPrawymPoddrzewem(n, prawe))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => log.info("gowno")
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => log.info("Znaleziono, prawe")
      }
    }
  }

  def zPoddrzewami(lewe: ActorRef, wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(n) => {
      n match {
        case n if n < wartosc => lewe ! Wstaw(n)
        case n if n > wartosc => prawe ! Wstaw(n)
        case n if n == wartosc => context.become(zPoddrzewami(lewe, n, prawe))
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
  root ! Wstaw(15)
  root ! Wstaw(14)
  root ! Wstaw(4)
  root ! Wstaw(5)
  root ! Znajdz(5)
}
```



#### Error stacktrace:

```

```
#### Short summary: 

scala.reflect.internal.Types$TypeError: illegal cyclic reference involving object Predef