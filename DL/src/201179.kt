import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    Scanner(File("reduce.in")).useWith {
        File("reduce.out").printWriter().useWith {
            val n = nextInt()
            val x = IntArray(n)
            val y = IntArray(n)
            repeat(n) {
                x[it] = nextInt()
                y[it] = nextInt()
            }
            val r = 0 until n
            val minXi = r.minBy { x[it] }!!
            val maxXi = r.maxBy { x[it] }!!
            val minYi = r.minBy { y[it] }!!
            val maxYi = r.maxBy { y[it] }!!
            val candidates = listOf(minXi, maxXi, minYi, maxYi)
            fun IntArray.except(ci: Int) = asSequence().filterIndexed { i, _ -> i != ci }
            val areas = mutableListOf<Int>()
            candidates.forEach { ci ->
                val xx = x.except(ci)
                val yy = y.except(ci)
                areas += (xx.max()!! - xx.min()!!) * (yy.max()!! - yy.min()!!)
            }
            println(areas.min()!!)
        }
    }
}

