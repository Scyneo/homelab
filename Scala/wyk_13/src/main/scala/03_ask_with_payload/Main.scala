package zjp
package wyk_13_03.ask_with_payload

import org.apache.pekko.actor.typed.ActorSystem

@main def main: Unit = {
  val guardian: ActorSystem[Guardian.Command] = ActorSystem(Guardian(), "ask_with_payload")
  guardian ! Guardian.Start

  waitForEnter()
  guardian.terminate()
}

