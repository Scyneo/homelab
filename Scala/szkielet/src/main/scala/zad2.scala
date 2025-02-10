import org.apache.pekko.actor.typed.scaladsl.{Behaviors, ActorContext}
import org.apache.pekko.actor.typed.*
import scala.util.Random

sealed trait Message
case class Utworz2(n: Int) extends Message
case class Generuj(nadzorca: ActorRef[Message]) extends Message
case class Wynik(actor: ActorRef[Message], n: Int) extends Message

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad2(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

object Nadzorca {
 def apply(): Behavior[Message] = Behaviors.receive {
   (ctx, msg) => {
      msg match {
         case Utworz2(n) => 
            val generatory = (1 to n).map { i => ctx.spawn(Generator(), s"generator-$i") }.toList
            generatory.foreach(g => g ! Generuj(ctx.self))
            mainBehavior(ctx, generatory, List())
         case _ =>
            Behaviors.stopped
      }
   }
 }

 def mainBehavior(ctx: ActorContext[Message], generatory: List[ActorRef[Message]], wyniki: List[Wynik]): Behavior[Message] = {
      if (wyniki.size >= generatory.size) {
         results(ctx, wyniki)
      } else {
            Behaviors.receiveMessage {
               case wynik: Wynik => mainBehavior(ctx, generatory, wyniki :+ wynik)
               case _ => Behaviors.stopped
            }
      }
   }

  def results(ctx: ActorContext[Message], wyniki: List[Wynik]): Behavior[Message] = {
   val sortedWyniki = wyniki.sortBy(wynik => (-wynik.n, wynik.actor.path.name))
   sortedWyniki.zipWithIndex.foreach { case (wynik, index) =>
   println(s"${index + 1} " +
      s"${wynik.actor.path.name}: " +
      s"${wynik.n}")}
   Behaviors.same
  }
}

object Generator {
 def apply(): Behavior[Message] = Behaviors.receive {
   (ctx, msg) => {
      msg match {
         case Generuj(nadzorca) =>
            val len = losuj_zad2(1, 10)
            val sequence = for {i <- (1 to len) } yield {losuj_zad2(0, 1)}
            ctx.log.info("{}",sequence.toList)
            val n = calculateSequence(sequence.toList)
            nadzorca ! Wynik(ctx.self, n)
            Behaviors.same
         case _ =>
            Behaviors.stopped
      }
   }
 }
 def calculateSequence(sequence: List[Int]): Int = {
   var count = 1
   sequence.sliding(2).foreach(two => two match {
      case List(a, b) => if a != b then count = count +1
      case _ => None
   })
   return count
 }
}


@main 
def zad2: Unit = {
   val pacjent: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   pacjent ! Utworz2(10)
}