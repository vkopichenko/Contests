import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("diamond.in")).useWith {
        File("diamond.out").printWriter().useWith {
            val n = nextInt()
            val k = nextInt()
            val s = IntArray(n) { nextInt() }
            val counts = s.asList().groupingBy { it }.eachCount()
            var best = 0
            val keys = counts.keys.toList()
            for (start in keys.indices) {
                val minSize = keys[start]
//                val maxSize = keys.subList(start, start).fold(0)
//                val minSize = counts.entries[start]
//                var sum =
//                t = co
            }
            println(best)
        }
    }
}
