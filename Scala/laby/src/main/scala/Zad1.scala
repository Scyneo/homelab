import scala.annotation.tailrec

@main
def Zad1: Unit = {
  val str: String = "Hello"
  val reversed: String = reverse(str)
  println(reversed)
}

def reverse(str: String): String = {
  @tailrec
  def helper(str: String, acc: String): String = {
    if (str.isEmpty) acc
    else
      helper(str.tail, str.head +: acc)
  }
  helper(str, "")
}