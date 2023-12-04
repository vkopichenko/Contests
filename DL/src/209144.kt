import java.io.Closeable
import java.io.File

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    File("cowsignal.in").bufferedReader().useWith {
        File("cowsignal.out").printWriter().useWith {
            val (m, n, k) = readln().split(' ').map(String::toInt)
            val lines = Array(m) { readln() }
            lines.forEach { line ->
                repeat(k) {
                    line.forEach { char ->
                        print(char.toString().repeat(k))
                    }
                    println()
                }
            }
        }
    }
}
