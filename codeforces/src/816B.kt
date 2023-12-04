fun main() {
    fun readInts() = readln().split(' ').map(String::toInt)

    val Q = 200000 + 1;
    val (n, k, q) = readInts()
    val qb = IntArray(Q)
    val qe = IntArray(Q)
    repeat(n) {
        val (l, r) = readInts()
        ++qb[l]
        ++qe[r]
    }
    var t = 0
    val qq = IntArray(Q)
    for (i in 1 until qq.size) { t += qb[i]; t -= qe[i - 1]; qq[i] = t }
    var s = 0
    val qs = IntArray(Q) { s += if (qq[it] >= k) 1 else 0; s }
    repeat(q) {
        val (a, b) = readInts()
        println(qs[b] - qs[a - 1])
    }
}
