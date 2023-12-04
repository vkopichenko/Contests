import java.util.*

fun main() {
    fun Boolean.toBit(shift : Int = 0) = (if (this) 1 else 0) shl shift
    fun Int.bitCount() = Integer.bitCount(this)

    val W = 7
    val R = 5
    val B = 3
    val N = W+R+B
    val Wr = 0 until W
    val Rr = W until W + R
    val K = 4
    val whites = BitSet(K)
    val reds = BitSet(K)
    var count = 0
    repeat(N) { i ->
        whites.set(0, i in Wr)
        reds.set(0, i in Rr)
        repeat(N) { j ->
            if (j == i) return@repeat
            whites.set(1, j in Wr)
            reds.set(1, j in Rr)
            repeat(N) { k ->
                if (k == j || k == i) return@repeat
                whites.set(2, k in Wr)
                reds.set(2, k in Rr)
                repeat(N) { l ->
                    if (l == k || l == j || l == i) return@repeat
                    whites.set(3, l in Wr)
                    reds.set(3, l in Rr)
                    val wc = whites.cardinality()
                    val rc = reds.cardinality()
                    val bc = 4 - wc - rc
                    println("$i $j $k $l: $wc $rc $bc")
                    if ((wc == 2).toBit() + (rc == 2).toBit() + (bc == 2).toBit() == 2) ++count
                }
            }
        }
    }
    println(count)
}
