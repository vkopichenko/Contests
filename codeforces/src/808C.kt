import java.util.*

fun main() {

    data class Cup(val p: Int, val a: Int, var f: Int = 0)

    fun Int.halfUp() = (this + 1) shr 1

    with(Scanner(System.`in`)) {
        val n = nextInt()
        val w = nextInt()
        val cups = Array(n) { i -> Cup(i, nextInt()) }
        val halfSum = cups.sumBy { it.a.halfUp() }
        if (halfSum > w) println(-1)
        else {
            var extra = w - halfSum
            val s = cups.sortedBy { -it.a }
            var c = 0
            s.forEach {
                it.f = it.a.halfUp()
                val e = extra.coerceAtMost(it.a - it.f)
                it.f += e
                extra -= e
            }
            println(cups.joinToString(" ") { it.f.toString() })
        }
    }
}
