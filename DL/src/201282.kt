import java.io.Closeable
import java.io.File

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    infix fun Double.eq(that: Double) = Math.abs(this - that) <= Double.MIN_VALUE

    File("slowdown.in").bufferedReader().useWith {
//    run {
        File("slowdown.out").printWriter().useWith {
//        run {
            val T = mutableListOf<Double>()
            val D = mutableListOf<Double>()
            val n = readln().trim().toInt()
            repeat(n) {
                val (c, x) = readln().trim().split(' ')
                when (c) {
                    "T" -> T += x.toDouble()
                    "D" -> D += x.toDouble()
                }
            }
            val finish = 1000.0
            D += finish
            T.sort()
            D.sort()
            var d = 0.0
            var t = 0.0
            for (i in 1..n+1) {
                val v = 1.0 / i
                when {
                    T.size > 0 && (D.size == 0 || T[0] - t <= (D[0] - d)/v) -> {
                        d += v * (T[0] - t)
                        t = T.removeAt(0)
                    }
                    D.size > 0  -> {
                        t += (D[0] - d) / v
                        d = D.removeAt(0)
                    }
                }
//                println("$i: d=$d, t=$t")
                if (d eq finish) break
            }
            println(Math.round(t))
        }
    }
}
