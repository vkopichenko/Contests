fun main() {
    val a = readln().split(' ').map { it.toLong() }.toLongArray()
//    val (b, d, s) = a
//    fun p(vararg x: Long) = (x[2] shl 4) + (x[1] shl 2) + x[0]
    fun p(vararg x: Long) = x.foldIndexed(0L) { i, acc, v -> acc + (v shl (i shl 1)) }
    val overflows = mapOf(
            p(0, 1, 0) to 1,

            p(2, 2, 0) to 1,
            p(0, 2, 2) to 1,

            p(2, 1, 0) to 1,
            p(0, 1, 2) to 1,
            p(2, 0, 1) to 1,
            p(1, 0, 2) to 1,
            p(1, 2, 0) to 1,
            p(0, 2, 1) to 1,

            p(0, 2, 0) to 2,
            p(0, 0, 2) to 2,
            p(2, 0, 0) to 2,

            p(2, 0, 2) to 1)
    val hi = a.max()!! - 2L
    var r = a.fold(0L) { acc, v -> acc + (hi - v).coerceAtLeast(0) }
//    println(r)
    val overflow = a.map { (it - hi).coerceAtLeast(0) }.toLongArray()
    r += overflows[p(*overflow)] ?: 0
    println(r)
}
