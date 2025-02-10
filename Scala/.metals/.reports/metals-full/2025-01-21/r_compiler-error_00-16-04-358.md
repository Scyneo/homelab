file://<WORKSPACE>/laby14/src/main/scala/Zadanie.scala
### scala.reflect.internal.Types$TypeError: illegal cyclic inheritance involving class Annotation

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:



action parameters:
offset: 2018
uri: file://<WORKSPACE>/laby14/src/main/scala/Zadanie.scala
text:
```scala
import org.apache.pekko.actor.typed.{Behavior, ActorRef}
import org.apache.pekko.actor.typed.scaladsl.{Behaviors, ActorContext}
import org.apache.pekko.actor.typed.ActorSystem

object Konkurs {
    sealed trait Message
    case object Start extends Message
    case object Stop extends Message
    case class Proba(runda: ActorRef[Message]) extends Message
    case class StartRunda(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]]) extends Message
    case class RunFinalRunda(tabela: Tabela) extends Message
    case class Wynik(z: ActorRef[Message], a: Int, b: Int, c: Int) extends Message
    case class Tabela(wyniki: List[Wynik]) extends Message

    object Organizator {
        def apply(): Behavior[Message] = Behaviors.setup { ctx =>
            val zawodnicy = (1 to 50).map { i =>
                ctx.spawn(Zawodnik(), s"zawodnik-$i")
            }.toList
            val runda = ctx.spawn(Runda(), "runda")
            mainBehavior(ctx, runda, zawodnicy)
        }

        def mainBehavior(ctx: ActorContext[Message], runda: ActorRef[Message], zawodnicy: List[ActorRef[Message]]): Behavior[Message] = Behaviors.receiveMessage {
            case Start =>
                ctx.log.info("Zawody rozpoczęte")
                runda ! StartRunda(ctx.self, zawodnicy)
                getResults(runda)
            case Stop =>
                ctx.log.info("Zawody zakończone")
                Behaviors.stopped
            case RunFinalRunda(tabela) =>
                finalRound(ctx, runda, tabela)
            case _ =>
                Behaviors.stopped
        }
        
        def getResults(runda: ActorRef[Message]): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Tabela(wynik) =>
                    val wynik2 = wynik.sortBy(w => w.a + w.b + w.c)(Ordering[Int].reverse).take(20)
                    ctx.log.info("Wyniki: {}", wynik2)
                    ctx.self ! RunFinalRunda(Tabela(wynik2))
                    finalRound(ctx, r@@)
                case _ =>
                    Behaviors.stopped
            }
        }

        def finalRound(ctx: ActorContext[Message], runda: ActorRef[Message], tabela: Tabela): Behavior[Message] = {
            val zawodnicy: List[ActorRef[Message]] = tabela.wyniki.map(_.z)
            runda ! StartRunda(ctx.self, zawodnicy)

            Behaviors.receiveMessage {
                case Tabela(wynikFinal) =>
                    val wynik2 = (tabela.wyniki ++ wynikFinal).groupBy(w=> w.z)
                    ctx.log.info("Wyniki: {}", wynik2)
                    Behaviors.stopped
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
                    ctx.log.info("Zaczynamy runde")
                    zawodnicy.foreach(_ ! Proba(ctx.self))
                    receiveProba(organizator, zawodnicy)
                case _ =>
                    Behaviors.stopped
            }
        }

        def receiveProba(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]]): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Wynik(z, a, b, c) =>
                    tabela = Tabela(tabela.wyniki :+ Wynik(z, a, b, c))
                    if (tabela.wyniki.size == zawodnicy.size) {
                        ctx.log.info("Wysyłamy do organizatora wyniki rundy")
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

scala.reflect.internal.Types$TypeError: illegal cyclic inheritance involving class Annotation