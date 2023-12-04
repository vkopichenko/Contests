import java.util.*

fun main() {
    with(Scanner(System.`in`)) {
        val n = nextInt()
        val a = IntArray(n) { nextInt() }
        var s = 0L
        val sumsFromBegin = LongArray(n) { i -> s += a[i]; s }
        val sumsUntilEnd = LongArray(n) { i -> s -= a[i]; s }
        val sa = (0 until n).sortedBy { a[it] }
        var ans = "NO"
        outer@
        for (i in 0 until n) {
            val dl = sumsFromBegin[i] - sumsUntilEnd[i]
            if (dl == 0L) { ans = "YES"; break }
            if (dl > Int.MAX_VALUE || dl < Int.MIN_VALUE || dl and 1 == 1L) continue
            val d = dl.toInt()
            val d2 = Math.abs(d) shr 1
            val p = sa.binarySearchBy(d2) { a[it] }
            if (p < 0) continue
            if ((sa[p] > i) xor (d > 0)) { ans = "YES"; break }
            var pp = p
            while (pp > 0 && a[sa[--pp]] == d2) if ((sa[pp] > i) xor (d > 0)) { ans = "YES"; break@outer }
            pp = p
            while (pp < n - 1 && a[sa[++pp]] == d2) if ((sa[pp] > i) xor (d > 0)) { ans = "YES"; break@outer }
        }
        println(ans)
    }
}
