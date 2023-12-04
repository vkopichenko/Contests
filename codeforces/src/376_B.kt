fun main() {
    val n = readln().toInt()
    val aa = readln().split(' ').map { it.toInt() }
    val a = aa.toMutableList().apply { add(0) }.toIntArray()
    assert(n == a.size)
    var d = 0
    var r = true
    for (v in a) {
        val o = v - d
        if (o < 0) { r = false; break }
        d = o and 1
    }
    println(if (r) "YES" else "NO")
}
