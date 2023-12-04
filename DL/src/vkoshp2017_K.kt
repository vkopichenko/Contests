fun main() {
    val abcd = readln().split(' ').filterNot(String::isEmpty).map(String::toInt)
    val (a, c, b, d) = abcd.sorted()
    println("$a $b $c $d")
}
