import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    data class Point(val x: Int, val y: Int)

    fun longOf(hi: Int, lo: Int) = hi.toLong() shl 32 or (lo.toLong() and 0xffffffffL)

    Scanner(File("balancing.in")).useWith {
        File("balancing.out").printWriter().useWith {
            val n = nextInt()
            val B = nextInt()
            val p = Array(n) { Point(nextInt(), nextInt()) }
            p.sortWith(compareBy({ it.x }, { it.y }))
            val qHalf = n.shl(1) + 1
            val h = longOf(qHalf, qHalf)
//            val h = qHalf
            val pp = -1 - p.indices.asIterable().toList().binarySearchBy(h) { i ->
                longOf(i shl 2, p.count { it.y < p[i].y } shl 2)
//                i shl 2
            }
            val cx = p.count { it.x < p[pp].x }
            val cy = p.count { it.y < p[pp].y }
            val cxy = p.count { it.x < p[pp].x && it.y < p[pp].y }
            println(listOf(cxy, cx - cxy, cy - cxy, n - cx - cy + cxy).max())
        }
    }
}

