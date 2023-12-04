import java.util.*

fun main() {
    with(Scanner(System.`in`)) {
        val n = nextInt()
        val k = nextInt()
        val a = IntArray(n) { nextInt() }
        println(a.foldIndexed(0.0) { i, s, v ->
            s + k.coerceAtMost(n - k + 1).coerceAtMost(i + 1).coerceAtMost(n - i) * v.toDouble()
        } / (n - k + 1))
    }
}
