fun main() {
    val (a, b) = readln().split(' ').map(String::toInt)
    println(if (!(a == 0 && b == 0) && Math.abs(a - b) <= 1) "YES" else "NO")
}
