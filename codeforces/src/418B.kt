import java.util.*

fun main() {
    with(Scanner(System.`in`)) {
        val n = nextInt()
        val a = IntArray(n) { nextInt() }
        val b = IntArray(n) { nextInt() }
        val da = a.groupBy { it }.values.maxBy { it.size }!![0]
        val ma = (1..n).minus(a.asIterable()).first()
        val i = a.indices.firstOrNull { a[it] == da && a[it] != b[it] && b[it] == ma }
                ?: a.indices.first { a[it] == da && a[it] != b[it] }
        a[i] = ma
        println(a.joinToString(" "))
    }
}
