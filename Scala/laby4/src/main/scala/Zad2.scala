import scala.math.pow
import scala.annotation.tailrec

@main
def Zad2: Unit = {
    val a = List(1, 3, 5, 8)
    val b = List(2, 4, 6, 8, 10, 12)
    val leq = (m: Int, n: Int) => m < n
    println(merge(a, b)(leq))
}

def merge[A](a: List[A], b: List[A])(leq: (A, A) => Boolean): List[A] = {
    @tailrec
    def helper(list1: List[A], list2: List[A], acc: List[A]): List[A] = {
        (list1, list2) match {
            case (Nil, Nil) => acc.reverse
            case (Nil, _) => acc.reverse ++ list2
            case (_, Nil) => acc.reverse ++ list1
            case (ah :: at, bh :: bt) =>
                if (leq(ah, bh)) helper(at, list2, ah :: acc)
                else helper(list1, bt, bh :: acc)
        }
    }
    helper(a, b, Nil)
}