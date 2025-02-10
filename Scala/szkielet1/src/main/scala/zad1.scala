import scala.annotation.tailrec

def leastVal[A, B](l1: List[A], l2: List[B])(op: (A, B) => Double): Double = {
	if (l1.size == 0 || l2.size == 0) throw new IllegalArgumentException
	@tailrec
	def helper(l1: List[A], l2:List[B], least: Double): Double = {
		l1 match {
			case Nil => least
			case (ah :: tail1) => 
				l2 match {
					case Nil => helper(tail1, l2, least)
					case (bh :: tail2) =>
						if op(ah, bh) < least then helper(tail1, tail2,op(ah, bh))
						else helper(ah :: tail1, tail2, least)
				}
		}	
	}
	helper(l1, l2, 10000d)
}

@main def zad1: Unit = {
	val comp: (Int, Int) => Double = (_ - _)
	val l1 = List(1, 2, 3)
	val l2 = List(5, 4, 3, 4, 7, 2, 4, 6)
	println(leastVal(l1, l2)(comp))
}
