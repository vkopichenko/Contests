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
            val maxSize = 10000
            val sizes = IntArray(maxSize + 1)
            s.forEach { ++sizes[it] }
            val counts = IntArray(sizes.size - k)
            counts[0] = sizes.asList().subList(0, k + 1).sum()
            for (i in 1 until counts.size) {
                counts[i] = counts[i - 1] - sizes[i - 1] + sizes[i + k]
            }
            println(counts.max())
        }
    }
}
