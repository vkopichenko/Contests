import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    data class Point(val pos: Int, val x: Int, val y: Int)

    Scanner(File("reduce.in")).useWith {
        File("reduce.out").printWriter().useWith {
            val n = nextInt()
            val k = 3
            val points = Array(n) { Point(it, nextInt(), nextInt())}
            val compareByXY0 = compareBy<Point>({ it.x }, { it.y })
            val compareByXY1 = compareBy<Point>({ it.x }, { -it.y })
            val compareByYX0 = compareBy<Point>({ it.y }, { it.x })
            val compareByYX1 = compareBy<Point>({ it.y }, { -it.x })
            val pxy0 = points.sortedWith(compareByXY0)
            val pxy1 = points.sortedWith(compareByXY1)
            val pyx0 = points.sortedWith(compareByYX0)
            val pyx1 = points.sortedWith(compareByYX1)
            fun List<Point>.except(excluded: List<Int>) = asSequence().filter { it.pos !in excluded }
            val excluded = mutableListOf<Int>()
            val minXY0 = pxy0.except(excluded)
            val minXY1 = pxy1.except(excluded)
            val maxXY0 = pxy0.asReversed().except(excluded)
            val maxXY1 = pxy1.asReversed().except(excluded)
            val minYX0 = pyx0.except(excluded)
            val minYX1 = pyx1.except(excluded)
            val maxYX0 = pyx0.asReversed().except(excluded)
            val maxYX1 = pyx1.asReversed().except(excluded)
            val areas = mutableListOf<Int>()
            for (mask in 0 until (1 shl (k shl 3))) {
                excluded.clear()
                var m = mask
                repeat(k) {
                    excluded += when (m and 0b111) {
                        0 -> minXY0.first().pos
                        1 -> minXY1.first().pos
                        2 -> maxXY0.first().pos
                        3 -> maxXY1.first().pos
                        4 -> minYX0.first().pos
                        5 -> minYX1.first().pos
                        6 -> maxYX0.first().pos
                        7 -> maxYX1.first().pos
                        else -> TODO()
                    }
                    m = m shr 3;
                }
                areas += (maxXY0.first().x - minXY0.first().x) * (maxYX0.first().y - minYX0.first().y)
            }
            println(areas.min()!!)
        }
    }
}
