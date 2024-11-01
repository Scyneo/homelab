file://<WORKSPACE>/laby/src/main/scala/Zad9.scala
### scala.MatchError: TypeDef(A,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree)) (of class dotty.tools.dotc.ast.Trees$TypeDef)

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 380
uri: file://<WORKSPACE>/laby/src/main/scala/Zad9.scala
text:
```scala
import scala.annotation.tailrec

@main
def Zad9: Unit = {
    val l: List[Option[Int]] = List(Some(1), None, Some(2), None, Some(3), Some(4))
    val op1: Int => Int = _ + 0
    val op2: (Int, Int) => Int = _ + _
    val lSub = List(3, 2, 1)
    println(compute(l)(op1)(op2))
}

def compute[A, B](l: List[Option[A]])(op1: A => B)(op2: (A, B) => B): Option[B] = {
    def helper[A.@@(l: List[Option[A]], acc: Option[B]): Option[B] = {
        l match {
            case Nil => acc
            case None::tail => helper(tail, acc)
            case Some(head)::tail =>
                acc match {
                    case None => helper(tail, Some(op1(head)))
                    case Some(value) => helper(tail, Some(op2(head, value)))
                }
        }
     }
     helper(l, None)
}

```



#### Error stacktrace:

```
dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents$$anonfun$2(KeywordsCompletions.scala:218)
	scala.Option.map(Option.scala:242)
	dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents(KeywordsCompletions.scala:215)
	dotty.tools.pc.completions.KeywordsCompletions$.contribute(KeywordsCompletions.scala:44)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:124)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:90)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

scala.MatchError: TypeDef(A,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree)) (of class dotty.tools.dotc.ast.Trees$TypeDef)