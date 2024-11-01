import scala.math.pow
import scala.annotation.tailrec

@main
def Zad5: Unit = {
    println(divide(List(1, 2, 3, 4, 5)))
}

def divide[A](list: List[A]): (List[A], List[A]) = {
    @tailrec
    def helper[A](acc1: List[A], acc2: List[A], remainder: List[A]): (List[A], List[A]) = {
        remainder match {
            case Nil => (acc1.reverse, acc2.reverse)
            case first :: Nil => ((first :: acc1).reverse, acc2.reverse) 
            case first :: second :: tail => helper(first :: acc1, second :: acc2, tail)
        }
    }
    return helper(Nil, Nil, list)
}