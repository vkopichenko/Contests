fun main() {
    val (n, m) = readln().split(' ').map { it.toInt() }
    val a = readln().split(' ').map { it.toInt() }
    val lr = Array(m, { readln().split(' ').map { it.toInt() }.run { Pair(this[0] - 1, this[1]) } })
    println(lr.map { a.subList(it.first, it.second).sum() }.sumBy { it.coerceAtLeast(0) })
}
