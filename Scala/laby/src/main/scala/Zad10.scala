import scala.annotation.tailrec

@main
def Zad10: Unit = {
    val f: Int => Double = x => x * 2.5
    val g: Double => String = y => s"Result: $y"
    val h: (Double, Double) => Double = (x, y) => x * 10 + y
    val i: Long => Double = x => x * 3.14
    println(compose(f)(g)(4))
    println(prod(f)(g)(4, 5.0d))
    println(lift(h)(f, i)(4, 10))

}

def compose[A, B, C](f: A => B)(g: B => C): A => C = {
    (x: A) => g(f(x))
}

def prod[A, B, C, D](f: A => C)(g: B => D): (A, B) => (C, D) = {
    (x: A, y: B) => (f(x), g(y))
}
def lift[A, B, T](op: (T,T) => T)(f: A => T, g: B => T): (A,B) => T = {
    (x: A, y: B) => op(f(x), g(y))
}