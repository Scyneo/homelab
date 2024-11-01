import scala.math.pow
import scala.annotation.tailrec

@main
def Zad3: Unit = {
  val n: Int = 101
  val decimal: Int = binToDec(n)
  println(decimal)
}

def binToDec(bin: Int): Int = {
  @tailrec
  def helper(n: Int, power: Int, acc: Int): Int = {
    if (n == 0) acc
    else if (n % 10 == 1) helper(n / 10, power + 1, acc + (pow(2, power).intValue)) 
    else 
      helper(n / 10, power + 1, acc)
  }
  helper(bin, 0, 0)
}
