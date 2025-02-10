package example_typed

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem

object Counter {
  sealed trait Cmd
  case object Inc extends Cmd
  case object Dec extends Cmd

  def apply(cnt: Int, min: Int = 0, max: Int = 5): Behavior[Cmd] =
    Behaviors.receive { (ctx, msg) =>
      msg match {
        case Inc =>
          if (cnt == max) {
            ctx.log.info(s"Licznik ma już wartość maksymalną „$max” i nie może być zwiększony")
            Behaviors.stopped
          } else {
            val newCnt = cnt + 1
            ctx.log.info(s"Zwiększamy licznik do wartości $newCnt")
            apply(newCnt, min, max)
          }
        case Dec =>
          if (cnt == min) {
            ctx.log.info(s"Licznik ma już wartość minimalną „$min” i nie może być zmniejszony")
            Behaviors.stopped
          } else {
            val newCnt = cnt - 1
            ctx.log.info(s"Zmniejszamy licznik do wartości $newCnt")
            apply(newCnt, min, max)
          }
      }}
}

@main
def demo: Unit = {
  val główny: ActorSystem[Counter.Cmd] = ActorSystem(Counter(0), "counter")
  import Counter.*
  główny ! Counter.Inc
  główny ! Counter.Dec
  główny ! Counter.Dec
}