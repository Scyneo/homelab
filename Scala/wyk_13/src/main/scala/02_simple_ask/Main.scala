package zjp
package wyk_13_02.simple_ask

import org.apache.pekko.actor.typed.ActorSystem

@main def main: Unit = {
  val guardian: ActorSystem[Guardian.Command] = ActorSystem(Guardian(), "ask-without-content")
  guardian ! Guardian.Start(List("tekst-a", "tekst-b", "tekst-c"))

  waitForEnter()
  guardian.terminate()
}
