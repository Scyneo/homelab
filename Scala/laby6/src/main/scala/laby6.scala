@main
def laby6: Unit = {
    val str = "Stringss"
    println(countChars(str))
    val set = Set(- 1, 0, 1, 2, 3, 4, 5, 6);
    val s = Seq(- 1, 0, 1, 2, 3, 4, 5, 6);
    println(minNotCon(set))
    println(swap(s))

    val s1 = Seq(0, 3, 2, 3, 4, 5, 6);
    val s2 = Seq(Some(5.4), Some(-2.0), Some(1.0), None, Some(2.6))
    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq 
    println(strefy.filter(_.contains("Europe/")).map(s => s.stripPrefix("Europe/")).sortBy(_.length))
    val code = Seq(1, 3, 2, 2, 4, 5)
    val move = Seq(2, 1, 2, 4, 7, 2)
    println(score(code)(move))

}

def countChars(str: String): Int = {
    str.toLowerCase().distinct.length()
}

def minNotCon(set: Set[Int]): Int = {
    Iterator.from(0).find(e => !set.filter(_ >= 0).toSeq.sorted.contains(e)).get
}

def swap[A](seq: Seq[A]): Seq[A] = {
    seq.grouped(2).flatMap{
        case Seq(x, y) => Seq(y, x)
        case Seq(x) => Seq(x)
    }.toSeq
}

def score(code: Seq[Int])(move: Seq[Int]): (Int, Int) = {
    code.zip(move).flatMap{
        case 
    }
}