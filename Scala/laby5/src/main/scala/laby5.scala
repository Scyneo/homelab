@main
def laby5: Unit = {
    val s = Seq(0, 1, 2, 3, 4, 5, 6);
    val s1 = Seq(0, 3, 2, 3, 4, 5, 6);
    println(subSeq(s, 1, 3))
    println("JAzda")
    println(remElems(s, 1))
    println(diff(s, s1))
    val s2 = Seq(Some(5.4), Some(-2.0), Some(1.0), None, Some(2.6))
    println(sumOption(s2))
}
 def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {
    seq.drop(begIdx).take(endIdx)
 }

def remElems[A](seq: Seq[A], k: Int): Seq[A] = {
    seq.filter(_ != k)
}

def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = {
    // seq1.zip(seq2).filter {case (a,b) => a != b }.map {case (a, _) => a}
    seq1.zip(seq2).collect { case (a, b) if a != b => a}
}

def sumOption(seq: Seq[Option[Double]]): Double = {
    seq.foldLeft(0d)((a, e) => e match { case None => a; case Some(i) => a + i})
    // seq.flatten().foldLeft(0d)((a, b) => a + b)
}

// def deStutter[A](seq: Seq[A]): Seq[A] = {
//     //seq.foldLeft(Seq(0))((a, b) => )
// }