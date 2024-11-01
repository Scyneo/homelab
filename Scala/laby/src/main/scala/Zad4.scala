import scala.math.pow
import scala.annotation.tailrec

@main
def Zad4: Unit = {
  val n: Int = 5
  val fib: Int = value(n)
  println(fib)
}

def value(n: Int): Int = {
  @tailrec
  def helper(n: Int, a: Int, b: Int): Int = {
    if (n == 0) a
    else if (n == 1) b
    else
      helper(n - 1, b, a + b)
  }
  helper(n, 2, 1)
}
