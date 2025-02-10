error id: DD9157C1377DBF5B51A29598DA5BA487
file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
### java.lang.AssertionError: assertion failed: 
  (3,1)
     while compiling: file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
        during phase: globalPhase=<no phase>, enteringPhase=namer
     library version: version 2.12.20
    compiler version: version 2.12.20
  reconstructed args: -classpath <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Ident(org)
       tree position: line 2 of file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
            tree tpe: org.type
              symbol: final package org
   symbol definition: final package org (a ModuleSymbol)
      symbol package: <none>
       symbol owners: package org
           call site: <none> in <none>

== Source file context for tree position ==

     1 import org.apache.pekko.actor.typed.scaladsl.Behaviors
     2 import org.apache.pekko.actor.typed.*
     3 import scala.util.Random
     4 
     5 sealed trait Message
     6 case class Utworz2(n: Int) extends Message
     7 case object Generuj extends Message

occurred in the presentation compiler.

Failed to shorten type Class[T]

action parameters:
offset: 1108
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
         results(generatory, wyniki)
      } else {
            Behaviors.receiveMessage {
               case wynik: Wynik => mainBehavior(c@@generatory, wyniki :+ wynik)
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
scala.reflect.internal.SymbolTable.throwAssertionError(SymbolTable.scala:185)
	scala.reflect.internal.Symbols$Symbol.updateInfo(Symbols.scala:1593)
	scala.meta.internal.pc.MetalsGlobal.shortSymbol$1(MetalsGlobal.scala:332)
	scala.meta.internal.pc.MetalsGlobal.loop$1(MetalsGlobal.scala:414)
	scala.meta.internal.pc.MetalsGlobal.shortType(MetalsGlobal.scala:543)
	scala.meta.internal.pc.Signatures$SignaturePrinter.<init>(Signatures.scala:310)
	scala.meta.internal.pc.completions.Completions.infoString(Completions.scala:291)
	scala.meta.internal.pc.completions.Completions.infoString$(Completions.scala:286)
	scala.meta.internal.pc.MetalsGlobal.infoString(MetalsGlobal.scala:36)
	scala.meta.internal.pc.completions.Completions.detailString(Completions.scala:326)
	scala.meta.internal.pc.completions.Completions.detailString$(Completions.scala:318)
	scala.meta.internal.pc.MetalsGlobal.detailString(MetalsGlobal.scala:36)
	scala.meta.internal.pc.CompletionProvider.$anonfun$completions$2(CompletionProvider.scala:111)
	scala.collection.Iterator$$anon$10.next(Iterator.scala:461)
	scala.collection.Iterator.toStream(Iterator.scala:1417)
	scala.collection.Iterator.toStream$(Iterator.scala:1416)
	scala.collection.AbstractIterator.toStream(Iterator.scala:1431)
	scala.collection.Iterator.$anonfun$toStream$1(Iterator.scala:1417)
	scala.collection.immutable.Stream$Cons.tail(Stream.scala:1173)
	scala.collection.immutable.Stream$Cons.tail(Stream.scala:1163)
	scala.collection.immutable.StreamIterator.$anonfun$next$1(Stream.scala:1061)
	scala.collection.immutable.StreamIterator$LazyCell.v$lzycompute(Stream.scala:1050)
	scala.collection.immutable.StreamIterator$LazyCell.v(Stream.scala:1050)
	scala.collection.immutable.StreamIterator.hasNext(Stream.scala:1055)
	scala.collection.convert.Wrappers$IteratorWrapper.hasNext(Wrappers.scala:32)
	org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter.write(CollectionTypeAdapter.java:134)
	org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter.write(CollectionTypeAdapter.java:40)
	com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper.write(TypeAdapterRuntimeTypeWrapper.java:73)
	com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$2.write(ReflectiveTypeAdapterFactory.java:247)
	com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.write(ReflectiveTypeAdapterFactory.java:490)
	com.google.gson.Gson.toJson(Gson.java:944)
	org.eclipse.lsp4j.jsonrpc.json.adapters.MessageTypeAdapter.write(MessageTypeAdapter.java:440)
	org.eclipse.lsp4j.jsonrpc.json.adapters.MessageTypeAdapter.write(MessageTypeAdapter.java:56)
	com.google.gson.Gson.toJson(Gson.java:944)
	com.google.gson.Gson.toJson(Gson.java:899)
	org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler.serialize(MessageJsonHandler.java:149)
	org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler.serialize(MessageJsonHandler.java:144)
	org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer.consume(StreamMessageConsumer.java:59)
	org.eclipse.lsp4j.jsonrpc.RemoteEndpoint.lambda$handleRequest$1(RemoteEndpoint.java:290)
	java.base/java.util.concurrent.CompletableFuture$UniAccept.tryFire(CompletableFuture.java:718)
	java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510)
	java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179)
	scala.meta.internal.metals.CancelTokens$.$anonfun$future$1(CancelTokens.scala:42)
	scala.meta.internal.metals.CancelTokens$.$anonfun$future$1$adapted(CancelTokens.scala:38)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:484)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: 
  (3,1)
     while compiling: file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
        during phase: globalPhase=<no phase>, enteringPhase=namer
     library version: version 2.12.20
    compiler version: version 2.12.20
  reconstructed args: -classpath <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.20/scala-library-2.12.20.jar -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Ident(org)
       tree position: line 2 of file://<WORKSPACE>/szkielet/src/main/scala/zad2.scala
            tree tpe: org.type
              symbol: final package org
   symbol definition: final package org (a ModuleSymbol)
      symbol package: <none>
       symbol owners: package org
           call site: <none> in <none>

== Source file context for tree position ==

     1 import org.apache.pekko.actor.typed.scaladsl.Behaviors
     2 import org.apache.pekko.actor.typed.*
     3 import scala.util.Random
     4 
     5 sealed trait Message
     6 case class Utworz2(n: Int) extends Message
     7 case object Generuj extends Message