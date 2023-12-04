import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    // https://stackoverflow.com/questions/16336500/kotlin-ternary-conditional-operator
//    fun Int.takeIf(cond: Boolean) = if (cond) this else 0
//    fun Int.takeUnless(cond: Boolean) = if (!cond) this else 0

    Scanner(File("paint.in")).useWith {
        File("paint.out").printWriter().useWith {
            val a = nextInt()
            val b = nextInt()
            val c = nextInt()
            val d = nextInt()
/*
            println(0
                    + (b - a)//.takeUnless(a in c..d && b in c..d)
                    + (d - c)//.takeUnless(c in a..b && d in a..b)
                    - (b - c)//.takeIf(c in a..b && d >= b)
                    + (d - a)//.takeIf(a in c..d && b > d)
            )
*/
            println(0
                    + (b - a)
                    + (d - c)
                    - (b - c)
                    + (b - d)
            )
        }
    }
}
