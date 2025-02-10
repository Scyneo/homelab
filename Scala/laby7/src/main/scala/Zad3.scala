@main
def zad3: Unit = {
 val linie = io.Source
  .fromResource("ogniem-i-mieczem.txt")
  .getLines.toList

  val letters = linie.map(_.filter(_.isLetter))
  val finals = letters.map(_.groupMapReduce(_.toLower)(_ => 1)(_ + _)).flatten.groupMapReduce(_._1)(_._2)(_ + _)
  val combined = finals.foldLeft(0)(_+_._2)
  histogram(finals, 50)
  println(finals.toSeq.sorted)
  println(combined)
  
 def histogram(finals: Map[Char, Int], maks: Int): Unit = {
  finals.toSeq.sorted.foreach(e => println(s"${e._1}:" + "*" * (if (e._2 > maks) maks else e._2)))
 }
}
