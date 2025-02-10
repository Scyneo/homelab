@main
def Zad2: Unit = {
    val arr = Array(1, 1, 2, 3, 3, 2, 1, 1)
    println(palindrome(arr))
}

def palindrome(tab: Array[Int]): Boolean = {
    if (tab.size == 1) true
    else if (tab.size == 2) then tab(0) == tab(1)
    else palindrome(tab.slice(1, tab.size-1)) && (tab(0) == tab(tab.size-1))
}
