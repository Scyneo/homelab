@main
def mainProg: Unit = { // (): Unit
  println("Hello, World!")
}

@main
def mainProg2: Unit = { // (): Unit
  println("Hello, World2!")
}

// PODSTAWY
//=====================================================================
// Typ – reprezentacja/opis zbioru wartości
//=====================================================================
// Typy podstawowe
//---------------------------------------------------------------------
// typy numeryczne
val byteValue: Byte = 127       //  8-bit (-2^7 .. 2^7-1)
val shortValue: Short = 1000    // 16-bit (-2^15 .. 2^15-1)
val intValue: Int = 30000       // 32-bit (-2^31 .. 2^31-1)
val longValue: Long = 650000    // 64-bit (-2^63 .. 2^63-1)
val floatValue: Float = 3.14f   // 32-bit (IEEE 754 single-precision float)
val doubleValue: Double = 3.14d // 64-bit (IEEE 754 double-precision float)
val pi = 3.14 // pi otrzyma domyślnie typ Double
// pozostałe typy proste
val charValue: Char = 'A' // 16-bit (Unicode – nie mylić z kodowaniem użytym w pliku!)
val stringValue: String = "Ala ma kota\ni psa"
val multiLineStringValue: String =
  """
    |Ala
    |ma
    |kota
    |i psa
  """.stripMargin

val multiLineStringValue2: String =
  """
    |Ala
    |ma
    |kota
    |i psa
  """

val interpolation = s"byteValue - 1 == ${byteValue - 1}"

@main
def prog3: Unit = {
  println(multiLineStringValue2)
}

@main
def prog4: Unit = {
  println(interpolation)
}


