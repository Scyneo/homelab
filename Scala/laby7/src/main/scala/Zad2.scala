@main
def zad2: Unit = {
 val linie = io.Source
  .fromResource("nazwiska.txt")
  .getLines.toList

  val max = linie.map(_.split(" ", 2)(0).distinct.length).max
  val filtered = linie.filter {name => 
    val List(a, b) = name.split(" ", 2).toList
    a.distinct.length == max
  }
  val min = filtered.map(_.split(" ", 2)(1).length).max
  val finals = filtered.filter {name => 
    val List(a, b) = name.split(" ", 2).toList
    b.length == min
  }
  println(finals)
 }
