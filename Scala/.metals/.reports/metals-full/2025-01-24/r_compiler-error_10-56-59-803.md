error id: 5550273C55DB7346AB040C14E04CD7C5
file://<WORKSPACE>/laby15/src/main/scala/Main.scala
### java.lang.StringIndexOutOfBoundsException: Range [1578, 1578 + -2) out of bounds for length 2345

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:



action parameters:
offset: 1599
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
        case n if n < wartosc => lewe ! Wstaw(n)
        case n if n > wartosc => context.become(zPrawymPoddrzewem(n, actor))
        case n if n == wartosc => context.become(zLewymPoddrzewem(lewe, n))
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

  def zPrawymPoddrzewem(wartość: Int, prawe: ActorRef): Receive = {
    case Wstaw(n) => {
      var actor = context.actorOf(Wezel())
      n match {
        case n if n < wartosc => context.become(zPoddrzewami(actor, n, @@p))
        case n if n > wartosc => prawe ! Wstaw(n)
        case n if n == wartosc => context.become(zPrawymPoddrzewem(n, prawe))
      }
    }
    case Znajdz(n) => {
      n match {
        case n if n < wartosc => println("gowno")
        case n if n > wartosc => prawe ! Znajdz(n)
        case n if n == wartosc => println("Znaleziono")
      }
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
java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
	java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
	java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
	java.base/jdk.internal.util.Preconditions.outOfBoundsCheckFromIndexSize(Preconditions.java:118)
	java.base/jdk.internal.util.Preconditions.checkFromIndexSize(Preconditions.java:397)
	java.base/java.lang.String.checkBoundsOffCount(String.java:4853)
	java.base/java.lang.String.rangeCheck(String.java:307)
	java.base/java.lang.String.<init>(String.java:303)
	scala.tools.nsc.interactive.Global.typeCompletions$1(Global.scala:1231)
	scala.tools.nsc.interactive.Global.completionsAt(Global.scala:1254)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$treeSymbol$1(SignatureHelpProvider.scala:398)
	scala.Option.map(Option.scala:230)
	scala.meta.internal.pc.SignatureHelpProvider.treeSymbol(SignatureHelpProvider.scala:396)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCall$.unapply(SignatureHelpProvider.scala:213)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:324)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:318)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.fromTree(SignatureHelpProvider.scala:287)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$signatureHelp$3(SignatureHelpProvider.scala:28)
	scala.Option.flatMap(Option.scala:271)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$signatureHelp$2(SignatureHelpProvider.scala:26)
	scala.Option.flatMap(Option.scala:271)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:24)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:417)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Range [1578, 1578 + -2) out of bounds for length 2345