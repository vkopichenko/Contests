import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R) = use { with(it, block) }

    Scanner(File("dq.in")).useWith {
        File("dq.out").printWriter().useWith {
            val n = nextInt()
            val k = nextInt()
            val c = Array(k) { nextInt() }
            for (i in 1..n) {

            }
            println(n)
        }
    }
}
