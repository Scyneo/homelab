// Funkcja przekształca argumenty na wynik

def plusPoPythonowemu(n: Int): Int =
  val x = 123
  n + x

def plus(n: Int): Int = {
  val x = 123
  n + x
}

// Obserwacje:
// 1. „nie stosujemy” słowa kluczowego „return”


@main
def prog5: Unit = {
  println(plus(10))
}

def ciekawa(n: Int)(m: Int): Int = {
  val wynik = n + m
  println(wynik)
  wynik
}

@main
def prog6: Unit = {
  println(ciekawa(10)(2))
  println(ciekawa(10))
  val coś = ciekawa(120)
  val cośJawnie: Int => Int = ciekawa(120)

  println(coś(5))
  println(cośJawnie(10))
}

def zastosuj(op: (Int,Int) => Int)(n: Int)(m: Int): Int = {
  // zastosuj: ((Int,Int) => Int) => Int => Int => Int
  // zastosuj((a,b) => a + b): Int => Int => Int
  op(n, m)
}

@main
def prog7: Unit = {
  val z = zastosuj((a,b) => a + b)(10)
  println(z(1000))
}

def silnia(n: Int): Int = {
  if n <= 0 then 1
  else n * silnia(n - 1)
}

//---------------------------------------------------------------------
// Rekurencja „stosowa”
//---------------------------------------------------------------------
/*
  silnia(5) ==>
    5 * silnia(4) ==>
    5 * 4 * silnia(3) ==>
    5 * 4 * 3 * silnia(2) ==>
    5 * 4 * 3 * 2 * silnia(1) ==>
    5 * 4 * 3 * 2 * 1 * silnia(0) ==>
    5 * 4 * 3 * 2 * 1 * 1
    5 * 4 * 3 * 2 * 1
    5 * 4 * 3 * 2
    5 * 4 * 6
    5 *24
  120
*/


@main
def prog8: Unit = {
  println(silnia(20))
  println(silnia(100420))
}

@annotation.tailrec
def silniaOgonowa(n: Int, akumulator: Int = 1): Int = {
  if n <= 0 then akumulator
  else silniaOgonowa(n - 1, n * akumulator)
}

@main
def prog9: Unit = {
  println(silniaOgonowa(20))
  println(silniaOgonowa(100420))
}

@annotation.tailrec
def silniaOgonowaLepiej(n: BigInt, akumulator: BigInt = 1): BigInt = {
  if n <= 0 then akumulator
  else silniaOgonowaLepiej(n - 1, n * akumulator)
}

@main
def prog10: Unit = {
  println(silniaOgonowaLepiej(20))
  println(silniaOgonowaLepiej(100420))
}
