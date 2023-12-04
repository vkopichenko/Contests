import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    data class Point(val x: Int, val y: Int)

    Scanner(File("balancing.in")).useWith {
        File("balancing.out").printWriter().useWith {
            val n = nextInt()
            val B = nextLine()
            val p = Array(n) { Point(nextInt(), nextInt()) }
            val px = p.indices.sortedBy { p[it].x }
            val py = p.indices.sortedBy { p[it].y }
            for (i in 0 until n) {
                TODO()
            }
/*
            val cx = p.count { it.x < p[pp].x }
            val cy = p.count { it.y < p[pp].y }
            val cxy = p.count { it.x < p[pp].x && it.y < p[pp].y }
            println(listOf(cxy, cx - cxy, cy - cxy, n - cx - cy + cxy).max())
*/
        }
    }
}

