fun main() {
    val (n, a, b, c) = readln().split(' ').map { it.toLong() }
    println(when ((4 - n % 4).toInt()) {
        1 -> listOf(a, b+c, 3*c).min()
        2 -> listOf(2*a, b, 2*c).min()
        3 -> listOf(3*a, a+b, c).min()
        else -> 0
    })
}
