import java.io.Closeable

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    /*File("input.txt").bufferedReader().useWith*/ run {
        /*File("output.txt").printWriter().useWith*/ run {
            val (m, n) = readln().trim().split(' ').map(String::toInt)
            val mn = m * n

            fun debug(state: Int) {
                println(state.toString(2).padStart(mn, '0'))
            }

            val cache = Array<IntArray>(mn) { IntArray(1000000) }
            fun count(bit: Int, state: Int): Int {
                fun cached(res: Int): Int { if (state < cache[0].size) cache[bit][state] = res; return res }
//                if (bit == mn) debug(state);
                if (bit == mn) return 1;
                if (state < cache[0].size && cache[bit][state] != 0) return cache[bit][state]
                val bit1 = bit + 1
                if (bit < m || bit % m == 0) return cached(count(bit1, state) + count(bit1, state or (1 shl bit)))
                val mask = (1 shl bit - 1) or (1 shl bit - m) or (1 shl bit - m - 1)
//                debug(mask);
                val masked = state and mask
                return cached(
                        (if (masked != 0) count(bit1, state) else 0) +
                        (if (masked != mask) count(bit1, state or (1 shl bit)) else 0))
            }

            if (m == 1 || n == 1) println(1 shl mn)
            else println(count(0, 0))
        }
    }
}
