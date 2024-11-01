# Funkcje częściowe

Według dokumentacji, funkcje częściowe opisane są za pomocą cechy:

```scala
trait PartialFunction[-A, +B] extends A => B {
    // wszystkie metody wymienione poniżej
}
```

Cecha ta posiada (abstrakcyjną) metodę

```scala
def isDefinedAt(x: A): Boolean
```

oraz dziedziczy (także abstrakcyjną) metodę

```scala
def apply(x: A): B
```

Posiada też (między innymi) następujące metody „konkretne”:

```scala
def andThen[C](k: B => C): PartialFunction[A, C]
def andThen[C](k: PartialFunction[B, C]): PartialFunction[A, C]
def applyOrElse[A1 <: A, B1 >: B](x: A1, default: A1 => B1): B1
def compose[R](k: PartialFunction[R, A]): PartialFunction[R, B]
def lift: A => Option[B]
def orElse[A1 <: A, B1 >: B](that: PartialFunction[A1, B1]): PartialFunction[A1, B1]
def unapply(a: A): Option[B]
```

Żeby zdefiniować funkcję częściową z Int do Int musimy zatem zdefiować „brakujące” metody
w cesze PartialFunction[Int,Int] – `apply` oraz `isDefinedAt`.
