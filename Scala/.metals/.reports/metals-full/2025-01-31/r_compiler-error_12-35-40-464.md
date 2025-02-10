error id: 16CF52805F16587C7368A96CE0F9DEFB
file://<WORKSPACE>/szkielet/src/main/scala/zad1.scala
### java.lang.StringIndexOutOfBoundsException: Range [713, 713 + -2) out of bounds for length 1539

occurred in the presentation compiler.



action parameters:
offset: 726
uri: file://<WORKSPACE>/szkielet/src/main/scala/zad1.scala
text:
```scala
import org.apache.pekko
import pekko.actor.*
import scala.util.Random

case class Utworz(lMagazynierow: Int)
case object Dostawa
case class Dostarcz(firma: ActorRef)
case class Przyjmujacy(magazynier: ActorRef)
case object Brak
case class Przyjmij(list: List[Int])
case class Towar(list: List[Int])

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad1(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

class Firma extends Actor with ActorLogging {
  def receive: Receive = {
    case Utworz(n) => {
      val workers = for {i <- (1 to n) } yield {context.actorOf(Props[Magazynier](), s"magazynier-${i}")}.toList()
      context.become(receiveDostawa(@@))
  }

  def receiveDostawa(free: List[ActorRef], busy: List[ActorRef]): Receive {
    case Dostawa => {

    }
    case Towar(list) => {
      sender()
    }
  }
}

class Dostawca extends Actor with ActorLogging {
  def receive: Receive = {
    case Dostarcz(to) => {
      to ! Dostawa
    }
    case Przyjmujacy(actor) => {
      val randInt = losuj_zad1(0, 100)
      val randList = for i in (0...randInt) => {}
      actor ! Przyjmij(randList)
      self ! PoisonPill
    }
    case Brak => {
      self ! PoisonPill
    }
  }
}

class Magazynier extends Actor with ActorLogging {
  def receive: Receive = {
    case Przyjmij(list) => {
      self.context.parent ! Towar(list)
    }
  }
}

@main 
def zad1: Unit = {
  val system = ActorSystem("Zadanie1")
}

```


presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar [exists ]
Options:





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
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$treeSymbol$1(SignatureHelpProvider.scala:401)
	scala.Option.map(Option.scala:230)
	scala.meta.internal.pc.SignatureHelpProvider.treeSymbol(SignatureHelpProvider.scala:399)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCall$.unapply(SignatureHelpProvider.scala:216)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:327)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:321)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.fromTree(SignatureHelpProvider.scala:290)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$signatureHelp$3(SignatureHelpProvider.scala:31)
	scala.Option.flatMap(Option.scala:271)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$signatureHelp$2(SignatureHelpProvider.scala:29)
	scala.Option.flatMap(Option.scala:271)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:27)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:421)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Range [713, 713 + -2) out of bounds for length 1539