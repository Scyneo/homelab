error id: 16CF52805F16587C7368A96CE0F9DEFB
file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
### java.lang.StringIndexOutOfBoundsException: Range [975, 975 + -2) out of bounds for length 1821

occurred in the presentation compiler.



action parameters:
offset: 985
uri: file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
text:
```scala
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.*
import scala.util.Random

sealed trait Message
case class Utworz2(n: Int) extends Message
case object Generuj extends Message
case class Wynik(n: Int) extends Message

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
            generatory.foreach(g -> g ! Generuj)
            mainBehavior(generatory, List())
      }
   }
 }

 def mainBehavior(ctx: ActorContext[Message], generatory: List[ActorRef[Message]], wyniki: List[Wynik]): Behavior[Message] = {
      if (wyniki.size >= generatory.size) {
         results(ctx,@@ generatory, wyniki)
      } else {
            Behaviors.receiveMessage {
               case wynik: Wynik => mainBehavior(ctx, generatory, wyniki :+ wynik)
               case _ => Behaviors.stopped
            }
      }
   }

  def results(generatory: List[ActorRef[Message]], wyniki: List[Wynik]): Unit = {
      
  }
}

object Generator {
 def apply(): Behavior[Message] = Behaviors.receive {
   (ctx, msg) => {
      msg match {
         case Generuj =>
            val len = losuj_zad2(10, 100)
            val sequence = for {i <- (1 to len) } yield {losuj_zad2(0, 1)}
            calculateSequence(sequence.toList)
      }
   }
 }
 def calculateSequence(sequence: List[Int]): Int = {

 }


@main 
def zad2: Unit = {
   val pacjent: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
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

java.lang.StringIndexOutOfBoundsException: Range [975, 975 + -2) out of bounds for length 1821