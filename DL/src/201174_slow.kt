import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("reduce.in")).useWith {
        File("reduce.out").printWriter().useWith {
            val n = nextInt()
            val k = 3
            val x = IntArray(n)
            val y = IntArray(n)
            repeat(n) {
                x[it] = nextInt()
                y[it] = nextInt()
            }
            val compareByXY0 = compareBy<Int>({ x[it] }, { y[it] })
            val compareByXY1 = compareBy<Int>({ x[it] }, { -y[it] })
            val compareByYX0 = compareBy<Int>({ y[it] }, { x[it] })
            val compareByYX1 = compareBy<Int>({ y[it] }, { -x[it] })
            val areas = mutableListOf<Int>()
            for (mask in 0 until (1 shl (k shl 2))) {
                val excluded = mutableListOf<Int>()
                val remaining = (0 until n).asSequence().filter { it !in excluded }
                var m = mask
                repeat(k) {
                    excluded += when (m and 7) {
                        0 -> remaining.minWith(compareByXY0)!!
                        1 -> remaining.minWith(compareByXY1)!!
                        2 -> remaining.maxWith(compareByXY0)!!
                        3 -> remaining.maxWith(compareByXY1)!!
                        4 -> remaining.minWith(compareByYX0)!!
                        5 -> remaining.minWith(compareByYX1)!!
                        6 -> remaining.maxWith(compareByYX0)!!
                        7 -> remaining.maxWith(compareByYX1)!!
                        else -> TODO()
                    }
                    m = m shr 4;
                }
//                println(excluded.joinToString())
                val xx = remaining.map { x[it] }
                val yy = remaining.map { y[it] }
                areas += (xx.max()!! - xx.min()!!) * (yy.max()!! - yy.min()!!)
            }
//            println(areas.joinToString())
            println(areas.min()!!)
        }
    }
}

