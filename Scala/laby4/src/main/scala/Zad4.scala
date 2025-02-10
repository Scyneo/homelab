import scala.math.pow
import scala.annotation.tailrec

@main
def Zad4: Unit = {
    val l = List(4, 5, 6, 1, 2, 3)
    val lSub = List(3, 2, 1)
    println(isSub(l, lSub))
}

def isSub[A](l: List[A], lSub: List[A]): Boolean =  {
    @tailrec
    def helper(l: List[A], lSub: List[A], lCopy: List[A]): Boolean = {
        (l, lSub) match {
           case (_, Nil) => true
           case (Nil, _) => false //short circuit this case if l2.size > l1.size
           case (h1::t1, h2::t2) => 
              if (h1 == h2) helper(lCopy, t2, lCopy)
              else helper(t1, lSub, lCopy)
        }
     }
     helper(l, lSub, l)
}