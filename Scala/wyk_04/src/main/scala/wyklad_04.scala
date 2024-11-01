package wpug.zjp.wyk_04
/*
  Ostatnio poznaliśmy „konstruktory typów”

    - przestrzeń funkcyjna: T1 => T2 => T3 == T1 => (T2 => T3)
    - iloczyn kartezjański: (T1, … ,Tn)

  a także

    - klasy i obiekty oraz CECHY („interfejsy”)

      class K {
        val pole: Int = 123
        def metoda(): Unit = println("metoda")
      }

      val obiekt = K()

  Zanim przejdziemy do omawiania kolekcji jeszcze kilka słów o funkcjach częściowych

    - Funkcje częściowe

      trait PartialFunction[-A, +B] extends A => B

*/

//----------------------------------------------------------------------------------
// 1. Sposób tradycyjny – definiujemy klasę, rozszerzającą cechę PartialFunction
//----------------------------------------------------------------------------------
class PfDoubleDouble extends PartialFunction[Double,Double] {
  // tutaj trzeba byłoby zdefiniować dwie „brakujące” metody cechy PartialFunction
  def isDefinedAt(x: Double): Boolean = ???
  def apply(x: Double) = ???
}

val pf = PfDoubleDouble() // obiekt będący funkcją częściową z Double do Double

//----------------------------------------------------------------------------------
// 2. Można też zdefiniować klasę „anonimową” i od razu powołać do życia jej obiekt
//----------------------------------------------------------------------------------
val pierwiastek = new PartialFunction[Double, Double] {
  def apply(x: Double) = Math.sqrt(x) // pierwiastek(4) === pierwiastek.apply(4)
  def isDefinedAt(x: Double) = x >= 0
}

//----------------------------------------------------------------------------------
// 3. Można też prościej – isDefinedAt oraz apply generowane są automatycznie
//----------------------------------------------------------------------------------
def pierwiastek2: PartialFunction[Double, Double] = {
	case arg if arg >= 0.0 => Math.sqrt(arg)
}
// powyższy przykład używa alternatywnej składni dla literałów funkcyjnych (nie tylko
// dla funkcji częściowych). Literały te mają formę ciągu klauzul „case” otoczonego
// nawiasami klamrowymi.

// funkcja całkowita
val fun: (Int, Int) => Int = {
  case (m, n) if n == 0 => -99
  case (m, _) => m * 2
}

@main
def prog01: Unit = {
  val wyn1 = pierwiastek.andThen(d => d * 10)(4)      // 20
  println(wyn1)

  val wyn2 = (pierwiastek andThen pierwiastek)(16)    // 2.0
  println(wyn2)

  val wyn3 = pierwiastek.applyOrElse(-2, r => r * 10) // -20.0
  println(wyn3)

  val dziwne = pierwiastek(-2)
  println(dziwne)

  // bezpieczne „podniesienie” funkcji częściowej do całkowitej
  val lifted = pierwiastek.lift(4)                    // None
  println(lifted)

  pierwiastek.unapply(4) match {
    case Some(res) => println(s"wynik to: $res")
    case None => println("dla 4 funkcja nie jest określona")
  }
}

