import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("blocks.in")).useWith {
        File("blocks.out").printWriter().useWith {
            val n = nextInt()
            val words = Array(n) { next() to next() }
            val az = 'a'.toInt()..'z'.toInt()
            val letters = IntArray(az.last + 1)
            for (tab in words) {
                val l1 = IntArray(az.last + 1)
                val l2 = IntArray(az.last + 1)
                tab.first.forEach { ++l1[it.toInt()] }
                tab.second.forEach { ++l2[it.toInt()] }
                for (i in az) letters[i] += maxOf(l1[i], l2[i])
            }
            println(letters.slice(az).joinToString("\n"))
        }
    }
}
