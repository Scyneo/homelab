package wpug.zjp.wyk_03

/*
  Podstawowe konstrukcje na typach:

  - Przestrzeń funkcyjna

    A => B  // przestrzeń wszystkich funkcji z typu A do typu B
    A => B => C // to samo A => (B => C)

  - Iloczyn kartezjański

    (A, B) // oznacza A x B
    (A, B, C) // A x (B x C) czy (A x B) x C
    (A_1, … , A_n) // n-tki uporządkowane

  - „klasy wzorcowe” (ang. case classes)

    case class Osoba(imię: String, wiek: Int)  // podobne do (String, Int)

    val janek = Osoba("Janek", 19)

    janek.imię
    janek.wiek

    val bolek: (String, Int) = ("Bolek", 18)
    bolek._1 // „imię”
    bolek._2 // „wiek”

  - Suma typów

    A|B|C  // suma typów A, B i C
    A|B|C == A|(B|C) == (A|B)|C

  - Część wspólna typów

    A & B

*/


type Trójki = (Int, Int, Int)
case class Osoba(imię: String, wiek: Int)  // „podobne” do (String, Int)
case class Para(a: Int, b: Int) // „podobne” do (Int, Int)

//-------------------------------------------------------------
// CECHY (bardzo proste prtzykłady – jeszcze do tematu wrócimy)
//-------------------------------------------------------------
trait T1 { val s: String}
trait T2 { val i: Int }
trait T3 { val f: Int => Int }
trait T4 { val wartośćInt: Int }

// wartość (obiekt) „posiadająca cechę” T1
val elT1 = new T1 {
  val s = "abrakadabra"
}

// wartość (obiekt) „posiadająca cechę” T3
val elT3 = new T3 {
  val f = n => n * 4
}

// wartość (obiekt) „posiadająca cechę” T4
val elT4 = new T4 {
  val wartośćInt = 123
}


// skorzystamy z dopasowania (do) wzorca
def fun(el: T1 | T2 | T4): Unit = el match {
  case t1: T1 => println(s"element T1: ${t1.s}")
  case t2: T2 => println(s"element T2: ${t2.i}")
  case t4: T4 => println(s"element T2: ${t4.wartośćInt}")
}

def fun2(o: Osoba | Int | Double | (String,Int) | (Int => Int)): Unit = o match {
  case f: (Int => Int)@unchecked => println(s"f(2) == ${f(2)}")
  case Osoba("Jan", 19) => println("to Jasiek!")
  case Osoba(ktoś, 19) => println(s"$ktoś ma 19 lat")
  case (imię, wiek) => println(s"($imię, $wiek)")
  case i: Int => println(s"liczba całkowita $i")
  case d: Double if d > 2.0 => println(s"liczba zmiennopozycyjna > 2.0: $d")
  case _ => println("to nie Jasiek!")
}

// wytłumaczenie roli adnotacji @unchecked użytej powyżej:
//   https://www.scala-lang.org/api/current/scala/unchecked.html
//

/*
  Użyta poniżej notacja „A <: B” oznacza, że A musi być „podtypem” B lub, innymi słowy,
  każda instancja typu A jest jednocześnie instancją typu B (ale niekoniecznie na odwrót
  oczywiście)
*/
def fun3[T <: T4](el: T): Unit = {
  println(s"el.watośćInt == ${el.wartośćInt}")
}

@main
def main01_dopasowania: Unit = {
  val romek = Osoba("Romek", 19)
  fun2(romek)
  fun2(123)
  fun2(2.12)
  fun2(("Ania", 20))
  fun2(n => n * 2)
  fun2(elT3.f)
  fun3(elT4)
}

// Zapowiedź: Ważne do zapmaiętania: żeby zdefiniować własne wzorce
//   musimy określić dwie operacje – „apply” oraz „unapply”.
//   Do tematu wrócimy kiedy będziemy omawiać wspomniany na wykładzie
//   przykład prezentujący klasę liczb wymiernych.

//--------------------------------
// PROSTE struktury danych: Listy
//--------------------------------
@main
def main02_listy: Unit = {
  val l = List(1,2,3,4,5) // łatwy dostęp do pierwszego el. oraz „reszty”
  println(s" głowa: ${l.head}")  // pierwszy el. („głowa” listy)
  println(s" ogon:  ${l.tail}")  // reszta („ogon” listy)
  println(s" lista: $l")         // „pobranie” głowy/ogona jest niedestrukcyjne
  println(l.reverse) // odwracanie TWORZY NOWĄ listę (wynik)

  // LISTY są NIEMUTOWALNE

  /*
    l:  1 -> (2 -> (3 -> (4 -> (5 -> Nil))))
             /
    val reszta = l.tail

  */

  // prosty przykład GENERYCZNEJ funkcji działającej na listach
  @annotation.tailrec
  def ostatni[E](l: List[E]): Option[E] = l match {
    case Nil => None
    case el :: Nil => Some(el)
    case _ :: reszta => ostatni(reszta)
  }
  // za tydzień zobaczymy jeszcze inne podejście (zamiast Option)
}
