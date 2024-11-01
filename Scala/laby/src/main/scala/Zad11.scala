@main
def Zad10: Unit = {
    type MSet[A] = A => Int 
}
def sum[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    (x: A) => s1(x) + s2(x)
}

def diff[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    (x: A) => s1(x) - s2(x)
}

def mult[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    (x: A) => s1(x) * s2(x)
}