private fun isSpace(c: Char) = c in " \r\n\t"
private fun readString() = generateSequence { System.`in`.read().toChar() }
        .dropWhile { isSpace(it) }.takeWhile { !isSpace(it) }.joinToString("")
private fun readInt() = readString().toInt()

fun main() {
    val Q = 200000 + 1;
    val n = readInt()
    val k = readInt()
    val q = readInt()
    val qb = IntArray(Q)
    val qe = IntArray(Q)
    repeat(n) {
        ++qb[readInt()]
        ++qe[readInt()]
    }
    //        println(qb.asList().subList(90, 100).joinToString())
    //        println(qe.asList().subList(90, 100).joinToString())
    var t = 0
    val qq = IntArray(Q)
    for (i in 1 until qq.size) {
        t += qb[i]; t -= qe[i - 1]; qq[i] = t
    }
    //        println(qq.asList().subList(90, 100).joinToString())
    var s = 0
    val qs = IntArray(Q) { s += if (qq[it] >= k) 1 else 0; s }
    //        println(qs.asList().subList(90, 100).joinToString())
    repeat(q) {
        val (a, b) = readInt() to readInt()
        println(qs[b] - qs[a - 1])
    }
}
