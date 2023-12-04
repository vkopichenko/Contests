fun main() {
    val (a, b, c, d) = readln().split(' ').map(String::toInt)
    println("$a-$b*($c-$d)=$a-$b*$c+$b*$d")
}
