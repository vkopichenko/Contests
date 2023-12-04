import java.io.Closeable
import java.io.File

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    File("haybales.in").bufferedReader().useWith {
        File("haybales.out").printWriter().useWith {
            val (n, q) = readln().split(' ').map(String::toInt)
            val h = readln().split(' ').map { 2 * it.toInt() }.toIntArray()
            h.sort()
            repeat(q) {
                val (a, b) = readln().split(' ').map(String::toInt)
                val l = 2 * a - 1
                val r = 2 * b + 1
                val lp = -h.binarySearch(l) - 1
                val rp = -h.binarySearch(r) - 1
                println(rp - lp)
            }
        }
    }
}
