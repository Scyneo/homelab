file://<WORKSPACE>/laby14/src/main/scala/Zadanie.scala
### scala.reflect.internal.Types$TypeError: illegal cyclic reference involving object Predef

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:



action parameters:
offset: 1964
uri: file://<WORKSPACE>/laby14/src/main/scala/Zadanie.scala
text:
```scala
import org.apache.pekko.actor.typed.{Behavior, ActorRef}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem

object Konkurs {
    sealed trait Message
    case object Start extends Message
    case object Stop extends Message
    case class Proba(runda: ActorRef[Message]) extends Message
    case class StartRunda(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]]) extends Message
    case class Wynik(z: ActorRef[Message], a: Int, b: Int, c: Int) extends Message
    case class Tabela(wyniki: List[Wynik]) extends Message

    object Organizator {
        def apply(): Behavior[Message] = Behaviors.setup { ctx =>
            val zawodnicy = (1 to 50).map { i =>
                ctx.spawn(Zawodnik(), s"zawodnik-$i")
            }.toList
            val runda = ctx.spawn(Runda(), "runda")

            Behaviors.receiveMessage {
                case Start =>
                    ctx.log.info("Zawody rozpoczęte")
                    runda ! StartRunda(ctx.self, zawodnicy)
                    getResults()
                case Stop =>
                    ctx.log.info("Zawody zakończone")
                    Behaviors.stopped
                case _ =>
                    Behaviors.stopped
            }
        }

        def finalRound(tabela: Tabela): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Start =>
                    ctx.log.info("Finał rozpoczęty")
                    Behaviors.same
                case Stop =>
                    ctx.log.info("Finał zakończony")
                    Behaviors.stopped
                case _ =>
                    Behaviors.stopped
            }
        }

        def getResults(): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Tabela(wynik) =>
                    ctx.log.info("Wyniki: {}", wynik)
                    finalRound(Tabl@@)
                case _ =>
                    Behaviors.stopped
            }
        }
    }
    

    object Zawodnik {
        val rand = new scala.util.Random
        def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
            msg match {
                case Proba(runda) =>
                    val a = rand.between(0, 100)
                    val b = rand.between(0, 100)
                    val c = rand.between(0, 100)
                    runda ! Wynik(ctx.self, a, b, c)
                    Behaviors.same
                case _ =>
                    Behaviors.stopped
            }
        }
    }

    object Runda {
        var tabela = Tabela(List())
        def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
            msg match {
                case StartRunda(organizator, zawodnicy) =>
                    zawodnicy.foreach(_ ! Proba(ctx.self))
                    receiveProba(organizator, zawodnicy)
                case _ =>
                    Behaviors.stopped
            }
        }

        def receiveProba(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]]): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Wynik(z, a, b, c) =>
                    tabela.wyniki :+ Wynik(z, a, b, c)
                    if (tabela.wyniki.size == zawodnicy.size) {
                        organizator ! tabela
                        apply()
                    } else {
                        Behaviors.same
                    }
                case _ =>
                    Behaviors.stopped
            }
        }
    }
}

@main
def zadanie: Unit = {
  import Konkurs.*
  val organizator = ActorSystem(Organizator(), "organizator")
  organizator ! Start
}
```



#### Error stacktrace:

```

```
#### Short summary: 

scala.reflect.internal.Types$TypeError: illegal cyclic reference involving object Predef