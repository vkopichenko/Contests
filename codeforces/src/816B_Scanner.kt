import java.util.*

fun main() {
    val Q = 200000 + 1;
    with(Scanner(System.`in`.bufferedReader())) {
        val n = nextInt()
        val k = nextInt()
        val q = nextInt()
        val qb = IntArray(Q)
        val qe = IntArray(Q)
        repeat(n) {
            ++qb[nextInt()]
            ++qe[nextInt()]
        }
        var t = 0
        val qq = IntArray(Q)
        for (i in 1 until qq.size) { t += qb[i]; t -= qe[i - 1]; qq[i] = t }
        var s = 0
        val qs = IntArray(Q) { s += if (qq[it] >= k) 1 else 0; s }
        repeat(q) {
            val (a, b) = nextInt() to nextInt()
            println(qs[b] - qs[a - 1])
        }
    }
}
