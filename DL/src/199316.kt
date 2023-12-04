import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("cbarn.in")).useWith {
        File("cbarn.out").printWriter().useWith {
            val n = nextInt()
            val r = IntArray(n) { nextInt() }
            val sum = r.sum()
            var min = Int.MAX_VALUE
            for (i in 0 until n) {
                var s = sum
                var d = 0
                for (j in 0 until n) {
                    val p = (i + j) % n
                    s -= r[p]
                    d += s
                }
                min = min.coerceAtMost(d)
            }
            println(min)
        }
    }
}

