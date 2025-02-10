@main
def zad1: Unit = {
 val linie = io.Source
  .fromResource("liczby.txt")
  .getLines.toList

  val filtered = linie.filter { number =>
    val digits = number.map(_.asDigit)
    val a = digits.sliding(2).forall{ case Seq(a, b) => a <= b }
    val b = digits.sum % 2 != 0
    a && b
  }
  println(filtered)
}
