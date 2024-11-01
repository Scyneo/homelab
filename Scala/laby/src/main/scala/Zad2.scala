import scala.annotation.tailrec

@main
def Zad2: Unit = {
  val n: Int = 18
  val checkPrime: Boolean = isPrime(n)
  println(checkPrime)
}

def isPrime(n: Int): Boolean = {
  @tailrec
  def helper(n: Int, divisor: Int): Boolean = {
    if (n % divisor == 0 ) return false
    else if (divisor * divisor > n) {
      return true
    }
    helper(n, divisor + 1)
  }
  helper(n, 2)
}
