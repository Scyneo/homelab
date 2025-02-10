@main
def Zad3: Unit = {
    triangle(5)
}

def triangle(wys: Int): Unit = {
    def getEl(i: Int, j: Int): Int = {
        if (j == 0 || i == j) 1
        else getEl(i-1, j-1) + getEl(i-1, j)
    }

    def printRow(i: Int, curr: Int = 0): Unit = {
    if (curr <= i) {
      print(getEl(i, curr) + "   ")
      printRow(i, curr + 1)
        }
    }

    def printTriangle(curr: Int): Unit = {
    if (curr < wys) {
      print(" " * (wys - curr - 1) * 2)
      printRow(curr)
      println()
      printTriangle(curr + 1)
        }
    }
    printTriangle(0)
}