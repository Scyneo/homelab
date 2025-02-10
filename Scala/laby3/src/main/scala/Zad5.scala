import scala.annotation.tailrec

@main
def Zad5: Unit = {
    var arr = Array(1, 3, 3, 6, 8)
    var mlr: (Int, Int) => Boolean = (_ < _)
    println(isOrdered(arr, mlr))
}

def isOrdered(tab: Array[Int], mlr: (Int, Int) => Boolean): Boolean = {
    @tailrec
    def helper(idx: Int): Boolean = {
        idx >= tab.length - 1 || (mlr(tab(idx), tab(idx+1)) && helper(idx+1))
    }    
    helper(0)
}