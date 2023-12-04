import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    fun brutto(size: Int, range: IntRange, conforms: (Int, IntArray) -> Boolean, action: (IntArray) -> Unit) {
        val a = IntArray(size)
        fun rec(p: Int) {
            if (p < a.size) {
                for (i in range) {
                    a[p] = i
                    if (conforms(p, a)) rec(p + 1)
                }
            } else action(a)
        }
        rec(0)
    }

    fun sumByBrutto(size: Int, range: IntRange, conforms: (Int, IntArray) -> Boolean, func: (IntArray) -> Long): Long {
        var s = 0L
        brutto(size, range, conforms) { s += func(it) }
        return s
    }

    val K = 7
    val S = "BESIGOM"
    val reminders = Array(S.length) { IntArray(K) }

/*
    fun calc(s: String): Long {
        val letterCounts = s.groupingBy { it }.eachCount()
        println(letterCounts)
        val letters = letterCounts.keys.map { S.indexOf(it) }.toIntArray()
        val counts = letterCounts.values.toIntArray()
        return sumByBrutto(letters.size, 0 until K) { a ->
            if (a.indices.sumBy { a[it] * counts[it] } % K == 0) {
                println(a.joinToString())
                a.foldIndexed(1L) { i, p, r -> p * reminders[letters[i]][r] }
                        .apply { println(this) }
            } else 0
        }
    }
*/

    Scanner(File("bgm.in")).useWith {
        File("bgm.out").printWriter().useWith {
            val n = nextInt()
            repeat(n) {
                val c = next()[0]
                val v = nextInt()
                ++reminders[S.indexOf(c)][Math.floorMod(v, K)]
            }
//            println(reminders.joinToString("\n") { it.joinToString() })
            val r = sumByBrutto(S.length, 0..K,
                    { p, a ->
                        reminders[p][a[p]] > 0 && when (p) {

                            else -> true
                        }
                    }) { a ->
//                calc("BESSIE") * calc("GOES") * calc("MOO")
                a.foldIndexed(1L) { i, p, r -> p * reminders[i][r] }
            }
            println(r)
        }
    }
}

