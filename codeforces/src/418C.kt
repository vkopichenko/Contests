import java.util.*

fun main() {

    // TODO: KEEP
    fun IntArray.getOrPut(i: Int, defaultValue: () -> Int): Int {
        if (this[i] == 0) this[i] = defaultValue()
        return this[i]
    }

    // TODO: SO
    fun <T> Iterable<T>.indexRangesBy(selector: (T) -> Boolean): List<IntRange> {
        val r = mutableListOf<IntRange>()
        var i = 0
        var curStart = -1
        val iter = iterator()
        while (true) {
            val hasNext = iter.hasNext()
            if (hasNext && selector(iter.next())) {
                if (curStart == -1) curStart = i
            } else if (curStart != -1) {
                r += curStart until i
                curStart = -1
            }
            if (!hasNext) break
            ++i
        }
        return r
    }

    fun IntRange.size() = endInclusive - start + 1

    with(Scanner(System.`in`.bufferedReader())) {
        val n = nextInt()
        nextLine()
        val s = nextLine()

        val indexRanges = mutableMapOf<Char, List<IntRange>>()
        for (c in 'a'..'z') indexRanges[c] = s.asIterable().indexRangesBy { it == c }

        val solutions = mutableMapOf<Char, IntArray>()
        val q = nextInt()
        repeat(q) {
            val m = nextInt()
            val c = next()[0]
            nextLine()

            val sol = solutions.getOrPut(c) { IntArray(n) }.getOrPut(m) {
                var best = m
                val ir = indexRanges[c]!!
                var irs = 0
                var irf = 0
                var start = ir[irs].start
                var finish = start + m
                while (irs < n) {
                    start = ir[irs++].start
                    while (finish < n && irf < ir.size && ir[irf].start <= finish) {
                        finish += ir[irf].size()
                        if (finish > n) {
                            start = (start - (finish - n)).coerceAtLeast(0)
                            finish = n
                        }
                        ++irf
                    }
                    best = best.coerceAtLeast(finish - start)
                }
                while (finish < n && irf < ir.size && ir[irf].start <= finish) {
                    finish = (finish + ir[irf].size()).coerceAtMost(m)
                    ++irf
                    best = best.coerceAtLeast(finish - start)
                }
                TODO()
                best
            }
            println(sol)
        }
    }
}
