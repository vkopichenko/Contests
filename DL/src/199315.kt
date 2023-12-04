import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("pails.in")).useWith {
        File("pails.out").printWriter().useWith {
            val x = nextInt()
            val y = nextInt()
            val m = nextInt()
            var max = 0
            var p = 0
            while (p <= m) {
                val lastp = p
                while (p <= m) {
                    max = max.coerceAtLeast(p)
                    p += y
                }
                p = lastp + x
            }
            println(max)
        }
    }
}

