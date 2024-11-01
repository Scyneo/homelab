import scala.math.pow
import scala.annotation.tailrec

@main
def Zad7: Unit = {
    val a = List(1, 1, 2, 4, 4, 6, 8, 10, 12, 1, 2, 2, 2)
    println(compress(a))
}

def compress[A](list: List[A]): List[(A, Int)] = {
    @tailrec
    def helper[A](list: List[A], current: A, count: Int, acc : List[(A, Int)]): List[(A, Int)] = {
        list match {
            case Nil => ((current -> count)::acc).reverse
            case head ::tail => 
                if (head == current) helper(tail, head, count+1, acc)
                else helper(tail, head, 1, (current -> count)::acc)

        }
    }
    list match {
        case head :: tail => helper(tail, head, 1, List.empty)
    }
}