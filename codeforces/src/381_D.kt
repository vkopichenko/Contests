fun main() {
    val n = readln().toInt()
    val a = readln().split(' ').map { it.toInt() }
    val t = Array(n-1, { readln().split(' ').map { it.toInt() }.run { Pair(this[0], this[1]) } })
    println("TODO")
}
