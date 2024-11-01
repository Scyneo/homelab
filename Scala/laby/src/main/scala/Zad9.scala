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
    @tailrec
    def helper(l: List[Option[A]], acc: Option[B]): Option[B] = {
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
