@main def zad2: Unit = {
 val domki = io.Source
  .fromResource("domki.txt")
  .getLines.toList


 val rezerwacje = io.Source 
  .fromResource("rezerwacje.txt")
  .getLines.toList

  val domki1 = domki.map(_.split(";").toList) 
  val rezerwacje2 = rezerwacje.map(_.split(";").toList) 

  val rezerwacjeDomek = rezerwacje2.map(a => (a(1), a(2), domki1.find(d => (d(0) == a(1))).get))
  val asd = rezerwacjeDomek.map(a => (a(1), a(2)(1), a(2)(2))) // liczba doby, pokoje, koszt
  val zarobek = asd.groupMapReduce(a => a._2)(o => (o._1.toInt * o._3.toInt))(_ + _) // zarobek per rezerwacja
  val zarobekIndex = zarobek.toList.sortBy(x => (x._2)).reverse
  println(zarobekIndex.zipWithIndex.map(x => (x._2 + 1, x._1(0), x._1(1))))
}
