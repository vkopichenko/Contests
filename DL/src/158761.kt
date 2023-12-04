import java.io.Closeable
import java.io.File

fun main() {
    fun <T : Closeable?, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    File("input.txt").bufferedReader().useWith {
        File("output.txt").printWriter().useWith {
            val (n, m, k) = readln().trim().split(' ').map(String::toInt)
            val a = readln().trim().split(' ').map(String::toInt)
            val c = readln().trim().split(' ').map(String::toInt)
            val f = readln().trim().toInt()
            // val r = setOf(*c.toTypedArray<Int>()).minus(a).minus(f).sorted()
            val r = c.subtract(a).minus(f).sorted()
            println(r.size)
            println(r.joinToString(" "))
        }
    }
}
