import java.util.*

fun main() {

    // TODO: KEEP
//    naturalOrder<Int>()
    fun IntArray.isOrdered() = if (size <= 1) true else (1 until size).all { this[it - 1] <= this[it] }

    with(Scanner(System.`in`)) {
        val n = nextInt()
        val k = nextInt()
        val a = IntArray(n) { nextInt() }
        val b = IntArray(k) { nextInt() }
        var r = k > 1
        if (!r) {
            a[a.indexOfFirst { it == 0 }] = b[0]
            r = !a.isOrdered()
        }
        println(if (r) "Yes" else "No")
    }
}
