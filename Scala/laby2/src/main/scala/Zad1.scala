@main
def Zad1: Unit = {
    val str: String = "Hello"
    println(reverse(str))
}

def reverse(str: String): String = {
    if (str.isEmpty()) ""
    else reverse(str.tail) + str.head
}