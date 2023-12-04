import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("square.in")).useWith {
        File("square.out").printWriter().useWith {
            val x = mutableListOf<Int>()
            val y = mutableListOf<Int>()
            repeat(4) {
                x += nextInt()
                y += nextInt()
            }
            val a = maxOf(x.max()!! - x.min()!!, y.max()!! - y.min()!!)
            println(a*a)
        }
    }
}
