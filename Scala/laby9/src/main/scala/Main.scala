import Math.sqrt

@main
def Zad1: Unit = {
  val xd = C(5, -1)
  val xd1 = C(3, 0.5)
  println(xd / xd1)
  println(xd)
  println(xd == xd1)
  println(xd != xd1)
  println(xd > xd1)
  println( 2.0f + xd)
}

class C(val re: Float, val im: Float) extends Ordered[C] {

  def this(re: Float) =  {
    this(re, 0)
  }

  def +(that: Int) = C(this.re + that, this.im)
  def -(that: Int) = C(this.re - that, this.im)
  def *(that: Int) = C(this.re * that, this.im * that)
  def /(that: Int) = C(this.re / that, this.im / that)


  def +(that: C) = C(this.re + that.re, this.im + that.im)
  def -(that: C) = C(this.re - that.re, this.im - that.im)
  def *(that: C) = {
        C(this.re * that.re - this.im * that.im, 
        this.im * that.re + this.re * that.im)
      }
  def /(that: C) = {
    val x = that.re * that.re + that.im * that.im
    if x < 0 then throw IllegalArgumentException("xdddddd")
    C((this.re * that.re - this.im * that.im) / x,
      (this.im * that.re - this.re * that.im) / x)
  }

  override def equals(other: Any): Boolean = other match {
    case that: C => 
      this.re == that.re && this.im == that.im
    case _ => false
  }

  def !=(other: C): Boolean = {
    !(this.equals(other))
  }
  
  def compare(that: C): Int = {
    sqrt(this.re * this.re + this.im * this.im).toInt - sqrt(that.re * that.re + that.im * that.im).toInt
  }

  override def toString(): String = {
    if this.im > 0 then s"$re + $im"
    else if this.im == 0 then s"$re"
    else s"$re - ${Math.abs(im)}"
    }
}

// object C {
//   implicit class FloatExtension(val f: Float) extends AnyVal {
//     def +(that: C): C = C(f + that.re)
//   }  
// }

extension(f: Float) {
  def +(that: C): C = C(f + that.re)
}