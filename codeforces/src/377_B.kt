fun main() {
    val (n, k) = readln().split(' ').map { it.toInt() }
    val a = readln().split(' ').map { it.toInt() }.toIntArray()
    val b = intArrayOf(*a)
    var r = 0
    for (i in 1 until n) {
        val d = Math.max(k - b[i-1] - a[i], 0)
        r += d
        b[i] += d
    }
    println(r)
    println(b.joinToString(" "))
}
