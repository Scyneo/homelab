import org.apache.pekko.actor.typed.{Behavior, ActorRef}
import org.apache.pekko.actor.typed.scaladsl.{Behaviors, ActorContext}
import org.apache.pekko.actor.typed.ActorSystem

object Konkurs {
    sealed trait Message
    case object Start extends Message
    case object Stop extends Message
    case class Proba(runda: ActorRef[Message]) extends Message
    case class StartRunda(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]]) extends Message
    case object RunFinalRunda extends Message
    case class Wynik(z: ActorRef[Message], a: Int, b: Int, c: Int) extends Message
    case class Tabela(wyniki: List[Wynik]) extends Message

    object Organizator {
        def apply(): Behavior[Message] = Behaviors.setup { ctx =>
            val zawodnicy = (1 to 50).map { i => ctx.spawn(Zawodnik(), s"zawodnik-$i") }.toList
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
            case _ =>
                Behaviors.stopped
        }
        
        def getResults(runda: ActorRef[Message]): Behavior[Message] = Behaviors.receive {
            (ctx, msg) => msg match {
                case Tabela(wynik) =>
                    val wynik2 = wynik.sortBy(totalScore)(Ordering[Int].reverse).take(20)
                    ctx.log.info("Wyniki: {}", wynik2)
                    ctx.self ! RunFinalRunda
                    finalRound(runda, Tabela(wynik2))
                case _ =>
                    Behaviors.stopped
            }
        }

        def finalRound(runda: ActorRef[Message], tabela: Tabela): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
            msg match {
                case RunFinalRunda =>
                    val zawodnicy: List[ActorRef[Message]] = tabela.wyniki.map(_.z)
                    runda ! StartRunda(ctx.self, zawodnicy)
                case _ =>
                    Behaviors.stopped
            }
            Behaviors.receiveMessage {
                case Tabela(wynikFinal) =>
                    val players = (tabela.wyniki ++ wynikFinal).groupBy(_.z)
                        .map { case (name, scores) =>
                            scores.reduce { (acc, score) =>
                                Wynik(acc.z, acc.a + score.a, acc.b + score.b, acc.c + score.c)
                            }
                        }.toList
                    
                    val sortedPlayers = players.sortBy(wynik => (-totalScore(wynik), -wynik.a, -wynik.b, -wynik.c))

                    println("Wyniki finału:")
                    sortedPlayers.zipWithIndex.foreach { case (wynik, index) =>
                    println(s"${index + 1} " +
                      s"${wynik.z.path.name}: " +
                      s"${wynik.a}-${wynik.b}-" +
                      s"${wynik.c} " +
                      s"Suma: ${totalScore(wynik)}")
                    }
                    Behaviors.same
                case _ =>
                    Behaviors.stopped
            }
        }
        def totalScore(wynik: Wynik): Int = wynik.a + wynik.b + wynik.c
    }
    
    object Runda {
        def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
            msg match {
                case StartRunda(organizator, zawodnicy) =>
                    ctx.log.info("Zaczynamy runde")
                    zawodnicy.foreach(_ ! Proba(ctx.self))
                    receiveProba(organizator, zawodnicy, List.empty)
                case _ => 
                    Behaviors.stopped
            }
        }

        def receiveProba(organizator: ActorRef[Message], zawodnicy: List[ActorRef[Message]], wyniki: List[Wynik]): Behavior[Message] = {
            if (wyniki.size >= zawodnicy.size) {
                organizator ! Tabela(wyniki)
                apply()
            } else {
                Behaviors.receiveMessage {
                    case wynik: Wynik => receiveProba(organizator, zawodnicy, wyniki :+ wynik)
                    case _ => Behaviors.stopped
                }
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